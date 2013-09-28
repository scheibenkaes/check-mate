(ns checkmate.views.new-list
  (:use hiccup.form)
  (:require [checkmate.views :as views]))

(defn render-form []
  [:form {:role "form"}

   [:div.form-group {:class "has-error" :id "name-group"}
    (label {:class "control-label"} "listname" "Name of the list")
    (text-field {:class "form-control" :placeholder "Please enter a unique name"} "listname")]
   
   [:div.form-group
    [:label {:for "itemtext"} "Checklist item text"]
    
    [:div.input-group
     [:span {:class "input-group-btn"} (views/bs-button "+" :tooltip "Prepend item to list" :onclick "alert(1);")]
     (text-field {:class "form-control" :placeholder "What needs to be checked?"} "itemtext")
     [:span {:class "input-group-btn"} (views/bs-button "++" :tooltip "Append item to list")]
     ]]

   [:div.form-group {:id "items"} [:small "No items added yet."]]

   [:div.form-group
    [:button {:class "btn btn-default"} "Save Checklist"]
    ]])

(defn render []
  [:div
   [:h1 "Create a new check list"]
   [:div.row
    [:div.col-md-12 (render-form)]]])
