(ns spheres.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[spheres started successfully]=-"))
   :stop
   (fn []
     (log/info "\n-=[spheres has shut down successfully]=-"))
   :middleware identity})
