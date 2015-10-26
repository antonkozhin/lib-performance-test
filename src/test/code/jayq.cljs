(ns test.code.jayq
  (:require [jayq.core :as jayq :refer [$]]
            [cljs.pprint])
  (:require-macros [test.macros :refer [fn-with-source]]))

(def fn-list {:lib-name "jayq"
              :by-id (fn-with-source []
                                     ($ :#test-div))
              :by-class (fn-with-source []
                                        ($ :.test-div))
              :sel (fn-with-source []
                                   ($ ".test-div p"))
              :append-elem! (fn-with-source []
                                            (jayq/append ($ :#test-div) "<span class=\"testspan\"></span>"))
              :class-list (fn-with-source []
                                          (-> ($ :#test-div)
                                              (jayq/attr "class")
                                              (clojure.string/split " ")))
              :add-class! (fn-with-source []
                                          (-> ($ :#test-div)
                                              (.addClass "jayq")))
              :set-style! (fn-with-source []
                                          (-> ($ :#test-div)
                                              (jayq/css {:backgroundColor "#aaa"})))})

