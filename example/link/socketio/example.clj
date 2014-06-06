(ns link.socketio.example
  (:refer-clojure :exclude [send])
  (:require [link.core :refer [send]]
            [link.socketio :refer :all]))

(def default-handler
  (create-handler
   (on-connect [ch]
               (println "on connect" ch))
   (on-message [ch msg]
               (println "on message" ch msg))
   (on-disconnect [ch]
                  (println "on disconnect" ch))))


(defn -main [& args]
  (println "starting server")
  (socketio-server 9494 {"/" default-handler})
  (println "server started on 9494"))
