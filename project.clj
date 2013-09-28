(defproject checkmate "0.1.0-SNAPSHOT"
  :description "A checklist app for the web"
  :url "http://checkmate.clojurecup.com/"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [compojure "1.1.5"]
                 [enfocus "1.0.1"]
                 [hiccup "1.0.4"]]
  :plugins [[lein-ring "0.8.7"]
            [lein-cljsbuild "0.3.3"]]
  :ring {:handler checkmate.core/app})
