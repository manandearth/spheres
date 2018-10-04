(ns spheres.sounds
  (:require [spheres.config :refer [env]]
            [clj-time.jdbc]
            [clojure.java.jdbc :as jdbc]
            [spheres.records :refer :all]
            [spheres.db.core :refer :all]
            [overtone.live :refer :all]))

;;(def db (:database-url env))


(demo (sin-osc-fb 220 1.1))

;;(demo (example dbrown :rand-walk))

(definst quux [freq 440] (* 0.4 (saw freq)))
(definst quuy [freq 430] (* 0.4 (saw freq)))

(definst wah [freq 220 attack 1 sustain 1 release 1 vol 1.5]
  (* (env-gen (lin attack sustain release) 1 1 0.2 1 FREE) (sin-osc freq)
     vol))

(wah)
;;(quux)
;;(quuy)
;;(stop)
(def play-wah (demo (wah)))


(def earth-mass (Double/parseDouble (:mass (get-body {:name "Earth"}))))
(def venus-mass (Double/parseDouble (:mass (get-body {:name "Venus"}))))
(def earth-periapsis (Double/parseDouble (:periapsis (get-body {:name "Earth"}))))
(def venus-periapsis (Double/parseDouble (:periapsis (get-body {:name "Venus"}))))
(def earth-apoapsis (Double/parseDouble (:apoapsis (get-body {:name "Earth"}))))
(def venus-apoapsis (Double/parseDouble (:apoapsis (get-body {:name "Venus"}))))






(definst sin-wave-earth [freq (/ earth-mass 10000000000000000000000) attack earth-mass sustain 0.4 release 0.1 vol 0.4] 
  (* (env-gen (lin attack sustain release) 1 1 0 1 FREE)
     (sin-osc freq)
     vol))
(definst sin-wave-venus [freq (/ venus-mass 10000000000000000000000) attack earth-mass sustain 0.4 release 0.1 vol 0.4] 
  (* (env-gen (lin attack sustain release) 1 1 0 1 FREE)
     (sin-osc freq)
     vol))

;;(sin-wave-earth)
;;(sin-wave-venus)
;;(stop)


(examples)
;;(demo (example dyn-klang :sin-osc))

;;(demo (example dwhite :rand-seq))

;;(demo (example b-moog :compare-filters))

;;(demo (example membrane-circle :mouse))


(definst sin-wave [freq 440 attack 0.01 sustain 0.4 release 0.1 vol 0.4] 
  (* (env-gen (lin attack sustain release) 1 1 0 1 FREE)
     (sin-osc freq)
     vol))
(sin-wave)


()



;; (let [env (envelope [0 0.5 1] [1 1] :lin)]
;;   (demo ((sin-osc :freq (+ 200 (* 2 (env-gen env :action FREE)))))))

(stop)
(definst square-wave [freq 440 attack 0.01 sustain 0.4 release 0.1 vol 0.4] 
  (* (env-gen (lin attack sustain release) 1 1 0 1 FREE)
     (lf-pulse:ar freq)
     vol))
(square-wave)

(definst noisey [freq 440 attack 0.01 sustain 0.4 release 0.1 vol 0.4] 
  (* (env-gen (lin attack sustain release) 1 1 0 1 FREE)
     (pink-noise) ; also have (white-noise) and others...
     vol))
;;(noisey)

(definst triangle-wave [freq 440 attack 0.01 sustain 0.1 release 0.4 vol 0.4] 
  (* (env-gen (lin attack sustain release) 1 1 0 1 FREE)
     (lf-tri freq)
     vol))
(triangle-wave)


(definst foo [freq 440] (quad-c freq))
(definst bar [] (quad-c 350))
(definst baz [] (cusp-l 400))
(definst zed [] (latoocarfian-c 420))
(definst zap [] (lf-saw 440))
(definst tat [] (vibrato 440)) 
;;(foo 220)
;;(foo (/ earth-mass 10000000000000000000000))

;;(foo (/ venus-mass 10000000000000000000000))

(bar) 
(baz)
(zed)
(zap)
(tat)
(stop)
(kill baz)
(kill bar)
(kill foo)
(kill zed)
(kill zap)
(kill tat)
