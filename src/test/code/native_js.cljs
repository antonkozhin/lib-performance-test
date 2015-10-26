(ns test.code.native-js
  (:require [cljs.pprint])
  (:require-macros [test.macros :refer [fn-with-source]]))

(def fn-list {:lib-name "native"
              :by-id (fn-with-source []
                                     (.getElementById js/document "test-div"))
              :by-class (fn-with-source []
                                        (.getElementsByClassName js/document "test-div"))
              :sel (fn-with-source []
                                   (.querySelectorAll js/document ".test-div p"))
              :append-elem! (fn-with-source []
                                            (let [test-div (.getElementById js/document "test-div")
                                                  span (.createElement js/document "span")]
                                              (.setAttribute span "class" "testspan")
                                              (.appendChild test-div span)))
              :class-list (fn-with-source []
                                          (.-classList (.getElementById js/document "test-div")))
              :add-class! (fn-with-source []
                                          (-> (.-classList (.getElementById js/document "test-div"))
                                              (.add "native")))
              :set-style! (fn-with-source []
                                          (set! (.. (.getElementById js/document "test-div") -style -backgroundColor) "#aaa"))})
