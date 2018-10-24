(ns pablito.core-test
  (:require [clojure.test :refer :all]
            [pablito.core :refer :all]))

(deftest product->calories-test
  (is (= 63.265 (product->calories {:fats 3.5 :proteins 2.8 :carbohydrates 4.7}))))

(deftest valid-calories?-test
  (let [components {:fats [30 50] :proteins [180 220] :carbohydrates [150 180]}]
    (is (valid-calories? {:calories [1700 1900] :components components}))
    (is (not (valid-calories? {:calories [100 500] :components components})))))
