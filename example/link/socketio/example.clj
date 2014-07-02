(ns link.socketio.example
  (:refer-clojure :exclude [send])
  (:require [link.core :refer [send remote-addr id close]]
            [link.socketio :refer :all]))

(def default-handler
  (create-handler
   (on-connect [ch]
               (println "on connect" (id ch) (remote-addr ch)))
   (on-event "test" [ch msg]
             (send ch {:event "test"
                       :data "Greeting from server!"})
             (println "on message" ch msg)
             (close ch))
   (on-disconnect [ch]
                  (println "on disconnect" ch))))


(defn -main [& args]
  (println "starting server")
  (socketio-server 9494 {"/" default-handler})
  (println "server started on 9494"))
