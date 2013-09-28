(ns checkmate.core
  (:use compojure.core)
  (:require [compojure.route :as route]
            [checkmate.views :as views]
            [checkmate.views.new-list :as new-list]))

(defroutes app
  (GET "/" [] (views/main-template (new-list/render)))
  (route/resources "/"))

