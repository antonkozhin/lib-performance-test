(ns test.code.enfocus
  (:require-macros [test.macros :refer [fn-with-source]])
  (:require [enfocus.core :as enfocus]
            [domina :as domina]
            [cljs.pprint]))(enable-console-print!)

(enable-console-print!)

(def by-id (fn-with-source []
                           (domina/single-node (enfocus/css-select "#test-div"))))

(def by-class (fn-with-source []
                              (domina/single-node (enfocus/css-select ".test-div"))))

(def sel (fn-with-source []
                         (domina/single-node (enfocus/css-select ".test-div p"))))

(def append-elem! (fn-with-source []
                                  (enfocus/at ["#test-div"] (enfocus/append (enfocus/html [:span {:class "testspan"}])))))

(def class-list (fn-with-source []
                                (-> (enfocus/from "#test-div" (enfocus/get-attr :class))
                                    (clojure.string/split " "))))

(def add-class! (fn-with-source []
                                (enfocus/at ["#test-div"] (enfocus/add-class "enfocus"))))

(def set-style! (fn-with-source []
                                (enfocus/at ["#test-div"] (enfocus/set-style :backgroundColor "#aaa"))))

