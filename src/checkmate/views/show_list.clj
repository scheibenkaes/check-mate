(ns checkmate.views.show-list
  (:require [checkmate.views :as views]))

(defn render-buttons [l]
  [:div
   (views/bs-button "Save")
   (views/bs-button "Check all" :id "check-all")
   (views/bs-button "Uncheck all" :id "uncheck-all")
   [:a.btn.btn-default "Edit checklist"]])

(defn render [l]
  {:body [:div
          [:h3#title "Checklist"]
          [:div.row
           [:div#list.col-md-8
            [:div [:h5 "Loading Checklist"]]]
           [:div.col-md-4 (render-buttons l)
            ]]]
   :onload (format "checkmate.views.show_list.init('%s');" (:_id l))})
