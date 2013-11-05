(ns checkmate.core
  (:use compojure.core
        [compojure.handler :only [site]]
        [clojure.tools.nrepl.server :only [start-server stop-server]])
  (:require [cheshire.core :as json]
            [compojure.route :as route]
            [checkmate.views :as views]
            [checkmate.db :as db]
            [checkmate.util :as util]
            [checkmate.users :as users]
            [checkmate.views.new-list :as new-list]
            [checkmate.views.landing :as landing]
            [checkmate.views.show-list :as show-list]
            [checkmate.views.overview :as overview]
            [checkmate.views.login :as login]
            [ring.util.response :as resp]
            [cemerick.friend :as friend]
            [cemerick.friend.workflows :as workflow]
            [cemerick.friend.credentials :as cred]))

(def repl
  (when (System/getenv "CHECKMATE_REPL")
    (start-server :port 8079 :bind "127.0.0.1")))

(defn init []
  (db/init))

(defn shutdown []
  (when repl (stop-server repl))
  (db/shutdown))

(defn request->user [req]
  (-> req
      (friend/current-authentication)
      :identity))

(defroutes app
  (GET "/edit/:id" {{id :id} :params :as req}
       (friend/authorize #{:user}
                         (let [user (request->user req)]
                           (views/main-template (new-list/render id) :request req))))
  
  (GET "/new" req
       (friend/authorize #{:user}
                         (views/main-template (new-list/render nil) :request req)))
  
  (GET "/list/:id" {{id :id} :params :as req}
       (friend/authorize #{:user}
                         (let [user (request->user req)]
                           (json/generate-string (or (db/find-list id user) {:error "No list with this id"})))))
  
  (GET "/az" req (let [user (request->user req)]
                   (friend/authorize #{:user}
                                     (views/main-template (overview/render (db/get-all-lists user)) :request req))))
  
  (GET "/show/:id" {{id :id} :params :as req}
       (friend/authorize #{:user}
                         (let [user (request->user req)
                               list (db/find-list id user)]
                           (views/main-template (show-list/render list) :request req))))
  
  (POST "/save" {{data :data} :params :as req}
        (friend/authorize #{:user}
                          (let [c (json/parse-string data true)
                                user (request->user req)
                                existing? (:_id c)
                                saved-list (if existing?
                                             (db/update-list c user)
                                             (db/store-new-list c user))]
                            (json/generate-string saved-list))))
  
  (POST "/delete" {{data :data} :params :as req}
        (friend/authorize #{:user}
                          (let [l (json/parse-string data true)
                                user (request->user req)]
                            (json/generate-string (db/delete-list l user)))))

  (GET "/" req
       (views/main-template (landing/render) :request req))

  (GET "/login" req
       (views/main-template (login/render)))

  (GET "/logout" req
       (friend/logout* (resp/redirect "/")))
  
  (route/resources "/")
  (route/not-found "These are not the checklists you are looking for!"))

(def handler (-> app
                 (friend/authenticate {:credential-fn #(cred/bcrypt-credential-fn users/find-user %)
                                       :workflows [(workflow/interactive-form)]})
                 site))

