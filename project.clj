(defproject pablito "0.1.0-SNAPSHOT"
  :description "Pablito. The code of Tasty and Healthy Food"
  :dependencies [[org.clojure/clojure "1.10.0-beta4"]]
  :main ^:skip-aot pablito.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
