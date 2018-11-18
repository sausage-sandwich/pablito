(ns pablito.db
  (:require
   [clojure.edn :as edn]
   [clojure.java.io :as io]
   [pablito.core :as core]))

(def db
  (with-open [reader (io/reader "resources/db.edn")]
    (->> (edn/read (java.io.PushbackReader. reader)))))

(def dishes
  (let [headers [:name :calories :proteins :fats :carbohydrates]]
    (map #(zipmap headers %) db)))
