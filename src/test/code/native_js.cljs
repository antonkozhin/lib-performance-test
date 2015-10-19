(ns test.code.native-js
  (:require-macros [test.macros :refer [fn-with-source]]))

(enable-console-print!)

(def by-id (fn-with-source []
                           (.getElementById js/document "content")))

(def by-class (fn-with-source []
                              (.getElementsByClassName js/document "container")))

(def append-elem! (fn-with-source []
                                  (let [content (.getElementById js/document "content")
                                        span (.createElement js/document "span")]
                                    (.setAttribute span "class" "testspan")
                                    (.appendChild content span))))

(def class-list (fn-with-source []
                                (.-classList (.getElementById js/document "content"))))

(def add-class! (fn-with-source []
                                (-> (.-classList (.getElementById js/document "content"))
                                    (.toggle "native"))))

(def set-style! (fn-with-source []
                                (set! (.. (.getElementById js/document "content") -style -backgroundColor) "#aaa")))
