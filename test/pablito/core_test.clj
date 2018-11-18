(ns pablito.core-test
  (:require [clojure.test :refer :all]
            [pablito.core :refer :all]))

(deftest product->calories-test
  (testing "Black Currant Drink"
    (is (= 86.1 (product->calories {:proteins 0 :fats 0 :carbohydrates 21}))))
  (testing "Omelet with baked tomatoes"
    (is (= 187.46 (product->calories {:proteins 11 :fats 14 :carbohydrates 3}))))
  (testing "Oatmeal with baked apple and cinnamon"
    (is (= 366.852 (product->calories {:proteins 9.6 :fats 4.8 :carbohydrates 69})))))

(deftest valid-calories?-test
  (let [components {:fats [30 50] :proteins [180 220] :carbohydrates [150 180]}]
    (is (valid-calories? {:calories [1700 1900] :components components}))
    (is (not (valid-calories? {:calories [100 500] :components components})))))

(deftest dishes-by-calories-test
  (testing "dishes be calories"
    (let [menu [{:name "Напиток из черной смородины", :calories 84}
                {:name "Омлет с запеченными томатами", :calories 186}
                {:name "Овсяная каша с запеченным яблоком и корицей", :calories 353}]]
      (is (= (take 0 menu) (dishes-by-calories 50 menu)))
      (is (= (take 1 menu) (dishes-by-calories 100 menu)))
      (is (= (take 2 menu) (dishes-by-calories 300 menu)))
      (is (= (take 3 menu) (dishes-by-calories 700 menu))))))
