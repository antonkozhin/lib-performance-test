(ns test.code.dommy
  (:require [dommy.core :as dommy :refer-macros [sel1]]
            [cljs.pprint])
  (:require-macros [test.macros :refer [fn-with-source]]))

(def by-id (fn-with-source []
                           (dommy/sel1 :#content)))

(def by-class (fn-with-source []
                              (dommy/sel1 :.container)))

(def append-elem! (fn-with-source []
                                  (let [content (.getElementById js/document "content")
                                        span (.createElement js/document "span")]
                                    (.setAttribute span "class" "testspan")
                                    (dommy/append! content span))))

(def class-list (fn-with-source []
                                (-> (dommy/attr (.getElementById js/document "content") "class")
                                    (clojure.string/split " "))))

(def add-class! (fn-with-source []
                                (dommy/add-class! (.getElementById js/document "content") "dommy")))

(def set-style! (fn-with-source []
                                (dommy/set-style! (.getElementById js/document "content") "background-color" "#aaa")))
