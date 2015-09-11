(ns theremotion.theremin
  (:require [theremotion.osc :as osc]
            [theremotion.gain :as gain]))

(def settings (atom {:pitch 0
                     :volume 1
                     :waveform 0
                     :brightness 0}))

(defn adjust-settings
  [adjust]
    (swap! settings merge adjust))

(def osc (osc/create))

(defn start []
  (osc/start osc))

(defn adjust-freq [freq]
  (osc/freq osc freq))

(defn adjust-volumne [vol]
  (osc/volume osc vol))

