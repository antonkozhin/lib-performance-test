(ns test.code.enfocus
  (:require-macros [test.macros :refer [fn-with-source]])
  (:require [enfocus.core :as enfocus]
            [domina :as domina]))

(def by-id (fn-with-source []
                           (domina/single-node (enfocus/css-select "#content"))))

(def by-class (fn-with-source []
                              (domina/single-node (enfocus/css-select ".content"))))

(def append-elem! (fn-with-source []
                                  (enfocus/at ["#content"] (enfocus/append (enfocus/html [:span {:class "testspan"}])))))

(def class-list (fn-with-source []
                                (-> (enfocus/from "#content" (enfocus/get-attr :class))
                                    (clojure.string/split " "))))

(def add-class! (fn-with-source []
                                (enfocus/at ["#content"] (enfocus/add-class "enfocus"))))

(def set-style! (fn-with-source []
                                (enfocus/at ["#content"] (enfocus/set-style :backgroundColor "#aaa"))))
