(ns theremotion.util)

(defn dist-from-2d [a b]
  (let [sqrt (.-sqrt js/Math)
        pow (.-pow js/Math)]
    (sqrt (+
            (pow (- (nth b 1) (nth a 1)) 2)
            (pow (- (nth b 0) (nth a 0)) 2)))))
