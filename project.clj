(defproject pablito "0.1.0-SNAPSHOT"
  :description "Pablito. The code of Tasty and Healthy Food"
  :dependencies [
    [org.clojure/clojure "1.10.0-beta4"]
    [org.immutant/web "2.1.10"]
    [compojure "1.6.1"]
    [hiccup "1.0.5"]
  ]
  :main pablito.server
  :target-path "target/%s"
  :profiles {
    :dev {
      :dependencies [
        [environ        "1.1.0"]
        [ring/ring-mock "0.3.2"]
      ]
      :plugins [
        [lein-cloverage "1.0.13"]
      ]
    }
    :uberjar {:aot :all}
  })
