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

(defn save-list [checklist callback]
  (.post js/jQuery "/save" (pack-obj checklist)
                       (fn [e] (let [resp (unpack-obj e)]
                                (callback resp)))))

(defn ^:export warn-of-unsaved-changes []
  (set! (.-onbeforeunload js/window) (fn []
                                       "You may have unsaved changes, they'll be lost if you proceed.")))

