(ns checkmate.views.login
  (:use hiccup.form))

(defn render []
  {:body [:div (form-to {:role "form"} [:post "/login"]
                        [:div.form-group
                         (text-field {:class "form-control" :placeholder "Enter username"} "username")
                         (password-field {:class "form-control" :placeholder "Enter password"} "password")
                         [:input.btn.btn-default {:type "submit" :value "Login"}]])]})
