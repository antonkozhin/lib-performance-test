(defproject dots "0.1.0-SNAPSHOT"
  :description "Test DOM manipulation libraries"
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "1.7.122"]
                 [org.clojure/core.async "0.1.346.0-17112a-alpha"]
                 [prismatic/dommy "1.1.0"]
                 [domina "1.0.3"]
                 [enfocus "2.1.1"]
                 [jayq "2.5.4"]
                 [om "0.7.3"]
                 [prismatic/om-tools "0.3.12"]
                 [cljsjs/react "0.14.0-0"]
                 [cljsjs/jquery "2.1.4-0"]]
  :plugins [[lein-cljsbuild "1.1.0"]]
  :cljsbuild {:builds  {:dev {:source-paths ["src/test"]
                              :compiler {:output-to "resources/test.js"
                                         :optimizations :whitespace
                                         :pretty-print true}}
                        :prod {:source-paths ["src/test"]
                               :compiler {:output-to "resources/test.js"
                                          :optimizations :advanced
                                          :pretty-print true
                                          ;:output-wrapper false
                                          }}}})
