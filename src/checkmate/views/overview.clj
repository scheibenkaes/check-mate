(ns checkmate.views.overview
  (:require [clojure.string :as string]
            [hiccup.element :as elm]
            [checkmate.views :as views]))

(defn render-group [group]
  (let [ordered (sort-by first group)]
    [:div
     (for [[letter items] ordered]
       [:div
        [:h3 {:id letter} letter]
        (for [i items]
          (let [num-done (count (filter :checked? (:items i)))
                num-all (count (:items i))
                cls-link (keyword (if-not (= num-all num-done) "strong" "strong.checked"))]
            [(keyword (format "div#%s.row" (:_id i)))
             [:p
              [:div.col-md-8.col-xs-6
               [cls-link (elm/link-to (str "/show/" (:_id i)) (str (:name i) "  (" num-done "/" num-all ")"))]]
              [:div.col-md-4.col-xs-6
               [:div.btn-group
                [:a {:class "btn btn-default btn-xs" :href (str "/edit/" (:_id i))} [:span.glyphicon.glyphicon-edit] "Edit"]
                [:a {:class "btn btn-default btn-xs":href "#" :onclick (format "checkmate.views.overview.try_delete_list('%s', '%s');" (:_id i) (:name i))} [:span.glyphicon.glyphicon-trash] "Delete"]]]]]))])]))

(defn render [lists]
  (let [grouped (group-by (comp first string/upper-case :name) lists)
        letters (map first grouped)]
    {:body [:div.container
            [:h3 "All checklists"]
            [:div.row
             [:div.col-md-4]
             [:div.col-md-4
              [:div.btn-toolbar
              [:div.btn-group
               (for [l (sort letters)]
                 [:a.btn.btn-default {:href (str "#" l)} l])]]]
             [:div.col-md-4]]
            [:div.row
             [:div.col-md-12
              [:div#msg]]]
            [:div.row
             [:div.col-md-12
              (render-group grouped)]]
            [:div.row
             [:div.col-md-4.col-xs-5]
             [:div.col-md-4.col-xs-4
              [:a {:href "#top"} [:span.glyphicon.glyphicon-arrow-up]]]]
            ]}))
