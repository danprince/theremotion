(ns theremotion.theremin
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs.core.async :refer [<!]]
            [theremotion.osc :as osc]
            [theremotion.gain :as gain]
            [theremotion.audio :as audio]))

(def ctx  (audio/create))
(def osc  (osc/create ctx))
(def gain (gain/create ctx))
(def out  (audio/dest ctx))

(audio/connect [osc gain out])

(gain/volume gain 0)

(def settings (atom {:pitch 0
                     :volume 1
                     :waveform 0
                     :brightness 0}))

(defn adjust-settings
  [adjust]
    (swap! settings merge adjust))

(defn start! []
  (osc/start osc))

(defn set-freq! [f]
  (osc/freq osc f))

(defn set-volume! [l]
  (gain/volume gain l))

(defn map-frequency
  "Map a normalised value between 0 and 1 to a frequency."
  [x] (* x 1000))

(defn map-volume
  "Map a normalised value between 0 and 1 to a volume."
  [x] (* x 5))

(defn use-pitch-chan!
  "Listen for values on chan and apply them to pitch."
  [chan]
    (go (while true
      (let [[x y z] (<! chan)
            freq (map-frequency x)]
        (set-freq! freq)))))

(defn use-volume-chan!
  "Listen for values on chan and apply them to volume."
  [chan]
    (go (while true
      (let [[x y z] (<! chan)
            vol (map-volume y)]
        (set-volume! vol)))))

