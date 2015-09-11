(ns theremotion.audio)

(def AudioContext js/AudioContext)

(defn create []
  (AudioContext.))

(defn dest [ctx]
  (.-destination ctx))

(defn connect [signal]
  (reduce
    (fn [before node]
      (.connect before node))
    signal)

