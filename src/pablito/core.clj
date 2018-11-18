(ns pablito.core)

; Energe density in kcal/g
(def energy-density
  {:fats 9.29
   :proteins 4.1
   :carbohydrates 4.1})

(defn micronutrient->calories
  [micronutrient weight]
  (* (get energy-density micronutrient) weight))

(defn product->calories
  [product]
  (reduce + (map #(apply micronutrient->calories %) product)))

(defn valid-calories?
  [{:keys [:calories :components]}]
  (let [min-calories (product->calories (into {} (map (fn [[component value]] (vector component (first  value))) components)))
        max-calories (product->calories (into {} (map (fn [[component value]] (vector component (second value))) components)))]
    (and (<= (apply min calories) max-calories)
         (>= (apply max calories) min-calories))))
