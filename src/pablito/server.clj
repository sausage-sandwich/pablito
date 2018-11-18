(ns pablito.server
  (:require
   [compojure.route]
   [compojure.core :as compojure]
   [environ.core :refer [env]]
   [hiccup.core :as hiccup]
   [hiccup.form]
   [immutant.web :as web]
   [ring.middleware.keyword-params]
   [ring.middleware.params]

   [pablito.core :as core]
   [pablito.db :as db])
  (:gen-class))

(def index-page
  (hiccup/html
   [:head
    [:title "Pablito"]
    [:link
     {:href        "https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css"
      :rel         "stylesheet"
      :integrity   "sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO"
      :crossorigin "anonymous"}]
    [:link {:href "css/styles.css" :rel "stylesheet" :type "text/css"}]]
   [:body
    [:div.container
     [:div.jumbotron
      (hiccup.form/form-to
       [:post "/"]
       [:div.form-group
        [:label "Calories"]
        [:input.form-control {:name :calories :placeholder "2000-2500"}]]
       [:div.form-group
        [:label "Proteins"]
        [:input.form-control {:name :proteins}]]
       [:div.form-group
        [:label "Fats"]
        [:input.form-control {:name :fats}]]
       [:div.form-group
        [:label "Carbohydrates"]
        [:input.form-control {:name :carbohydrates}]]
       [:button.btn.btn-primary {:type :sumbit} "Feed me!"])]]]))

(compojure/defroutes routes
  (compojure/GET "/" [] index-page)
  (compojure/POST "/" []
    (fn [{{calories :calories} :params :as req}]
      (str (core/dishes-by-calories (Integer/parseInt calories)
                                    (shuffle pablito.db/dishes))))))

(def application
  (compojure/routes
   (-> routes
       (ring.middleware.keyword-params/wrap-keyword-params)
       (ring.middleware.params/wrap-params))
   (compojure.route/resources "/", {:root "static"})
   (compojure.route/not-found "Not found")))

(defn run []
  (let [port-str (env :port "3000")
        host-str "0.0.0.0"]
    (println "Starting web server on port" port-str)
    (web/run #'application {:port (Integer/parseInt port-str)
                            :host host-str})))

(defn -main [& args]
  (run))

(comment
  (def server (-main))
  (web/stop server))
