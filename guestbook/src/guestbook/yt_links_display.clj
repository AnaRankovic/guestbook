(ns guestbook.yt-links-display
  (:use clojure.java.io
      clojure.java.browse))

(defn show-link-in-new-window [link]
  "Shows forwarded link in new browser window"
  (browse-url (str "https://www.youtube.com/" link)))
