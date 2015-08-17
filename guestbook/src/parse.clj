(ns parse
  (:require [compojure.core :refer :all]
            [guestbook.views.layout :as layout]
            [hiccup.form :refer :all]
            [guestbook.models.db :as db]
            [noir.session :as session])
  :use clojure.java.io)

(def readXml
  (slurp "https://www.youtube.com/"))

(def readFileLineByLine
  (with-open [rdr (reader (slurp "https://www.youtube.com/"))]
  (doseq [line (line-seq rdr)]
    (println line))))

(with-open [rdr (reader "newfile.txt")]
     (doseq [line (line-seq rdr)]
       (if(.contains line "yt-lockup-title ") (spit "novo.txt" line))))


