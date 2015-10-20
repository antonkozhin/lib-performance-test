(ns test.code.dommy
  (:require [dommy.core :as dommy :refer-macros [sel1]]
            [cljs.pprint])
  (:require-macros [test.macros :refer [fn-with-source]]))

(enable-console-print!)

(def by-id (fn-with-source []
                           (dommy/sel1 :#test-div)))

(def by-class (fn-with-source []
                              (dommy/sel1 :.test-div)))

(def sel (fn-with-source []
                         (dommy/sel [:.test-div :p])))

(def append-elem! (fn-with-source []
                                  (let [test-div (.getElementById js/document "test-div")
                                        span (.createElement js/document "span")]
                                    (.setAttribute span "class" "testspan")
                                    (dommy/append! test-div span))))

(def class-list (fn-with-source []
                                (-> (dommy/attr (.getElementById js/document "test-div") "class")
                                    (clojure.string/split " "))))

(def add-class! (fn-with-source []
                                (dommy/add-class! (.getElementById js/document "test-div") "dommy")))

(def set-style! (fn-with-source []
                                (dommy/set-style! (.getElementById js/document "test-div") "background-color" "#aaa")))

