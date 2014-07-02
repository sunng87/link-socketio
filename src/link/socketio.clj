(ns link.socketio
  (:refer-clojure :exclude [send])
  (:require [link.core :refer
             [LinkMessageChannel id send* send channel-addr
              remote-addr close valid?]]
            [link.util :refer [make-handler-macro]]
            [link.socketio.util :as u])
  (:import [com.corundumstudio.socketio
            Configuration SocketIOServer
            SocketIOClient VoidAckCallback
            AckRequest SocketConfig])
  (:import [com.corundumstudio.socketio.listener
            ConnectListener DisconnectListener
            DataListener ClientListeners]))

(extend-protocol LinkMessageChannel

  SocketIOClient
  (id [this]
    (str (.getSessionId this)))
  (send* [this msg cb]
    (let [{evt :event data :data} msg]
     (.sendEvent this evt
                 (proxy [VoidAckCallback] []
                   (onSuccess [r]
                     (cb))) (into-array Object [data]))))
  (send [this msg]
    (send* this msg (fn [])))
  (channel-addr [this]
    (throw (UnsupportedOperationException.)))
  (remote-addr [this]
    (.getRemoteAddress this))
  (close [this]
    (.disconnect this))
  (valid? [this]
    (.isChannelOpen this)))

(make-handler-macro connect)
(make-handler-macro disconnect)
(u/make-event-handler-macro event)

(defmacro create-handler [& body]
  `(merge ~@body))

(defn socketio-server [port handlers
                       & {:keys [ssl-keystore-in
                                 ssl-keystore-password
                                 ssl-keystore-format
                                 hostname
                                 heartbeat-timeout
                                 heartbeat-interval
                                 ack-mode]
                          :or {hostname "0.0.0.0"}}]
  (let [socket-config (doto (SocketConfig.)
                        (.setReuseAddress true))
        config (doto (Configuration.)
                 (.setPort port)
                 (.setHostname hostname)
                 (.setSocketConfig socket-config))]
    (when ssl-keystore-in
      (.setKeyStore ^Configuration config
                    ssl-keystore-in))

    (when ssl-keystore-password
      (.setKeyStorePassword ^Configuration config
                            ssl-keystore-password))

    (when ssl-keystore-format
      (.setKeyStoreFormat ^Configuration config
                          ssl-keystore-format))

    (when heartbeat-timeout
      (.setHeartbeatTimeout ^Configuration config
                            heartbeat-timeout))

    (when heartbeat-interval
      (.setHeartbeatInterval ^Configuration config
                             heartbeat-interval))

    (when ack-mode
      (.setAckMode ^Configuration config
                   ack-mode ))

    (let [server (SocketIOServer. config)]
      (doseq [ns (keys handlers)]
        (let [socketio-ns (if (or (= "" ns) (= "/" ns))
                            server
                            (.addNamespace ^SocketIOServer server ns))
              handler (get handlers ns)]

          (.addConnectListener
           ^ClientListeners socketio-ns
           (reify ConnectListener
             (onConnect [this client]
               (when-let [h (:on-connect handler)]
                 (h client)))))

          (.addDisconnectListener
           ^ClientListeners socketio-ns
           (reify DisconnectListener
             (onDisconnect [this client]
               (when-let [h (:on-disconnect handler)]
                 (h client)))))

          (doseq [[event h] (u/parse-event-handlers handler)]
            (println event h)
            (.addEventListener
             ^ClientListeners socketio-ns
             event
             String
             (reify DataListener
               (onData [this client msg ack]
                 (println msg)
                 (h client msg)))))))

      (.start server))))

(defn stop-socketio-server [^SocketIOServer server]
  (.stop server))
