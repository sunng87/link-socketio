# link-socketio

SocketIO server with [link](https://github.com/sunng87/link) verbs,
based on
[Nikita Koksharov's netty-socketio](https://github.com/mrniko/netty-socketio).

link-socketio 0.1 is based on netty-socketio `1.6.5`, implements
SocketIO protocol till 0.9. (Yes, we don't support SocketIO 1.0
currently, but will soon.)

## Usage

### Leiningen

```clojure
[link/link-socketio "0.1.0"]
```

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

(def default-handler
  (create-handler
   (on-connect [ch]
               (println "on connect" (id ch) (remote-addr ch)))

   (on-message [ch msg]
               (send ch "Greeting from server!")
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
