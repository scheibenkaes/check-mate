(ns checkmate.views.new-list
  (:require [enfocus.core :as ef]))

(def empty-model {:name nil
                  :items []})

(def list-model (atom empty-model))

(defn render-model! [{:keys [name items]}]
  (ef/at "#items" (ef/content (ef/html [:h1 "test"]))
         "#listname" (ef/set-prop :value name)))

(defn get-item-text []
  (ef/from "#itemtext" (ef/get-prop :value)))

(defn ^:export append-item []
  (render-model! @list-model))

(defn ^:export prepend-item []
  (js/alert "b"))

(defn ^:export init []
  (render-model! @list-model))

