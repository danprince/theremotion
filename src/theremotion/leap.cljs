(ns theremotion.leap
  (:require [cljs.core.async :refer [chan put! <!]]))

(defn take-from
  "Filters a collection of hands against a predicate of their x
  coordinate and returns the first hand in the filtered list."
  [pred hands]
  (first
    (filter
      #(pred (nth (.-palmPosition %) 0) 0)
      hands)))

(def take-left
  "Take a hand from the left side of the x origin."
  (partial take-from <))

(def take-right
  "Take a hand from the right side of the x origin"
  (partial take-from >))

(defn frame->hand
  "Extract a hand from a frame, given a search function.

  The search function will be passed a sequence of hands and should
  return one of them."
  [search frame] (search (.-hands frame)))

(def frame->left
  "Extract and return the left hand from a frame."
  (partial frame->hand take-left))

(def frame->right
  "Extract and return the right hand from a frame."
  (partial frame->hand take-right))

(def <left
  "All left hand values will be made available on this channel."
  (chan))

(def <right
  "All right hand values will be made available on this channel."
  (chan))

(def options
  "Options to pass to the loop function."
  {})

(defn process-frame!
  "Every frame generated frame is passed to this function."
  [frame]
    (let [left (frame->left frame)
          right (frame->right frame)
          int-box (.-interactionBox frame)
          normalize (.-normalizePoint int-box)
          clamp? false]
      (when left
        (put! <left (normalize (.-palmPosition left) clamp?)))
      (when right
        (put! <right (normalize (.-palmPosition right) clamp?)))))

(.loop js/Leap
   options
   process-frame!)

