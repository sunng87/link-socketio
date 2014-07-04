(defproject link/link-socketio "0.2.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [com.corundumstudio.socketio/netty-socketio "1.7.0"]
                 [link "0.6.9"]]
  :profiles {:example {:source-paths ["example"]
                       :dependencies [[log4j "1.2.17"]
                                      [org.slf4j/slf4j-log4j12 "1.7.6"]]}}
  :lein-release {:scm :git
                 :deploy-via :shell
                 :shell ["lein" "deploy" "clojars"]})
