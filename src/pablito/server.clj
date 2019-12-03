(ns pablito.server
  (:require
   [compojure.route]
   [compojure.core :as compojure]
   [environ.core :refer [env]]
   [hiccup.core :as hiccup]
   [hiccup.form]
   [immutant.web :as web]
   [ring.middleware.oauth2]
   [ring.middleware.defaults :refer
    [wrap-defaults secure-site-defaults site-defaults]])
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
  (compojure/POST "/" [] index-page))

(defn wrap-oauth2
  [handler]
  (ring.middleware.oauth2/wrap-oauth2
   handler
   {:instagram
    {:authorize-uri    "https://api.instagram.com/oauth/authorize"
     :access-token-uri "https://api.instagram.com/oauth/access_token"
     :client-id        (env :instagram-client-id)
     :client-secret    (env :instagram-client-secret)
     :scopes           ["basic"]
     :launch-uri       "/oauth2/instagram"
     :redirect-uri     "/oauth2/instagram/callback"
     :landing-uri      "/"}}))

(def application
  (compojure/routes
   (->
    routes
    (wrap-oauth2)
    (wrap-defaults (->
                    (get {:production secure-site-defaults}
                         (env :ring-dev)
                         site-defaults)
                    (assoc-in [:session :cookie-attrs :same-site] :lax))))
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
