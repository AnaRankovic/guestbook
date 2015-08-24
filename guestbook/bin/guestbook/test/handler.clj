(ns guestbook.test.handler
  (:use clojure.test
        ring.mock.request
        guestbook.handler))

(deftest test-app
  (testing "Home GET route"
    (let [response (app (request :get "/"))]
      (is (= (:status response) 200))
      (is (.contains (:body response) "Enter your name and click the button \"Continue\""))))
  (testing "Playclip POST route"
    (let [response (app (request :post "/playclip"))]
      (is (= (:status response) 200))
      (is (.contains (:body response) "Choose action:"))))
  (testing "Goodbye POST route"
    (let [response (app (request :post "/goodbye"))]
      (is (= (:status response) 200))
      (is (.contains (:body response) "Goodbye !")))))