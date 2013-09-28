(ns checkmate.core
  (:use compojure.core)
  (:require [compojure.route :as route]))

(defroutes app
  (GET "/" [] "Hello World")
  (route/resources "/"))

