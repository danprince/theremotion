(ns theremotion.gain)

(defn create [context]
  (.createGain context))

(defn volume
  [node]
    (.-value (.-gain node))
  [node level]
    (set! (.-value (.-gain node)) level))

