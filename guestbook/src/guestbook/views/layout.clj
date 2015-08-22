(ns guestbook.views.layout
  (:require [hiccup.page :refer [html5 include-css]]))

(defn common [& body]
  (html5
    [:head
     [:title "YouTube random jukebox"]
     (include-css "/css/screen.css")]
    [:body body
     (include-css "/css/screen.css")]))
