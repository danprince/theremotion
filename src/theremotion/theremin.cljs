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

(def pitch-pos [-20 10 0])

(def volume-pos [20 10 0])

(def settings (atom {:pitch 0
                     :volume 1
                     :waveform 0
                     :brightness 0}))

(defn adjust-settings
  "Atomically swap the old settings for some new ones"
  [adjust]
    (swap! settings merge adjust))

(defn start! []
  (osc/start osc))

(defn freq! [f]
  (osc/freq osc f))

(defn volume! [l]
  (gain/volume gain l))

(defn dist-from [a b]
  (let [sqrt (.-sqrt js/Math)
        pow (.-pow js/Math)]
    (sqrt (+
            (pow (- (nth b 1) (nth a 1)) 2)
            (pow (- (nth b 0) (nth a 0)) 2)))))

(defn pitch-chan [chan]
  (go (while true
    (let [pos (<! chan)
          dist (dist-from pos pitch-pos)]
      (println :pitch dist)
      (freq! dist)))))

(defn volume-chan [chan]
  (go (while true
    (let [pos  (<! chan)
          dist (dist-from pos volume-pos)
          vol (/ (- dist 20) 100)]
      (println :volume vol)
      (volume! vol)))))

