(ns spheres.scrape.core
    (:require [net.cgrand.enlive-html :as html]
              [org.httpkit.client :as http]))


(defn get-dom
  []
  (html/html-snippet
   (:body @(http/get "https://en.wikipedia.org/wiki/Earth" {:insecure? true}))))

(get-dom)


;;TODO look at enlive api for the values of html/select
(defn extract-titles
    [dom]
    (map
     (comp first :content) (html/select dom [:a.cellMainLink])))

(extract-titles (get-dom))


