(ns theremotion.leap
  (:require [cljs.core.async :refer all]
            [leapjs :as leapjs]))

(def options {})

(def listen (partial leapjs/loop options))

(def frame-chan (chan))

(def frame-mult (mult frame-chan))

(defn find-hand [id hands]
  (first
    (filter
      (fn [hand] (= id (.-id hand)))
      hands)))

(defn hand->chan [hand]
  (let [id (.-id hand)
        chan (chan)
        fc (chan frame-mult)]
    (while true
      (let [frame (<!! fc)
            hands (.-hands frame)
            hand (find-hand id hands)]
        (put! chan hand)))
    (chan)))

(listen
  (fn [frame]
    (put! frame-chan frame)))

