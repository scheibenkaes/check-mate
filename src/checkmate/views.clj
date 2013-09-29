(ns checkmate.views
  (:use [hiccup.page :only [html5 include-css include-js]]))

(defn main-template [{:keys [title body onload]
                      :or {title "Check-Mate" body [:h1 "body"]}}]
  (html5 [:head
          [:title title]
          [:meta {:name "viewport" :content "width=device-width, initial-scale=1.0"}]
          (include-css "/css/bootstrap.min.css" "/css/checkmate.css")
          (include-js "//code.jquery.com/jquery-2.0.3.min.js" "/js/bootstrap.min.js" "/js/checkmate.js")
          [:link {:rel "shortcut icon":href "http://checkmate.clojurecup.com/favicon.ico"}]
          [:link {:href "http://fonts.googleapis.com/css?family=Ubuntu" :rel "stylesheet" :type "text/css"} ]]
         [:body {:onload (or onload "")}
          [:nav.navbar.navbar-default {:role "navigation"}
           [:div.navbar-header
            [:button.navbar-toggle {:type "button" :data-toggle "collapse" :data-target ".navbar-ex1-collapse"}
             [:span.sr-only "Toggle Menu"]
             [:span.icon-bar]
             [:span.icon-bar]
             [:span.icon-bar]]
            [:a.navbar-brand {:href "/"} "Check-Mate"]]
           [:div.collapse.navbar-collapse.navbar-ex1-collapse
            [:ul.nav.navbar-nav
             [:li [:a {:href "/az"} "A-Z"]]
             [:li [:a {:href "/new"} "New Checklist"]]]
            [:p.navbar-text.pull-right "A ClojureCup entry by Benjamin Klüglein"]]]
          [:div.container body]]))

(defn landing-view []
  {:body
   [:div.jumbotron
    [:div.container
     [:h1 "Check-Mate" [:small "A tiny yet friendly checklist app."]]
     [:p "If you came here hoping for an app about chess, I'm sorry. If not go ahead and improve your day with some checklists. No matter what brought you here, you should read Atul Gawande's great book " [:a {:href "https://en.wikipedia.org/wiki/The_Checklist_Manifesto"} "'The Checklist Manifesto'."] " You definitely will feel the urge for some checklists afterwards. :-)"]
     [:p [:div.btn-toolbar
          [:div.btn-group
           [:a.btn.btn-default {:href "/new"} "Create a new checklist"]
           [:a.btn.btn-default {:href "/az"} "See all existing checklists"]]
          [:div.btn-group [:a.btn.btn-primary {:href "http://clojurecup.com/app.html?app=checkmate"} "Vote for Check-Mate"]]]]]]})

(defn bs-button [text & {:keys [tooltip onclick] :as options :or {tooltip "" onclick nil}}]
  [:button (merge {:onclick onclick :class "btn btn-default" :type "button" :title tooltip} options) text])

