(ns checkmate.views
  (:use [hiccup.page :only [html5 include-css include-js]]))

(defn main-template [{:keys [title body] :or {title "Check-Mate" body [:h1 "body"]}}]
  (html5 [:head
          [:title title]
          [:meta {:name "viewport" :content "width=device-width, initial-scale=1.0"}]
          (include-css "css/bootstrap.min.css")
          (include-js "//code.jquery.com/jquery.js" "js/bootstrap.min.js")]
         [:body [:div.container body]]
         ))
