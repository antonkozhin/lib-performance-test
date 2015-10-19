(ns test.code.jayq
  (:require [jayq.core :as jayq :refer [$]])
  (:require-macros [test.macros :refer [fn-with-source]]))

(def by-id (fn-with-source []
                           ($ :#content)))

(def by-class (fn-with-source []
                              ($ :.container)))

(def append-elem! (fn-with-source []
                                  (jayq/append ($ :#content) "<span class=\"testspan\"></span>")))

(def class-list (fn-with-source []
                                (-> ($ :#content)
                                    (jayq/attr "class")
                                    (clojure.string/split " "))))

(def add-class! (fn-with-source []
                                (-> ($ :#content)
                                    (.toggleClass "jayq"))))

(def set-style! (fn-with-source []
                                (-> ($ :#content)
                                    (jayq/css {:backgroundColor "#aaa"}))))
