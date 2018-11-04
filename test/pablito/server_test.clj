(ns pablito.server-test
  (:require
   [pablito.server :as server]
   [clojure.test :refer :all]
   [ring.mock.request :as mock]
   [immutant.web]))

(deftest application-test
  (is (= 200 (:status (server/application (mock/request :get "/")))))
  (is (= 404 (:status (server/application (mock/request :get "/404"))))))

(deftest run-server-test
  (is (immutant.web/stop (server/run))))
