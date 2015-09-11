(ns theremotion.osc)

(defn frequency
  [osc]
    (.-frequency osc)
  [osc f]
    (set! (.-value (.-frequency osc)) f))

(defn wave
  [osc]
    (.-type osc)
  [osc name]
    (set! (.-value (.-type osc)) name))

(defn create
  [context]
    (.createOscillator context)
  [context settings]
    (let [osc (create context)]
      (cond
        (contains? settings :frequency)
          (frequency osc (:frequency settings))
        (contains? settings :wave)
          (wave osc (:wave settings)))
      osc))

