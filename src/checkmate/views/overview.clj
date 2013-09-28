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
          [:div (elm/link-to (str "/show/" (:_id i)) (:name i))])])]))

(defn render [lists]
  (let [grouped (group-by (comp first string/upper-case :name) lists)]
    {:body [:div
            [:h3 "All checklists"]
            [:div.row
             [:div.col-md-12
              (render-group grouped)]]]}))