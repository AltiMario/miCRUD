(ns report.utils.generic
  (:require [clojure.edn :as edn]))


;;; CONFIG FILE MANAGEMENT ;;;

(defn read-path [path]
  (edn/read-string (slurp (clojure.java.io/resource path))))

(def load-memo (memoize read-path))

(defn dbconf
  []
  (get-in (load-memo "config.edn") [:db]))

(defn awsconf
  []
  (get-in (load-memo "config.edn") [:aws]))

(defn webserviceconf
  []
  (get-in (load-memo "config.edn") [:webservice]))


;;; HTTP HEADER ;;;

(def ctype
  {"Content-type" "application/json"})

(defn ok
  "return the right status number and the body in case of success"
  [d]
  {:headers ctype
   :status  200
   :body    d})

(defn bad-request
  "return the right status number and the body in case of error"
  [d]
  {:headers ctype
   :status  400
   :body    d})

(defn header
  "the output values returned are incapsulates into an header"
  [result]
  (if result
    (ok result)
    (bad-request {:message "No data to display"})))



