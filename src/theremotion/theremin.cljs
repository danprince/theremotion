(ns theremotion.theremin
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs.core.async :refer [<!]]
            [theremotion.osc :as osc]
            [theremotion.gain :as gain]
            [theremotion.audio :as audio]))

(def ctx  (audio/create))
(def osc  (osc/create ctx))
(def gain (gain/create ctx))
(def out  (.-destination ctx))

(audio/connect [osc gain out])

;; initial volume
(gain/volume gain 0)

(def settings (atom {:pitch 300
                     :volume 0.3
                     :waveform "sine"
                     :brightness 0}))

(defn adjust-settings
  [adjust]
    (swap! settings merge adjust))

(defn start! []
  (osc/wave osc (:waveform @settings))
  (osc/start osc))

(defn set-wave! [wave-name]
  (osc/wave osc wave-name))

(defn set-freq! [f]
  (osc/freq osc f))

(defn set-volume! [l]
  (gain/volume gain l))

(defn map-frequency
  "Map a normalised value between 0 and 1 to a frequency."
  [x] (+ 200 (* x (:pitch @settings))))

(defn map-volume
  "Map a normalised value between 0 and 1 to a volume."
  [x]
    (if (> x 0)
      (* x (:volume @settings))
      0))

(defn use-pitch-chan!
  "Listen for values on chan and apply them to pitch."
  [chan]
    (go (while true
      (let [[x y z] (<! chan)
            freq (map-frequency y)]
        (set-freq! freq)))))

(defn use-volume-chan!
  "Listen for values on chan and apply them to volume."
  [chan]
    (go (while true
      (let [[x y z] (<! chan)
            vol (map-volume x)]
        (set-volume! vol)))))

