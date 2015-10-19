(ns test.code.domina
  (:require [domina.css :as domina.css])
  (:require-macros [test.macros :refer [fn-with-source]]))

(def by-id (fn-with-source []
                           (domina/by-id "content")))

(def by-class (fn-with-source []
                              (domina/by-class "content")))

(def append-elem! (fn-with-source []
                                  (domina/append! (domina.css/sel "#content") "<span class=\"testspan\"></span>")))

(def class-list (fn-with-source []
                                (-> (domina/attr (.getElementById js/document "content") "class")
                                    (clojure.string/split " "))))

(def add-class! (fn-with-source []
                                (domina/add-class! (.getElementById js/document "content") "domina")))

(def set-style! (fn-with-source []
                                (domina/set-style! (.getElementById js/document "content") :backgroundColor "#aaa")))

