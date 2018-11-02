(ns pablito.server
  (:require
   [compojure.route]
   [compojure.core :as compojure]
   [hiccup.core :as hiccup]
   [immutant.web :as web])
  (:gen-class))

(def page
  (hiccup/html [:head
                [:title "Pablito"]]
               [:body "Hello, Pablito!"]))

(compojure/defroutes application
  (compojure/GET "/" [] page)
  (compojure.route/not-found "Not found"))

(defn -main [& args]
  (let [args-map (apply array-map args)
        port-str (or (get args-map "-p")
                     (get args-map "--port")
                     "3000")]
    (println "Starting web server on port" port-str)
    (web/run #'application {:port (Integer/parseInt port-str)})))

(comment
  (def server (-main))
  (web/stop server))
