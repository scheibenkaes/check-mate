(ns checkmate.core
  (:use compojure.core
        [compojure.handler :only [site]]
        monger.collection)
  (:require [clojure.data.json :as json]
            [compojure.route :as route]
            [checkmate.views :as views]
            [checkmate.views.new-list :as new-list]
            [monger.core :as mongo]))

(defn init []
  (mongo/connect!)
  (-> "checkmate"
      mongo/get-db
      mongo/set-db!))

(defn shutdown []
  (mongo/disconnect!))

(defroutes app
  (GET "/save" []
       (str (:_id (insert-and-return "cm-test" {:name "test"}))))
  (POST "/save" {{data :data} :params}
        (let [c (json/read-str data :key-fn keyword)]
          (println c)))
  (GET "/" [] (views/main-template (new-list/render)))
  (route/resources "/"))

(def handler (site app))

