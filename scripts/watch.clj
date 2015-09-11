(require '[cljs.build.api :as b])

(b/watch "src"
  {:main 'theremotion.core
   :output-to "out/theremotion.js"
   :output-dir "out"})
