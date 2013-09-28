(ns checkmate.views.new-list
  (:require [enfocus.core :as ef]))

(def empty-model {:name nil
                  :items []})

(def list-model (atom empty-model))

(defn render-items [items]
  (if-not (empty? items)
    [:ol
     (for [i items] [:li (:text i)])]
    [:small "No items added yet."]))

(defn render-model! [{:keys [name items]}]
  (ef/at "#items" (ef/content (ef/html (render-items items)))
         "#listname" (ef/set-prop :value name)))

(defn get-item-text []
  (ef/from "#itemtext" (ef/get-prop :value)))

(defn add-item-to-list-model [side]
  (let [f (if (= side :end) conj #(apply vector %2 %1))
        new-item-text (get-item-text)]
    (when-not (empty? new-item-text)
      (swap! list-model update-in [:items] f {:text new-item-text})
      (ef/at "#itemtext" (ef/set-prop :value nil)
             "#itemtext" (ef/focus)))))

(defn ^:export reset-view []
  (reset! list-model empty-model))

(defn ^:export append-item []
  (add-item-to-list-model :end))

(defn ^:export prepend-item []
  (add-item-to-list-model :start))

(defn ^:export init []
  (add-watch list-model ::autoupdate (fn [_k _r _old _new]
                                       (render-model! _new)))
  (reset-view))

