(ns guestbook.routes.pages
  (:require [compojure.core :refer :all]
            [guestbook.views.layout :as layout]
            [hiccup.form :refer :all]
            [noir.session :as session]
            [clojure.java.io :as io])
  (:use clojure.java.io
        clojure.java.browse
        guestbook.file-content-manipulation
        guestbook.yt-links-display))

(defn home [& [name error]]
  "Defines components of home page"
  (layout/common
    [:h1 "Enter your name and click the button \"Continue\"" (session/get :user)]
    [:p error]
    (form-to [:post "/"]
             [:p "Name:  " (text-field "name" name)]
             (submit-button "Continue"))))

(defn welcome-page [name]
  "Defines components of welcome page"
  (cond
    (empty? name)
    (home name "You did not enter a name in the box below. Try again.") 
  :else
    (layout/common 
      [:h1 "Welcome, " name "!"]
    [:h2 "Do you want to play random clip from YouTube? "]
      (form-to [:post "/playclip"]
               (submit-button "Yes"))
      (form-to [:post "/"]
               (submit-button "No")))))

(defn play-link-page []
  "Defines components of page for playing random clips"
  (layout/common
    [:h1 "Choose action:"]
      (form-to [:post "/playclip"]
               (submit-button "Play other random clip!")
               (write-html-in-file)
                 (show-link-in-new-window (rand-line "youtube-links.txt")))
      (form-to [:post "/goodbye"]
        (submit-button "I've had enough clips. Exit!")        
          (delete-youtube-links-file))))

(defn goodbye-page []
  "Defines components of goodbye page"
  (layout/common
      [:h1 "Goodbye !"]
        [:h1 "I hope you like the YouTube jukebox!!! "]))

(defroutes home-routes
  (GET "/" [] (home))
  (POST "/" [name] (welcome-page name))
  (POST "/playclip" [] (play-link-page))
  (POST "/goodbye" [] (goodbye-page)))