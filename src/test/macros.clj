(ns test.macros)

(defmacro fn-with-source [args & fn-stuff]
  `(do
     {:source (with-out-str (cljs.pprint/write (quote ~@fn-stuff)
                              :dispatch cljs.pprint/code-dispatch
                              :right-margin 70))
      :func (fn ~args ~@fn-stuff)}))

(defmacro defsource
  {:arglists (:arglists (meta (var defn)))}
  [fn-name args & defn-stuff]
  `(do
     (defn ~fn-name {:source ~defn-stuff} ~args ~@defn-stuff)
     (var ~fn-name)))
