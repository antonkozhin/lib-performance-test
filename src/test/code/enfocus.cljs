(ns test.code.enfocus
  (:require-macros [test.macros :refer [fn-with-source]])
  (:require [enfocus.core :as enfocus]
            [domina :as domina]
            [cljs.pprint]))

(def fn-list {:lib-name "enfocus"
              :by-id (fn-with-source []
                                     (domina/single-node (enfocus/css-select "#test-div")))
              :by-class (fn-with-source []
                                        (domina/single-node (enfocus/css-select ".test-div")))
              :sel (fn-with-source []
                                   (domina/single-node (enfocus/css-select ".test-div p")))
              :append-elem! (fn-with-source []
                                            (enfocus/at ["#test-div"] (enfocus/append (enfocus/html [:span {:class "testspan"}]))))
              :class-list (fn-with-source []
                                          (-> (enfocus/from "#test-div" (enfocus/get-attr :class))
                                              (clojure.string/split " ")))
              :add-class! (fn-with-source []
                                          (enfocus/at ["#test-div"] (enfocus/add-class "enfocus")))
              :set-style! (fn-with-source []
                                          (enfocus/at ["#test-div"] (enfocus/set-style :backgroundColor "#aaa")))})


