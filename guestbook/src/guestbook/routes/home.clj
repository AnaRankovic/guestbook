(ns guestbook.routes.home
(:require [compojure.core :refer :all]
[guestbook.views.layout :as layout]
[hiccup.form :refer :all]
[guestbook.models.db :as db]
[noir.session :as session]))

(defn show-guests []
[:ul.guests
(for [{:keys [message name timestamp]} (db/read-guests)]
[:li
[:blockquote message]
[:p "-" [:cite name]]
[:time timestamp]])])

(defn home [& [name error]]
(layout/common
[:h1 "Unesite ime i kliknite dugme Nastavi " (session/get :user)]
[:p error]
(form-to [:post "/"]
[:p "Ime:  " (text-field "name" name)]
(submit-button "Nastavi"))))

(defn save-message [name message]
(cond
(empty? name)
(home name message "Some dummy forgot to leave a name")
(empty? message)
(home name message "Don't you have something to say?")
:else
(do
(db/save-message name message)
(home))))

(defn format-time [timestamp]
(-> "dd/MM/yyyy"
(java.text.SimpleDateFormat.)
(.format timestamp)))

(defn show-guests []
[:ul.guests
(for [{:keys [message name timestamp]} (db/read-guests)]
[:li
[:blockquote message]
[:p "-" [:cite name]]
[:time (format-time timestamp)]])])

(defn pocetna [name]
  (cond
    (empty? name)
    (home name "Niste uneli ime. Pokusajte ponovo.") 
  :else
    (layout/common 
      [:h1 "Dobrodosli, " name "!"]
    [:h2 "Izaberite adresu web stranice ciji sadrzaj zelite da parsirate: "]
      (form-to [:post "/"]
             [:p "Ime:  " (text-field "name" name)]
(submit-button "Nastavi")))))

(defn save-message [name message]
(cond
(empty? name)
(home name "Some dummy forgot to leave a name")
(empty? message)
(home name message "Don't you have something to say?")
:else
(do
(db/save-message name message)
(home)))
)

(defroutes home-routes
(GET "/" [] (home))
(POST "/" [name] (pocetna name)))

