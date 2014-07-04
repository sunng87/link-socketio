(ns link.socketio.util)

(defmacro make-event-handler-macro [evt]
  (let [handler-name (str "on-" evt)
        symbol-name (symbol handler-name)
        args-vec-sym (symbol "args-vec")
        body-sym (symbol "body")
        evt-sym (symbol "event")
        type-sym (symbol "type")]
    `(defmacro ~symbol-name [~evt-sym ~type-sym ~args-vec-sym & ~body-sym]
       `{(keyword (str ~~handler-name "$" ~~evt-sym)) [~~type-sym (fn ~~args-vec-sym ~@~body-sym)]})))

(defn parse-event-handlers [handlers]
  (let [prefix ":on-event$"]
    (map #(vector (subs (str (key %)) (count prefix) (count (str (key %)))) (val %))
         (filter #(.startsWith (str (key %)) prefix) handlers))))
