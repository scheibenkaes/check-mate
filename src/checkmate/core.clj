(ns checkmate.core
  (:use compojure.core
        [compojure.handler :only [site]])
  (:require monger.json
            [cheshire.core :as json]
            [compojure.route :as route]
            [checkmate.views :as views]
            [checkmate.views.new-list :as new-list]
            [monger.core :as mongo]
            [monger.collection :as mc]))

(defn init []
  (mongo/connect!)
  (-> "checkmate"
      mongo/get-db
      mongo/set-db!))

(defn shutdown []
  (mongo/disconnect!))

(def not-empty? (comp not empty?))

(defn unique-name? [n]
  (nil? (mc/find-one "lists" {:name n})))

(defn store-new-list [l]
  (println l)
  (let [{list-name :name items :items} l]
    (if (and
         (not-empty? list-name)
         (not-empty? items)
         (unique-name? list-name))
      (mc/insert-and-return "lists" l)
      {:error "list name not unique please choose a different one"})))

(defroutes app
  (POST "/save" {{data :data} :params}
        (let [c (json/parse-string data true)]
          (json/generate-string (store-new-list c))))
  (GET "/" [] (views/main-template (new-list/render)))
  (route/resources "/"))

(def handler (site app))

