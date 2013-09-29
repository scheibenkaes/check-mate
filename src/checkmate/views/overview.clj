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
          [(keyword (format "div#%s.row" (:_id i)))
           [:div.col-md-4
            [:strong (elm/link-to (str "/show/" (:_id i)) (:name i))]]
           [:div.col-md-8
            [:small [:a {:href "#" :onclick (format "checkmate.views.overview.try_delete_list('%s', '%s');" (:_id i) (:name i))} "Delete"]]]])])]))

(defn render [lists]
  (let [grouped (group-by (comp first string/upper-case :name) lists)]
    {:body [:div
            [:h3 "All checklists"]
            [:div.row
             [:div.col-md-12
              [:div#msg]]]
            [:div.row
             [:div.col-md-12
              (render-group grouped)]]]}))
