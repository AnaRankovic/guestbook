(ns guestbook.handler
  (:require [compojure.core :refer [defroutes routes]]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.file-info :refer [wrap-file-info]]
            [hiccup.middleware :refer [wrap-base-url]]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [guestbook.routes.pages :refer [home-routes]]
            [noir.session :as session]
            [ring.middleware.session.memory :refer [memory-store]]
            [noir.validation :refer [wrap-noir-validation]]))

(defn init []
  (println "YouTube random jukebox is starting"))

(defn destroy []
  (println "YouTube random jukebox is shutting down"))

(def app
  (->
    (handler/site
      (routes 
        home-routes))
    (session/wrap-noir-session
      {:store (memory-store) })))