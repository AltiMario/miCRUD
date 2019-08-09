(ns report.system
  (:gen-class)
  (:require [mount.core :as mount]
            [report.config :as config]
            [report.core :as core]
            [report.model.dydb :as ddb]
            [ring.adapter.jetty :as jetty]))

(defn -main
  []
  (mount/start)
  (ddb/create-report-table-if-needed))


(comment
  (-main))
