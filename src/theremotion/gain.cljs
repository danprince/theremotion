(ns theremotion.gain)

(defn create
  "Create a gain node with some optional initial settings."
  ([context]
    (.createGain context))
  ([context settings]
    (let [gain (create context)]
      (when (contains? settings :volume)
        (volume gain (:volume settings))))))

(defn volume
  "Adjust the volume of a gain node."
  ([node] (.-value (.-gain node)))
  ([node level] (set! (.-value (.-gain node)) level)))

