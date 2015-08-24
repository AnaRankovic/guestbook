(ns guestbook.file-content-manipulation
  (:require [compojure.core :refer :all]
            [guestbook.views.layout :as layout]
            [hiccup.form :refer :all]
            [noir.session :as session]
            [clojure.java.io :as io])
  (:use clojure.java.io
        clojure.java.browse))

(defn rand-seq-elem [sequence]
  "Selects arbitrary line"
  (let [f (fn [[k old] new]
            [(inc k) (if (zero? (rand-int k)) new old)])]
    (->> sequence (reduce f [1 nil]) second)))

(defn rand-line [filename]
  "Reads content of forwarded file and calls method which chooses random line"
  (with-open [reader (io/reader filename)]
       (rand-seq-elem (line-seq reader))))

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

(defn delete-youtube-links-file []
  "Deletes file with YouTube links"
  (io/delete-file "youtube-links.txt"))