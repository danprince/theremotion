(ns theremotion.osc)

(defn start [osc]
  (.start osc))

(defn freq
  "Get/set the frequency of an oscillator."
  ([osc] (.-frequency osc))
  ([osc freq] (set! (.-value (.-frequency osc)) freq)))

(defn wave
  "Get/set the wavetype of an oscillator."
  ([osc] (.-type osc))
  ([osc name] (set! (.-type osc) name)))

(defn create
  "Creates an oscillator with some optional initial settings."
  ([context]
    (.createOscillator context))
  ([context settings]
    (let [osc (create context)]
      (cond
        (contains? settings :frequency)
          (freq osc (:frequency settings))
        (contains? settings :wave)
          (wave osc (:wave settings)))
      osc)))

