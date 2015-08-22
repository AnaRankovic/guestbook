#  YouTube jukebox 

Dear reader,

You are one step distanced from running project that is result of learning programming language Clojure. 
This project uses Leiningen, Ring server. Session

This app is YouTube jukebox, when you wan't surprise in your life, you use this app which plays random YouTube clip.
I started with basic concepts in Clojure, and as literature I used this books(among others): 
	-"Web Development with Clojure", Dmitri Sotnikov
	-"Programming collective inteligence", O'Reilly 
	-"Clojure for machine learning", Akhil Wali 
	-"Practical.Clojure", Luke VanderHart and Stuart Sierra
	-"Clojure programming", Chas Emerick, Brian Carper, and Christophe Grand

In this project put everything related to parsing web-content, and then I removed everything that is waste.

#Funcioning on highest level of abstraction:

1.Starting application
2.User enters his name and clics button "Continue"
3.Application welcomes user. Do you want to play random clip from YouTube? system asks.
	3.1.User clicks "No" --> gets redirected on home page
	3.2.User clicks "Yes" --> in new tab is played random YouTube clip, and User is redirected to page "http://localhost:8080/playclip"
		3.2.1.User clicks on button "Play other random clip!" --> in new tab is played random YouTube clip
		3.2.2.User clicks on button "I've had enough clips. Exit!" --> he's been redirected to goodbye page

## Running

Simply run the repl.clj namespace. When REPL is started, call the function "start-server", which will open home-page in web-browser.

#Namespaces and corresponding functions

## guestbook.file-content-manipulation

  This namespace manipulates with YouTube source code. 

	#rand-seq-elem
  
  Function that contants algorithm that chooses random line from file "youtube-links.txt".
  Body of this function is algotithm:
  
    (let [f (fn [[k old] new]
            [(inc k) (if (zero? (rand-int k)) new old)])]
    (->> sequence (reduce f [1 nil]) second))
  
	#rand-line

  Reads content of file "youtube-links.txt" and calls method which chooses random line from it.
    
	#break-tag
	
  Defines string "<h3 class=\"yt-lockup-title \"><a href=\"" which helps finding YouTube links.
	
	#write-youtube-links
	
  Writes YouTube links in file "youtube-links.txt".
	
	#write-html-in-file

  Writes YouTube page source in file "youtube-html.txt". 

	#delete-youtube-links-file

  Deletes file with YouTube links: "youtube-links.txt".

##guestbook.handler
	
  This namespace is responsible for handling requests and responses.
	
	#init
	
  Function that is called when the application starts.
  	
	#destroy
	
  Function that is called when the application shuts down.
	
	#app
	
  The entry point in application through which all the requests to application will be routed.
	******** (session/wrap-noir-session {:store (memory-store)}) - OVAJ DEO POSEBNO PROKOMENTARISI
	
	
##guestbook.repl

  aa
  
	#server
	
  aa
	
	#get-handler
	
  aa
  
	#start-server
	
  Used for starting the server in development mode from REPL.
	
	#stop-server
	
  Used for stopping the server in development mode from REPL.
	
##guestbook.yt-links-display

  This namespace contains function for displaying randomly chosen YouTube links
  
	#show-link-in-new-window

  Shows forwarded link in new browser window.
	
##guestbook.routes.pages

  This namespace is responsible for handling pages of this web aplication.

	#home

  Defines components of home page. Has validation, and if user didn't enter his name, error message is shown. When user enters his name and press button "Continue", redirected to welcome page.	
	
	#welcome-page
	
  Defines components of welcome page. User is welcomed by his name, and he is asked if he wants to play random clip from YouTube. If he clicks Yes, redirected to playclip page, and in new tab is opened randomly chosen YouTube link. If he clicks No, he gets returned to previous page.
	
	#play-link-page
	
  Defines components of page for playing random clips. If User clicks button "Play other random clip!" in new tab is opened randomly chosen YouTube link. Thus, if he clics button "I've had enough clips. Exit!" he gets redirected to goodbye page.
  	
	#goodbye-page
	
  Defines components of goodbye page.
	
	#home-routes

  Defines routes for navigation through application.

##guestbook.views.layout
	
  Defines layout of components on web page.
	
	#common
	
  Layout for page elements.
	
## Conclusion


At first I had some thoube to get used to Clojure's syntax and funcional programming in general, but later on shatila sam jednostavnot i lepotu jezika.  jednostavno .... It's all about functions can use input from another function and get passed to some other function.

Best regards,

Ana Rankovic

*********** Dodaj testove
************* Pisi i o sesiji