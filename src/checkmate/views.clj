(ns checkmate.views
  (:use [hiccup.page :only [html5 include-css include-js]])
  (:require [cemerick.friend :as friend]))

(defn main-template [{:keys [title body onload]
                      :or {title "Check-Mate" body [:h1 "body"]}}
                     & {:keys [request] :or {request {}}}]
  (let [signed-in? (friend/current-authentication request)]
    (html5 [:head
            [:title title]
            [:meta {:name "viewport" :content "width=device-width, initial-scale=1.0"}]
            (include-css "/css/bootstrap.min.css" "/css/checkmate.css")
            (include-js "/js/jquery-2.0.3.min.js"
                        "/js/bootstrap.min.js"
                        "/js/checkmate.js")
            [:link {:rel "shortcut icon" :href "//checkmate.scheibenkaes.org/favicon.ico"}]
            [:link {:href "//fonts.googleapis.com/css?family=Ubuntu" :rel "stylesheet" :type "text/css"} ]]
           [:body {:onload (or onload "")}
            [:nav.navbar.navbar-default {:role "navigation"}
             [:div.navbar-header
              [:button.navbar-toggle {:type "button" :data-toggle "collapse" :data-target ".navbar-ex1-collapse"}
               [:span.sr-only "Toggle Menu"]
               [:span.icon-bar]
               [:span.icon-bar]
               [:span.icon-bar]]
              [:a.navbar-brand {:href "/az"} [:img {:src "/icon.png" :width 48 :height 20}]]]
             [:div.collapse.navbar-collapse.navbar-ex1-collapse
              [:ul.nav.navbar-nav
               [:li [:a {:href "/az"} "A-Z"]]
               [:li [:a {:href "/new"} "New Checklist"]]
               (when-not signed-in?
                [:li [:a {:href "/"} "About"]])
               (when signed-in?
                 [:li [:a {:href "/logout"} "Logout"]])]
              [:p.navbar-text.pull-right "A ClojureCup 2013 entry by Benjamin Klüglein"]]]
            [:div.container body]])))

(defn bs-button [text & {:keys [tooltip onclick] :as options :or {tooltip "" onclick nil}}]
  [:button (merge {:onclick onclick :class "btn btn-default" :type "button" :title tooltip} options) text])

