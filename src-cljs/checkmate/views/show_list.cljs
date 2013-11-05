(ns checkmate.views.show-list
  (:require [enfocus.core :as ef]
            [enfocus.events :as ev]
            [checkmate.views :as views]))

(def list-model (atom nil))

(defn render-items [items]
  (ef/html (for [i items]
             (let [attrs {:type "checkbox" :name (:text i)}
                   attrs (if (:checked? i)
                           (assoc attrs :checked true)
                           attrs)]
               [:div.checkbox [:label {:id (str "l" (:text i))} [:input.cb attrs]
                               [(if (:checked? i) :span.checked :span) (:text i)]]]))))

(defn set-checked [name checked?]
  (swap! list-model update-in [:items] #(map (fn [item]
                                               (if (= name (:text item))
                                                 (assoc item :checked? checked?)
                                                 item)) %)))

(defn render-list [{:keys [name items id]}]
  (ef/at "#list" (ef/content (render-items items))
         "#title" (ef/content name)))

(defn on-save [e]
  (views/save-list @list-model (fn [{error :error :as list}]
                                 (if error
                                   (js/alert error)
                                   (.reload js/location true)))))

(defn hook-up-buttons []
  (ef/at "#uncheck-all" (ev/listen :click (fn [_] (swap! list-model update-in [:items] #(map (fn [i] (dissoc i :checked?)) %))))
         "#check-all" (ev/listen :click (fn [_] (swap! list-model update-in [:items] #(map (fn [i] (assoc i :checked? true)) %))))
         "#save-checkmarks" (ev/listen :click on-save)))

(defn init-checkboxes []
  (ef/at "input[type=checkbox]" (ev/listen :change (fn [e]
                                                     (let [target (.-currentTarget e)
                                                           n (.-name target)
                                                           checked? (.-checked target)]
                                                       (set-checked n checked?))))))

(defn model-loaded [model]
  (reset! list-model model))

(defn ^:export init [id]
  (hook-up-buttons)
  (add-watch list-model ::auto (fn [_ __ ___ n]
                                 (render-list n)
                                 (init-checkboxes)))
  (.getJSON js/jQuery (str "/list/" id)
            (fn [json]
              (let [clj (js->clj json :keywordize-keys true)]
                (if (:error clj)
                  (js/alert (str "Cannot find list " id))
                  (model-loaded clj))))))
