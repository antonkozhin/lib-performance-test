(ns test.core
  (:require [om.core :as om :include-macros true]
            [om-tools.dom :as dom :include-macros true]
            [cljs.core.async :refer [<! >! timeout]]
            [test.code.domina :as domina]
            [test.code.enfocus :as enfocus]
            [test.code.jayq :as jayq]
            [test.code.native-js :as native]
            [test.code.dommy :as dommy]
            [jayq.core :refer [$]])
  (:require-macros [cljs.core.async.macros :refer [go-loop]]
                   [test.macros :as macros]))

(enable-console-print!)

(def not-nil? (complement nil?))

(def libs [domina/fn-list enfocus/fn-list jayq/fn-list native/fn-list dommy/fn-list])

(def tests [{:title "Поиск элемента по идентификатору"
             :fn-name "by-id"}
            {:title "Поиск элемента по классу"
             :fn-name "by-class"}
            {:title "Поиск элемента по селектору"
             :fn-name "sel"}
            {:title "Добавление элемента"
             :fn-name "append-elem!"
             :cleaner-func #(.remove ($ :.testspan))}
            {:title "Определение класса элемента"
             :fn-name "class-list"}
            {:title "Изменение класса элемента"
             :fn-name "add-class!"}
            {:title "Изменение стиля элемента"
             :fn-name "set-style!"}])

(defn parse-fn-list [fn-list fn-name]
  (let [res (select-keys fn-list [:lib-name])]
    (merge res (fn-name fn-list))))

(defn add-libs [t]
  (assoc t :libs (reduce #(conj %1 (parse-fn-list %2 (keyword (:fn-name t))))
                         []
                         libs)))
(defn init-tests []
  (mapv #(add-libs %) tests))

(defonce app-state (atom {:tests (init-tests)}))

(defn measure-time [func]
  (if func
    (let [t (re-find #"\d+" (with-out-str (time (dotimes [n 10000] (func)))))]
      (quot 10000000 t))
    0))

(defn compare-results [libs]
  (let [min-idx (first (apply min-key #(-> % second :result :elapsed-time) (map-indexed vector @libs)))
        max-idx (first (apply max-key #(-> % second :result :elapsed-time) (map-indexed vector @libs)))]
    (om/update! libs [min-idx :result :min-value] true)
    (om/update! libs [max-idx :result :max-value] true)))

(defn run-tests [libs cleaner-func]
  (go-loop [i 0]
           (when-let [{:keys [func max-val min-val]} (get @libs i)]
             (<! (timeout 10))
             (om/update! libs [i :result :elapsed-time] (measure-time func))
             (when cleaner-func
               (cleaner-func))
             (recur (inc i)))
           (compare-results libs)))

(defn result-view [{:keys [elapsed-time min-value max-value]} owner]
  (reify
    om/IRender
    (render [_]
            (let [div-class "lib-test-result table-cell"
                  result-class (cond
                                min-value (str div-class " min-value")
                                max-value (str div-class " max-value")
                                :else div-class)]
              (dom/div {:class result-class} (when (not-nil? elapsed-time) (str elapsed-time " op/sec")))))))

(defn lib-view [{:keys [lib-name source func elapsed-time result] :as app} owner]
  (reify
    om/IRender
    (render [_]
            (dom/div {:class "lib table-row"}
                     (dom/div {:class "lib-name table-cell"} lib-name)
                     (dom/div {:class "code table-cell"}
                              (dom/pre nil
                                       (dom/code {:class "clojure"} (if source source "; функция не найдена"))))
                     (om/build result-view result)))))

(defn test-view [{:keys [title libs cleaner-func] :as app} owner]
  (reify
    om/IRender
    (render [_]
            (dom/div {:class "test-table"}
                     (dom/div {:class "table-caption"}
                              (dom/div {:class "test-title"} title)
                              (dom/button {:on-click #(run-tests libs cleaner-func)} "Run tests"))
                     (dom/div {:class "table-header"}
                              (dom/div {:class "table-cell"} "Название библиотеки")
                              (dom/div {:class "table-cell"} "Код")
                              (dom/div {:class "table-cell"} "Результат"))
                     (om/build-all lib-view libs {:opts {}})))))

(defn content-view [app owner]
  (reify
    om/IRender
    (render [_]
            (dom/div nil
                     (om/build-all test-view (:tests app))))))

(om/root content-view app-state {:target (. js/document (getElementById "content"))})

