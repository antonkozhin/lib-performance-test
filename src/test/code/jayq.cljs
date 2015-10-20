(ns test.code.jayq
  (:require [jayq.core :as jayq :refer [$]]
            [cljs.pprint])
  (:require-macros [test.macros :refer [fn-with-source]]))

(enable-console-print!)

(def by-id (fn-with-source []
                           ($ :#test-div)))

(def by-class (fn-with-source []
                              ($ :.test-div)))

(def sel (fn-with-source []
                         ($ ".test-div p")))

(def append-elem! (fn-with-source []
                                  (jayq/append ($ :#test-div) "<span class=\"testspan\"></span>")))

(def class-list (fn-with-source []
                                (-> ($ :#test-div)
                                    (jayq/attr "class")
                                    (clojure.string/split " "))))

(def add-class! (fn-with-source []
                                (-> ($ :#test-div)
                                    (.addClass "jayq"))))

(def set-style! (fn-with-source []
                                (-> ($ :#test-div)
                                    (jayq/css {:backgroundColor "#aaa"}))))
