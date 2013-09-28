(ns checkmate.views.new-list
  (:use hiccup.form)
  (:require [checkmate.views :as views]))

(defn render-form []
  [:form {:role "form" :id "new-list-form"}

   [:div.form-group {:class "has-error" :id "name-group"}
    (label {:class "control-label"} "listname" "Name of the list")
    (text-field {:class "form-control" :placeholder "Please enter a unique name" :id "listname"} "listname")]
   
   [:div.form-group
    [:label {:for "itemtext"} "Checklist item text"]
    
    [:div.input-group
     [:span {:class "input-group-btn"} (views/bs-button "+" :tooltip "Prepend item to list" :onclick "checkmate.views.new_list.prepend_item();")]
     (text-field {:class "form-control" :placeholder "What needs to be checked?" :id "itemtext"} "itemtext")
     [:span {:class "input-group-btn"} (views/bs-button "++" :tooltip "Append item to list" :onclick "checkmate.views.new_list.append_item();")]
     ]]

   [:div.form-group {:id "items"}]

   [:div.form-group
    [:button {:class "btn btn-default"} "Save Checklist"]]])

(defn render []
  {:body
   [:div
    [:h3 "Create a new checklist"]
    [:div.row
     [:div.col-md-12 (render-form)]]]
   :onload "checkmate.views.new_list.init();"})
