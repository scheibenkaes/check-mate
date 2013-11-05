(ns checkmate.db
  (:require monger.json
            [monger.core :as mongo]
            [monger.collection :as mc]
            [checkmate.util :as util])
  (:import [org.bson.types ObjectId]))

(defn init []
  (mongo/connect!)
  (-> "checkmate"
      mongo/get-db
      mongo/set-db!))

(defn shutdown []
  (mongo/disconnect!))

(def ^:const lists-db "lists")

(defn unique-name? [n user]
  (nil? (mc/find-one lists-db {:name n :username user})))

(defn store-new-list [l user]
  (let [{list-name :name items :items} l
        l (assoc l :username user)]
    (if (and
         (util/not-empty? list-name)
         (util/not-empty? items)
         (unique-name? list-name user))
      (mc/insert-and-return lists-db l)
      {:error "There's already a list of this name, please choose a different one"})))

(defn find-list [id user]
  (try
    (first
     (mc/find-maps lists-db {:_id (ObjectId. id) :username user}))
    (catch Exception e
      (println e)
      {:error (str "list " id " could not be found")})))

(defn get-all-lists [user]
  (mc/find-maps lists-db {:username user}))

(defn delete-list [l user]
  (if-let [name (:name (find-list (:id l) user))]
    (do
      (mc/remove-by-id lists-db (ObjectId. (:id l)))
      {:_id (:id l) :name name})
    {:error (format "the list you tried to delete cannot be found or is not yours")}))

(defn update-list [l user]
  (let [mongo-list (dissoc l :_id)]
    (mc/update lists-db {:_id (ObjectId. (:_id l)) :username user} mongo-list)
    (find-list (:_id l) user)))

