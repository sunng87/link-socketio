(defproject link/link-socketio "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [com.corundumstudio.socketio/netty-socketio "1.6.5"]
                 [link "0.6.9"]]
  :profiles {:example {:source-paths ["example"]}}
  :lein-release {:scm :git
                 :deploy-via :shell
                 :shell ["lein" "deploy" "clojars"]})
