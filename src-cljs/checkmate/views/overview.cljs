(ns checkmate.views.overview
  (:require [checkmate.views :as views]
            [enfocus.core :as ef]
            [enfocus.events :as ev]))

(defn on-delete [id list]
  (let [id-sel (str "#" id)]
    (ef/at "#msg" (ef/content
                   (ef/html
                    (views/make-panel "success" "Success" (str "Successfully deleted '" (:name list) "'"))))
           id-sel (ef/remove-node))))

(defn ^:export try-delete-list [id name]
  (when (.confirm js/window (str "Delete list '" name "'?"))
    (.post js/jQuery "/delete" (views/pack-obj {:id id})
           (fn [d]
             (let [response (views/unpack-obj d)]
               (if (:error response)
                 (js/alert (:error response))
                 (on-delete id response)))))))

(defn redirect-to-list [id]
  (set! (.-location js/window) (str "/show/" id)))

(defn on-quick-select [elm]
  (let [ignore-empty-s #(when-not (empty? %) %)
        val (-> elm (.-target) (.-value) (ignore-empty-s))]
    (when val
      (redirect-to-list val))))

(defn init-quick-select []
  (ef/at "#quick-select"
         (ev/listen :change on-quick-select)))

(defn ^:export init []
  (init-quick-select))
