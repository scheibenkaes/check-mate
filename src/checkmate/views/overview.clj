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
          (let [num-done (count (filter :checked? (:items i)))
                num-all (count (:items i))
                cls-link (keyword (if-not (= num-all num-done) "strong" "strong.checked"))]
            [(keyword (format "div#%s.row" (:_id i)))
             [:div.col-md-4
              [cls-link (elm/link-to (str "/show/" (:_id i)) (:name i)) " " [:span.badge (str num-done "/" num-all)]]]
             [:div.col-md-8
              [:div.btn-group
               [:a {:class "btn btn-default btn-xs" :href (str "/edit/" (:_id i))} [:span.glyphicon.glyphicon-edit] "Edit"]
               [:a {:class "btn btn-default btn-xs":href "#" :onclick (format "checkmate.views.overview.try_delete_list('%s', '%s');" (:_id i) (:name i))} [:span.glyphicon.glyphicon-trash] "Delete"]]]]))])]))

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
