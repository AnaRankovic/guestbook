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

;Funkcije za random biranje linije iz fajla:
(defn rand-seq-elem [sequence]
  (let [f (fn [[k old] new]
            [(inc k) (if (zero? (rand-int k)) new old)])]
    (->> sequence (reduce f [1 nil]) second)))

(defn rand-line [filename]
     (with-open [reader (io/reader filename)]
       (rand-seq-elem (line-seq reader))))

;Prikazivanje u novom prozoru
(defn prikaziRandomOdabraniLink [link]
  (browse-url (str "https://www.youtube.com/" link))
)

;Brise ana.txt
(defn brisi_filoveKadPrekidasApp []
  (io/delete-file "ana.txt")
  (io/delete-file "spit.txt")
  )

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

(def aaa (str "<h3 class=\"yt-lockup-title \"><a href=\""))

(defn upisi [aaaa]
              (spit "ana.txt" (str aaaa "\n")  :append true))

(defn upisiHtmlUFajl []
  (spit "spit.txt" (slurp "https://www.youtube.com/"))
  (with-open [](doseq [line (line-seq (reader "spit.txt"))]
                         (if(.contains line aaa) 
                           (println (subs line (+ (.indexOf line aaa) 38) (+ (.indexOf line aaa) 58))
                            (upisi (subs line (+ (.indexOf line aaa) 38) (+ (.indexOf line aaa) 58))))
                         ))))

(defn nova []
(layout/common
[:h1 "Nova"]
(form-to [:post "/b"]
(submit-button "Izlistaj preporucen sadrzaj")
(text-area {:rows 100 :cols 180} "message" 
           (slurp "https://www.youtube.com/")))))

(defn listaLinkova []
(layout/common
[:h1 "Lista linkova:"]
;poziva funkciju koja vraca linkove
(upisiHtmlUFajl)
(form-to [:post "/b"]
(submit-button "Pusti drugi random link!")
(prikaziRandomOdabraniLink (rand-line "ana.txt"))
(text-area {:rows 40 :cols 100} "message" (slurp "ana.txt"))
 )))

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
    (home name "Niste uneli ime u donje polje. Pokusajte ponovo.") 
  :else
    (layout/common 
      [:h1 "Dobrodosli, " name "!"]
    [:h2 "Da li zelite da parsirate YouTube sadrzaj: "]
      (form-to [:post "/a"]
               (submit-button "Da"))
      (form-to [:post "/"]
               (submit-button "Ne")))))

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
(POST "/" [name] (pocetna name))
(POST "/a" [] (nova))
(POST "/b" [] (listaLinkova)))