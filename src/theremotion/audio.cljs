(ns theremotion.audio)

(defn create
  "Creates an audio context."
  [] (js/AudioContext.))

(defn dest
  "Shorthand accessor for the destination property."
  [ctx] (.-destination ctx))

(defn connect
  "Connects a sequence of audio nodes together in serial"
  [signal]
    (reduce
      (fn [before node]
        (.connect before node)
        ;; return node for next step
        node)
      signal))

;; TODO define interface so that graphs can be built
;; with the thread first macro.
; (-> gain out)
; (-> [osc osc2] gain out)

