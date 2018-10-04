(ns spheres.records
  (:require [spheres.db.core :refer :all]
            [spheres.config :refer [env]]
            [clj-time.jdbc]
            [clojure.java.jdbc :as jdbc]
            [net.cgrand.enlive-html :as html]
            [luminus-migrations.core :refer [migrate]]
            [org.httpkit.client :as http]
            [hugsql.core :as hugsql]
            [honeysql.core :as sql]
            [honeysql.helpers :as helpers]))

(def db (:database-url env))

;;for hugsql:
(def opts {:database-url "postgresql://localhost/spheres_db?user=admin&password=admin"})

;;for jdbc
(def pg-db {:dbtype "postgresql"
            :dbname "spheres_db"
            :host "localhost"
            :classname "org.postgresql.Driver"
            :user "admin"
            :password "admin"})


(defrecord Body [name
                 volume        ; km³
                 apoapsis      ; km, farthest from sun
                 periapsis     ; km, closest to sun
                 mass          ; kg
                 surface_area  ; km²
                 satelites     ; int
                 circumference ; km
                 form          ; (star, planet, dwarf-planet, moon, astroid)
                 orbital_period; days
                 parent        ; name
                 ])

;; Positional factory function

(def earth
  (->Body  "Earth" "1.08321e12" "152098232" "147098290" "5.97219e24"
           "510072000" 1 "40075.017" "planet" "365.256363004" "Sun" ))

(def mercury
  (->Body "Mercury" "6.083e10" "69816900" "46001200" "3.3011e23"
          "7.48e7" 0 "15329" "planet" "58.6458333333" "Sun"))

(def venus
  (->Body "Venus" "9.2843e11" "108939000" "107477000" "4.8675e24"
          "4.6023e8" 0 "38025" "planet" "224.701" "Sun"))

(def bogus
  (->Body "Bogus" "1.22e3" "123123123" "123123155" "4.53e13"
          "44443321" 2 "3422.4" "planet" "123" "Sun"))

(def mars
  (->Body "Mars" "1.6318e11" "249200000" "206700000" "6.4171e23"
          "144798500" 2 "21344" "planet" "686.971" "Sun"))

(def phobos
  (->Body "Phobos" "5783.61" "9517.58" "9234.42" "1.0659e16"
          "1548.3" 0 "69.7" "moon" "0.3191" "Mars"))

(def deimos
  (->Body "Deimos" "999.78" "23470.9" "23455.5" "1.4762e15"
          "495.1548" 0 "39.0" "moon" "1.2624" "Mars"))
(def moon
  (->Body "Moon" "2.1958e10" "405400" "362600" "7.342e22"
          "3.793e7" 0  "10921" "moon" "27.321661" "Earth"))

(defn insert-body-record! [body-record]
  (jdbc/insert! db :bodies
                (keys body-record)
                (vals body-record)))



;; (insert-body-record! earth)
;; (insert-body-record! bogus)
;; (insert-body-record! venus)
;; (insert-body-record! mercury)
;; (insert-body-record! mars)
;; (insert-body-record! phobos)
;; (insert-body-record! deimos)
;; (insert-body-record! moon)

;;(insert-body-record! deimos)


;;DONE find using reporting-example.reports how to link the database to this ns.

                     ;;just for visual order..:
(def earth-map {:name "Earth"
                :volume "1.08321e12"
                :apoapsis "152100000"
                :periapsis "147095000"
                :mass  "5.97237e24"
                :surface_area "510072000"
                :satelites 1
                :circumference "40075.017"
                :form "planet"
                :orbital_period "365.256363004" 
                :parent "Sun"})


;;db to record:

;;(first (spheres.db.core/get-bodies))

;;(def test-planet (map->Body (first (spheres.db.core/get-bodies))))

;;(Double/parseDouble (:mass test-planet))

;;(spheres.db.core/get-body {:name "Earth"})

(defn get-row [body]
  (let [row (get-body {:name (clojure.string/capitalize body)})]
    row))


;;(get-row "bogus")
;;(get-body {:name "Bogus"})

;;retrieving data

(defn get-data
  "expects two strings first for body second for the attribute
  permited:
  :name  :volume :aphelion :perihelion :mass
  :surface_area :satelites :circumference :form
  :orbital_period :parent"
  [body attr]
    (let [b (clojure.string/capitalize body)
        a (-> attr keyword)]
    (let [result (a (get-body {:name b}))]
      (if (int? result)
        result
        (if (double? (try (Double/parseDouble result)
                          (catch java.lang.NumberFormatException
                              result)))
          (Double/parseDouble result)
             result)))))

;;double:

;;(get-data "venus" "mass")


;;(get-data "venus" "satelites")

;;string:

;;(get-data "bogus" "name")

;;(get-data "earth" "perihelion") ; => 1.4709829E8
;;(get-data "venus" "perihelion") ; => 1.07477E8
;;(get-data "earth" "aphelion") ; => 1.52098232E8
;;(get-data "venus" "aphelion") ; =>1.08939E8

;;honeysql example
(def moon-apoapsis (sql/build :select :apoapsis
                              :from :bodies
                              :where [:= :bodies.name "Moon"]))

;;jdbc
;;(jdbc/query pg-db (sql/format moon-apoapsis))




;;SCRAPING

 (defn get-dom
   []
   (html/html-snippet
    (:body @(http/get "https://en.wikipedia.org/wiki/Earth" {:insecure? true}))))

;; (get-dom)


 ;;TODO look at enlive api for the values of html/select
 (defn extract-titles
   [dom]
   (map
    (comp first :content) (html/select dom [:a.cellMainLink])))

;; (extract-titles (get-dom))


