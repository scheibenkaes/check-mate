(ns checkmate.views.overview
  (:require [clojure.string :as string]))

(defn render-group [group]
  (let [ordered (sort-by first group)]
    [:div
     (for [[letter items] ordered]
       [:div
        [:h4 (string/upper-case letter)]
        (for [i items]
          [:div (:name i)])])]))

(defn render [lists]
  (let [grouped (group-by (comp first :name) lists)]
    {:body [:div
            [:h3 "All checklists"]
            (render-group grouped)]}))
