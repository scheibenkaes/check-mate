(ns checkmate.views.new-list
  (:require [enfocus.core :as ef]
            [enfocus.events :as ev]))

(def empty-model {:name nil
                  :items []})

(def list-model (atom empty-model))

(defn render-items [items]
  (if-not (empty? items)
    [:ol
     (for [i items] [:li (:text i)])]
    [:small "No items added yet."]))

(def not-empty? (comp not empty?))

(defn render-model! [{:keys [name items]}]
  (ef/at "#items" (ef/content (ef/html (render-items items)))
         "#listname" (ef/set-prop :value name)
         "#name-group" (if (empty? name)
                         (ef/add-class "has-error")
                         (ef/remove-class "has-error"))))

(defn get-item-text []
  (ef/from "#itemtext" (ef/get-prop :value)))

(defn get-list-name []
  (ef/from "#listname" (ef/get-prop :value)))

(defn add-item-to-list-model [side]
  (let [f (if (= side :end) conj #(apply vector %2 %1))
        new-item-text (get-item-text)]
    (when-not (empty? new-item-text)
      (swap! list-model update-in [:items] f {:text new-item-text})
      (ef/at "#itemtext" (ef/set-prop :value nil)
             "#itemtext" (ef/focus)))))

(defn validate-model [{name :name items :items}]
  (every? not-empty? [name items]))

(defn ^:export reset-view []
  (reset! list-model empty-model))

(defn ^:export append-item []
  (add-item-to-list-model :end))

(defn ^:export prepend-item []
  (add-item-to-list-model :start))

(defn ^:export init []
  (add-watch list-model ::autoupdate (fn [_k _r _old _new]
                                       (render-model! _new)))
  (ef/at "#listname" (ev/listen :change (fn [_]
                                          (swap! list-model assoc :name (get-list-name)))))
  (reset-view))

(defn pack-obj [obj]
  (js-obj "data" (.stringify js/JSON (clj->js obj))))

(defn ^:export try-save-list []
  (let [model @list-model
        valid? (validate-model model)]
    (if valid?
      (js/alert (.post js/jQuery "/save" (pack-obj model) (fn [e] (js/alert e))))
      (js/alert "Not valid"))))

