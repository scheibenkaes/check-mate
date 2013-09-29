(ns checkmate.views.new-list
  (:use hiccup.form)
  (:require [checkmate.views :as views]))

(defn render-form []
  [:div {:role "form" :id "new-list-form"}
   [:div#msg]
   [:div.form-group {:id "name-group"}
    (label {:class "control-label"} "listname" "Name of the list")
    (text-field {:class "form-control" :placeholder "Please enter a unique name" :id "listname"} "listname")]
   
   [:div.form-group
    [:label {:for "itemtext"} "Checklist item text"]
    
    [:div.input-group
     [:span {:class "input-group-btn"}
      (views/bs-button "+" :tooltip "Prepend item to list" :onclick "checkmate.views.new_list.prepend_item();")]
     (text-field {:class "form-control" :placeholder "What needs to be checked/done?" :id "itemtext"} "itemtext")
     [:span {:class "input-group-btn"}
      (views/bs-button "++" :tooltip "Append item to list" :onclick "checkmate.views.new_list.append_item();")]
     ]]
   [:div.form-group [:hr]]
   [:div.form-group {:id "items"}]
   
   [:div.btn-group
    (views/bs-button "Save Checklist" :onclick "checkmate.views.new_list.try_save_list();")
    (views/bs-button "Reset Checklist" :onclick "checkmate.views.new_list.reset_view();")]])

(defn render [id]
  {:title "Check-Mate - Set up a checklist"
   :body
   [:div
    [:h3 "Set up a checklist"]
    [:div.row
     [:div.col-md-12
      (render-form)]]]
   :onload (format "checkmate.views.new_list.init(%s);" (if id (format "'%s'" id) id))})
