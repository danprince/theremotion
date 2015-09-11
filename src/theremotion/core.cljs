(ns theremotion.core
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs.core.async :refer [<!]]
            [clojure.browser.repl :as repl]
            [theremotion.leap :as leap]
            [theremotion.theremin :as theremin]))

;; (defonce conn
;;   (repl/connect "http://localhost:9000/repl"))

(enable-console-print!)

(theremin/start!)
(theremin/use-volume-chan leap/<left)
(theremin/use-pitch-chan leap/<right)

