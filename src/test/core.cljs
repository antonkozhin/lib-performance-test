(ns test.core
  (:require [om.core :as om :include-macros true]
            [om-tools.dom :as dom :include-macros true]
            [cljs.core.async :refer [<! >! timeout]]
            [clojure.string :as string]
            [test.code.domina :as domina]
            [test.code.enfocus :as enfocus]
            [test.code.jayq :as jayq]
            [test.code.native-js :as native]
            [test.code.dommy :as dommy])
  (:require-macros [cljs.core.async.macros :refer [go-loop]]
                   [test.macros :as macros]))

(enable-console-print!)

(defn ->js [var-name]
  (-> var-name
      (string/replace #"/" ".")
      (string/replace #"-" "_")
      (string/replace #"!" "_BANG_")))

;; (defn invoke [function-name & args]
;;     (let [fun (js/eval (->js function-name))]
;;       (apply fun args)))

(def libs ["domina" "enfocus" "jayq" "native-js" "dommy"])

(def tests [{:title "Поиск элемента по идентификатору"
             :fn-name "by-id"}
            {:title "Поиск элемента по классу"
             :fn-name "by-class"}
            {:title "Добавление элемента"
             :fn-name "append-elem!"}
            {:title "Определение класса элемента"
             :fn-name "class-list"}
            {:title "Изменение класса элемента"
             :fn-name "add-class!"}
            {:title "Изменение стиля элемента"
             :fn-name "set-style!"}])

(defn add-libs [t]
  (assoc t :libs (reduce #(conj %1 (merge {:lib-name %2}
                                          (js/eval (->js (str "test.code." %2 "/" (:fn-name t))))))
                         []
                         libs)))

(defn init-tests []
  (mapv add-libs tests))

(defonce app-state (atom {:tests (init-tests)}))

(defn measure-time [func]
  (let [t (re-find #"\d+" (with-out-str (time (dotimes [n 10000] (func)))))]
    (str (quot 10000000 t) " op/sec")))

(defn run-tests [libs cleaner-func events]
  (go-loop [i 0]
           (<! (timeout 1000))
           (om/update! libs [i :elapsed-time] (measure-time (:func (get @libs i))))
           (when cleaner-func
             (cleaner-func))
           (when (< i (- (count @libs) 1))
             (recur (inc i)))))

(defn result-view [elapsed-time owner]
  (reify
    om/IRender
    (render [_]
            ;(println "render result")
            (dom/div {:class "lib-test-result table-cell"} elapsed-time))))

(defn lib-view [{:keys [lib-name source func elapsed-time] :as app} owner]
  (reify
    om/IRender
    (render [_]
            ;(println "render lib")
            (dom/div {:class "lib table-row"}
                     (dom/div {:class "lib-name table-cell"} lib-name)
                     (dom/div {:class "code table-cell"}
                              (dom/pre nil
                                       (dom/code {:class "clojure"} source)))
                     (om/build result-view elapsed-time)))))

(defn test-view [{:keys [title libs cleaner-func] :as app} owner]
  (reify
    om/IRender
    (render [_]
            ;(println "render test")
            (dom/div {:class "test-table"}
                     (dom/div {:class "table-caption"}
                              (dom/div {:class "test-title"} title)
                              (dom/button {:on-click #(run-tests libs cleaner-func (om/get-shared owner :chan))} "Run tests"))
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


((:func enfocus/append-elem!))


