(ns checkmate.core
  (:use compojure.core
        monger.collection)
  (:require [compojure.route :as route]
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
  (GET "/" [] (views/main-template (new-list/render)))
  (route/resources "/"))

