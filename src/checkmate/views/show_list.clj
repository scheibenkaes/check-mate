(ns checkmate.views.show-list
  (:require [checkmate.views :as views]))

(defn render-buttons [l]
  [:div.btn-group-vertical
   [:button.btn.btn-default.btn-sm {:id "save-checkmarks"} [:span.glyphicon.glyphicon-floppy-save] " Save changes"]
   [:button.btn.btn-default.btn-sm {:id "check-all"} [:span.glyphicon.glyphicon-ok] " Check all"]
   [:button.btn.btn-default.btn-sm {:id "uncheck-all"} [:span.glyphicon.glyphicon-unchecked] " Uncheck all"]
   [:a.btn.btn-default.btn-sm {:href (str "/edit/" (:_id l))} [:span.glyphicon.glyphicon-edit] " Edit checklist"]])

(defn render [l]
  {:title (str (:name l) " - CheckMate")
   :body [:div
          [:h3#title "Checklist"]
          [:div.row
           [:div#list.col-md-10
            [:div [:h5 "Loading Checklist"]]]
           [:div.col-md-2 (render-buttons l)
            ]]]
   :onload (format "checkmate.views.show_list.init('%s');" (:_id l))})
