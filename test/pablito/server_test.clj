(ns pablito.server-test
  (:require
   [pablito.server :as server]
   [clojure.test :refer :all]
   [ring.mock.request :as mock]))

(deftest application-test
  (is (= (server/application (mock/request :get "/"))
         {:status 404 :body "You are too fat! 404"})))
