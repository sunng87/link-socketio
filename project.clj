(defproject link/link-socketio "0.2.1-SNAPSHOT"
  :description "SocketIO server in link verbs."
  :url "http://github.com/sunng87/link-socketio"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [com.corundumstudio.socketio/netty-socketio "1.7.1"]
                 [link "0.6.13"]]
  :profiles {:example {:source-paths ["example"]
                       :dependencies [[log4j "1.2.17"]
                                      [org.slf4j/slf4j-log4j12 "1.7.6"]]}}
  :deploy-repositories {"releases" :clojars})
