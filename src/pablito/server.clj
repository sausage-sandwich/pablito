(ns pablito.server
  (:require
   [immutant.web :as web]
   [compojure.core :as compojure])
  (:gen-class))

(def application
  (compojure/routes
   (fn [request]
     {:status 404
      :body "You are too fat! 404"})))

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
