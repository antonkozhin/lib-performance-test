(ns test.code.native-js
  (:require [cljs.pprint])
  (:require-macros [test.macros :refer [fn-with-source]]))

(enable-console-print!)

(def by-id (fn-with-source []
                           (.getElementById js/document "test-div")))

(def by-class (fn-with-source []
                              (.getElementsByClassName js/document "test-div")))

(def sel (fn-with-source []
                         (.querySelectorAll js/document ".test-div p")))

(def append-elem! (fn-with-source []
                                  (let [test-div (.getElementById js/document "test-div")
                                        span (.createElement js/document "span")]
                                    (.setAttribute span "class" "testspan")
                                    (.appendChild test-div span))))

(def class-list (fn-with-source []
                                (.-classList (.getElementById js/document "test-div"))))

(def add-class! (fn-with-source []
                                (-> (.-classList (.getElementById js/document "test-div"))
                                    (.add "native"))))

(def set-style! (fn-with-source []
                                (set! (.. (.getElementById js/document "test-div") -style -backgroundColor) "#aaa")))

