(ns pablito.server-test
  (:require
   [pablito.server :as server]
   [clojure.test :refer :all]
   [ring.mock.request :as mock]))

(deftest application-test
  (is (= "<head><title>Pablito</title></head><body>Hello, Pablito!</body>"
         (:body (server/application (mock/request :get "/")))))
  (is (= "Not found"
         (:body (server/application (mock/request :get "/404"))))))
