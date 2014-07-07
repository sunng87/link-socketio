# link-socketio

SocketIO server with [link](https://github.com/sunng87/link) verbs,
based on
[Nikita Koksharov's netty-socketio](https://github.com/mrniko/netty-socketio).

link-socketio 0.2 based on netty-socketio `1.7.x` now supports
SocketIO protocol 1.0. There are some breaking changes compared with 0.1.

link-socketio 0.1 is based on netty-socketio `1.6.5`, implements
SocketIO protocol till 0.9.


## Usage

### Leiningen

![latest version on clojars](https://clojars.org/link/link-socketio/latest-version.svg)

### Server Handlers

We only support `connect`, `disconnect` and `message` in 0.1. As all
data type changed to `event` in SocketIO 1.0, I might not support
`json` or `event` of 0.9 protocol. Use it at your own risk.

All handlers are defined in [link](https://github.com/sunng87/link)
style.

```clojure
(refer-clojure :exclude '[send])
(require '[link.core :refer [send remote-addr id close]])
(require '[link.socketio :refer :all])
(import '[java.util Map])

(def default-handler
  (create-handler
   (on-connect [ch]
               (println "on connect" (id ch) (remote-addr ch)))

   ;; on-event macro arguments
   ;; 1. event name
   ;; 2. serialized type, typically in clojure we use Map here

   ;; and from 0.2, the data we sent must be a map contains :event and :data
   (on-event "test" Map [ch msg]
               (send ch {:event "test"
                         :data "Greeting from server!")
               (println "on message" ch msg)
               (close ch))

   (on-disconnect [ch]
                  (println "on disconnect" ch))))

```

The `SocketIOClient` now extends protocol `LinkMessageChannel` so it
supports [link](https://github.com/sunng87/link) verbs:

* id
* send
* send*
* remote-addr
* close

## License

Copyright © 2014 Sun Ning

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
