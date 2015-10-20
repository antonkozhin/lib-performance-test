(ns test.macros)

(defmacro fn-with-source [args & fn-stuff]
  `(do
     {:source (with-out-str (cljs.pprint/write (quote ~@fn-stuff)
                              :dispatch cljs.pprint/code-dispatch
                              :right-margin 70))
      :func (fn ~args ~@fn-stuff)}))
