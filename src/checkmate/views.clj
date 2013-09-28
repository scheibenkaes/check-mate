(ns checkmate.views
  (:use [hiccup.page :only [html5 include-css include-js]]))

(defn main-template [{:keys [title body onload]
                      :or {title "Check-Mate" body [:h1 "body"]}}]
  (html5 [:head
          [:title title]
          [:meta {:name "viewport" :content "width=device-width, initial-scale=1.0"}]
          (include-css "/css/bootstrap.min.css" "/css/checkmate.css")
          (include-js "//code.jquery.com/jquery-2.0.3.min.js" "/js/bootstrap.min.js" "/js/checkmate.js")]
         [:body {:onload (or onload "")}
          [:nav.navbar.navbar-default {:role "navigation"}
           [:div.navbar-header
            [:button.navbar-toggle {:type "button" :data-toggle "collapse" :data-target ".navbar-ex1-collapse"}
             [:span.sr-only "Toggle"]
             [:span.icon-bar]
             [:span.icon-bar]
             [:span.icon-bar]]
            [:a.navbar-brand {:href "/"} "Check-Mate"]]
           [:div.collapse.navbar-collapse.navbar-ex1-collapse
            [:ul.nav.navbar-nav
             [:li [:a {:href "/new"} "New Checklist"]]
             [:li [:a {:href "/"} "Show all"]]]]]
          [:div.container body]]))

(defn bs-button [text & {:keys [tooltip onclick] :as options :or {tooltip "" onclick nil}}]
  [:button (merge {:onclick onclick :class "btn btn-default" :type "button" :title tooltip} options) text])
