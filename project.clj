(defproject checkmate "0.1.0-SNAPSHOT"
  :description "A checklist app for the web"
  :url "http://checkmate.clojurecup.com/"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/clojurescript "0.0-1909"]
                 [org.clojure/core.async "0.1.222.0-83d0c2-alpha"]
                 [org.clojure/data.json "0.2.3"]
                 [compojure "1.1.5"]
                 [domina "1.0.2-SNAPSHOT"]
                 [enfocus "2.0.0-SNAPSHOT"]
                 [hiccup "1.0.4"]
                 [com.novemberain/monger "1.7.0-beta1"]]
  :plugins [[lein-ring "0.8.7"]
            [lein-cljsbuild "0.3.3"]]
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
