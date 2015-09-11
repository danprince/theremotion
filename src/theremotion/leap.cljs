(ns theremotion.leap
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs.core.async :refer [chan tap mult put! <!]]))

(def <left (chan))

(def <right (chan))

(def options {})

(defn take-left [hands]
  (first (filter
           #(< (nth (.-palmPosition %) 0) 0)
           hands)))

(defn take-right [hands]
  (first (filter
           #(> (nth (.-palmPosition %) 0) 0)
           hands)))

(defn frame->left [frame]
  (take-left (.-hands frame)))

(defn frame->right [frame]
  (take-right (.-hands frame)))

(defn process-frame! [frame]
  (let [left (frame->left frame)
        right (frame->right frame)]
    (when left
      ;(println :left)
      (put! <left (.-palmPosition left)))
    (when right
      ;(println :right)
      (put! <right (.-palmPosition right)))))

(.loop js/Leap
   options
   process-frame!)

