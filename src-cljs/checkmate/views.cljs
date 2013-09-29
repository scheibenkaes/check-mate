(ns checkmate.views)

(defn make-panel [type title content]
  [(keyword (str "div.panel.panel-" type))
    [:div.panel-heading title]
    [:div.panel-body content]])

(defn pack-obj [obj]
  (js-obj "data" (.stringify js/JSON (clj->js obj))))

(defn unpack-obj [string]
  (js->clj (.parse js/JSON string) :keywordize-keys true))

(defn get-list [id func]
  (.getJSON js/jQuery (str "/list/" id) #(func (js->clj % :keywordize-keys true))))

