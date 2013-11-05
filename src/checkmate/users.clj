(ns checkmate.users
  (:require [clojure.string :as string]
            [monger.collection :as mc]
            [cemerick.friend.credentials :as cred]))

(def ^:const users-db "users")

(def ^:const user-id :username)

(def ^:const password :password)

(defn ->user [id pw]
  {user-id (string/trim id) password (cred/hash-bcrypt pw)
   :roles #{:user}})

(defn exists? [id]
  (not (empty? (mc/find-maps users-db {user-id id}))))

(defn new-user [id pw]
  (when-not (exists? id)
    (mc/insert-and-return users-db (->user id pw))))

(defn find-user [id]
  (when-let [u (mc/find-one-as-map users-db {user-id id})]
    (update-in u [:roles] #(set (map keyword %)))))


