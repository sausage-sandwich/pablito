(ns pablito.server
  (:require
   [compojure.route]
   [compojure.core :as compojure]
   [environ.core :refer [env]]
   [hiccup.core :as hiccup]
   [immutant.web :as web])
  (:gen-class))

(def page
  (hiccup/html
   [:head [:title "Pablito"]]
   [:body "Hello, Pablito!"]))

(compojure/defroutes application
  (compojure/GET "/" [] page)
  (compojure.route/not-found "Not found"))

(defn run []
  (let [port-str (env :port "3000")]
    (println "Starting web server on port" port-str)
    (web/run #'application {:port (Integer/parseInt port-str)})))

(defn -main [& args]
  (run))

(comment
  (def server (-main))
  (web/stop server))
