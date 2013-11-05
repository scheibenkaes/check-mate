(defproject checkmate "0.2.0"
  :description "A checklist app for the web"
  :url "http://checkmate.clojurecup.com/"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/tools.nrepl "0.2.3"]
                 [cheshire "5.2.0"]
                 [compojure "1.1.5"]
                 [domina "1.0.2-SNAPSHOT"]
                 [enfocus "2.0.0-SNAPSHOT"]
                 [com.cemerick/friend "0.2.0"]
                 [hiccup "1.0.4"]
                 [ring/ring-core "1.2.0"]
                 [com.novemberain/monger "1.7.0-beta1"]]
  :plugins [[lein-ring "0.8.7"]
            [lein-cljsbuild "0.3.4"]]
  :cljsbuild {
              :builds [{:id :dev
                        :source-paths ["src-cljs"]
                        :compiler {
                                   :output-to "resources/public/js/checkmate.js"
                                   :optimizations :whitespace
                                   :pretty-print true}}]}
  :ring {:handler checkmate.core/handler
         :init checkmate.core/init
         :destroy checkmate.core/shutdown})
