(ns test.code.domina
  (:require [domina.css :as domina.css]
            [cljs.pprint])
  (:require-macros [test.macros :refer [fn-with-source]]))

(enable-console-print!)

(def by-id (fn-with-source []
                           (domina/by-id "test-div")))

(def by-class (fn-with-source []
                              (domina/by-class "test-div")))

(def sel (fn-with-source []
                         (domina/single-node (domina.css/sel ".test-div p"))))

(def append-elem! (fn-with-source []
                                  (domina/append! (domina.css/sel "#test-div") "<span class=\"testspan\"></span>")))

(def class-list (fn-with-source []
                                (-> (domina/attr (.getElementById js/document "test-div") "class")
                                    (clojure.string/split " "))))

(def add-class! (fn-with-source []
                                (domina/add-class! (.getElementById js/document "test-div") "domina")))

#_(def set-style! (fn-with-source []
                                (domina/set-style! (.getElementById js/document "test-div") :backgroundColor "#aaa")))


