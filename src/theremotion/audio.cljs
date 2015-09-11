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
        node)
      signal))

