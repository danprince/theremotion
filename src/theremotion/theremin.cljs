(ns theremotion.theremin
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs.core.async :refer [<!]]
            [theremotion.osc :as osc]
            [theremotion.gain :as gain]
            [theremotion.audio :as audio]
            [theremotion.util :refer [dist-from-2d]]))

(def ctx  (audio/create))
(def osc  (osc/create ctx))
(def gain (gain/create ctx))
(def out  (audio/dest ctx))

(audio/connect [osc gain out])

(gain/volume gain 0)

(def pitch-pos [-20 10 0])

(def volume-pos [20 10 0])

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

(defn map-pitch
  "Map a normalised value between 0 and 1 to a pitch"
  [val] val)

(defn map-volume
  "Map a normalised value between 0 and 1 to a volume"
  [val] val)

(defn use-pitch-chan
  "Listen for values on chan and apply them to pitch."
  [chan]
    (go (while true
      (let [pos  (<! chan)
            dist (dist-from-2d pos pitch-pos)]
        (set-freq! dist)))))

(defn use-volume-chan
  "Listen for values on chan and apply them to volume."
  [chan]
    (go (while true
      (let [pos  (<! chan)
            dist (dist-from-2d pos volume-pos)
            vol  (/ (- dist 20) 100)]
        (set-volume! vol)))))

