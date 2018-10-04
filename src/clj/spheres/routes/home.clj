(ns spheres.routes.home
  (:require [spheres.layout :as layout]
            [spheres.db.core :as db]
            [compojure.core :refer [defroutes GET POST]]
            [ring.util.http-response :as response]
            [clojure.java.io :as io]
            [spheres.records :as records]
            ))

 

(defn home-page []
  (layout/render
   "home.html" {:docs (-> "docs/docs.md" io/resource slurp)
                :items (map #(clojure.string/capitalize (name %)) (keys records/earth))
                :map (db/get-bodies)
                :test (layout/add-row (records/get-row "mars"))}))

(defn about-page []
  (layout/render "about.html"))

(defn audio-page []
  (layout/render "audio.html"     ;{:wah (sounds/wah)}
                 ))

(defroutes home-routes
  (GET "/" [] (home-page))
  (GET "/about" [] (about-page))
  (GET "/audio" [] (audio-page)))

