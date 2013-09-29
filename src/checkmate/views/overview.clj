(ns checkmate.views.overview
  (:require [clojure.string :as string]
            [hiccup.element :as elm]
            [checkmate.views :as views]))

(defn render-group [group]
  (let [ordered (sort-by first group)]
    [:div
     (for [[letter items] ordered]
       [:div
        [:h4 letter]
        (for [i items]
          [:div.row
           [:div.col-md-4
            [:strong (elm/link-to (str "/show/" (:_id i)) (:name i))]]
           [:div.col-md-8
            [:small (elm/link-to "#" "Delete")]]])])]))

(defn render [lists]
  (let [grouped (group-by (comp first string/upper-case :name) lists)]
    {:body [:div
            [:h3 "All checklists"]
            [:div.row
             [:div.col-md-12
              (render-group grouped)]]]}))
