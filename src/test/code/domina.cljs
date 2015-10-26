(ns test.code.domina
  (:require [domina.css :as domina.css]
            [cljs.pprint])
  (:require-macros [test.macros :refer [fn-with-source]]))

(def fn-list {:lib-name "domina"
              :by-id (fn-with-source []
                                     (domina/by-id "test-div"))
              :by-class (fn-with-source []
                                        (domina/by-class "test-div"))
              :sel (fn-with-source []
                                   (domina/single-node (domina.css/sel ".test-div p")))
              :append-elem! (fn-with-source []
                                            (domina/append! (domina.css/sel "#test-div") "<span class=\"testspan\"></span>"))
              :class-list (fn-with-source []
                                          (-> (domina/attr (.getElementById js/document "test-div") "class")
                                              (clojure.string/split " ")))
              :add-class! (fn-with-source []
                                          (domina/add-class! (.getElementById js/document "test-div") "domina"))
              :set-style! (fn-with-source []
                                          (domina/set-style! (.getElementById js/document "test-div") :backgroundColor "#aaa"))})


