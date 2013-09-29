(ns checkmate.core
  (:use compojure.core
        [compojure.handler :only [site]])
  (:require monger.json
            [cheshire.core :as json]
            [compojure.route :as route]
            [checkmate.views :as views]
            [checkmate.views.new-list :as new-list]
            [checkmate.views.show-list :as show-list]
            [checkmate.views.overview :as overview]
            [monger.core :as mongo]
            [monger.collection :as mc])
  (:import [org.bson.types ObjectId]))

(defn init []
  (mongo/connect!)
  (-> "checkmate"
      mongo/get-db
      mongo/set-db!))

(defn shutdown []
  (mongo/disconnect!))

(def not-empty? (comp not empty?))

(defn unique-name? [n]
  (nil? (mc/find-one "lists" {:name n})))

(defn store-new-list [l]
  (let [{list-name :name items :items} l]
    (if (and
         (not-empty? list-name)
         (not-empty? items)
         (unique-name? list-name))
      (mc/insert-and-return "lists" l)
      {:error "There's already a list of this name, please choose a different one"})))

(defn find-list [id]
  (try
    (mc/find-map-by-id "lists" (ObjectId. id))
    (catch Exception e
      (println e)
      {:error (str "list " id " could not be found")})))

(defn get-all-lists []
  (mc/find-maps "lists"))

(defn delete-list [l]
  (let [name (:name (find-list (:id l)))
        remv (mc/remove-by-id "lists" (ObjectId. (:id l)))]
    {:_id (:id l) :name name}))

(defn update-list [l]
  (let [mongo-list (dissoc l :_id)]
    (mc/update-by-id "lists" (ObjectId. (:_id l)) mongo-list)
    (find-list (:_id l))))

(defroutes app
  (POST "/delete" {{data :data} :params}
        (let [l (json/parse-string data true)]
          (json/generate-string (delete-list l))))
  (POST "/save" {{data :data} :params}
        (let [c (json/parse-string data true)
              existing? (:_id c)
              saved-list (if existing?
                           (update-list c)
                           (store-new-list c))]
          (json/generate-string saved-list)))
  (GET "/show/:id" [id]
       (let [list (find-list id)]
         (views/main-template (show-list/render list))))
  (GET "/list/:id" [id]
       (json/generate-string (or (find-list id) {:error "No list with this id"})))
  (GET "/new" [] (views/main-template (new-list/render nil)))
  (GET "/edit/:id" [id] (views/main-template (new-list/render id)))
  (GET "/" [] (views/main-template (overview/render (get-all-lists))))
  (route/resources "/"))

(def handler (site app))

