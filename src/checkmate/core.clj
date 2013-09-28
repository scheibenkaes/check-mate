(ns checkmate.core
  (:use compojure.core)
  (:require [compojure.route :as route]
            [checkmate.views :as views]))

(defroutes app
  (GET "/" [] (views/main-template {}))
  (route/resources "/"))

