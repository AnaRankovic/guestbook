(ns guestbook.routes.home
(:require [compojure.core :refer :all]
[guestbook.views.layout :as layout]
[hiccup.form :refer :all]
[guestbook.models.db :as db]
[noir.session :as session]
[clojure.java.io :as io])
(:use clojure.java.io
      clojure.java.browse)
)

(defn rand-seq-elem [sequence]
  "Selects arbitrary element in the sequence"
  (let [f (fn [[k old] new]
            [(inc k) (if (zero? (rand-int k)) new old)])]
    (->> sequence (reduce f [1 nil]) second)))

(defn rand-line [filename]
  "Select random line from forwarded file"
  (with-open [reader (io/reader filename)]
       (rand-seq-elem (line-seq reader))))

(defn show-link-in-new-window [link]
  "Shows forwarded link in new browser window"
  (browse-url (str "https://www.youtube.com/" link)))

(defn delete-youtube-links-file []
  "Deletes file with YouTube links"
  (io/delete-file "youtube-links.txt"))

(defn home [& [name error]]
  "Defines components of home page"
  (layout/common
[:h1 "Enter your name and click the button \"Continue\"" (session/get :user)]
[:p error]
(form-to [:post "/"]
[:p "Name:  " (text-field "name" name)]
(submit-button "Continue"))))

(def break-tag
  "String after which comes YouTube link"
  (str "<h3 class=\"yt-lockup-title \"><a href=\""))

(defn write-youtube-links [yt-link]
  "Writes YouTube links in file"
  (spit "youtube-links.txt" (str yt-link "\n")  :append true))

(defn write-html-in-file []
  "Writes YouTube page source in file"
  (spit "youtube-html.txt" (slurp "https://www.youtube.com/"))
  (with-open [](doseq [line (line-seq (reader "youtube-html.txt"))]
                         (if(.contains line break-tag) 
                           (println (subs line (+ (.indexOf line break-tag) 38) (+ (.indexOf line break-tag) 58))
                            (write-youtube-links (subs line (+ (.indexOf line break-tag) 38) (+ (.indexOf line break-tag) 58))))))))

(defn goodbye-page []
  "Defines components of home page"
  (layout/common
      [:h1 "Goodbye ! "]
        [:h1 "I hope you like the YouTube jukebox!!! "]))

(defn play-link-page []
  "Defines components of page for playing random clips"
  (layout/common 
      (form-to [:post "/playclip"]
               (submit-button "Play other random clip!")
               (write-html-in-file)
                 (show-link-in-new-window (rand-line "youtube-links.txt"))
        )
      (form-to [:post "/goodbye"]
        (submit-button "I've had enough clips. Exit!")        
          (delete-youtube-links-file))))

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

(defroutes home-routes
(GET "/" [] (home))
(POST "/" [name] (welcome-page name))
(POST "/playclip" [] (play-link-page))
(POST "/goodbye" [] (goodbye-page)))