(ns user
  (:require [clojure.repl :refer :all]
            [midje.repl :as midje]
            [mount.core :as mount]
            [report.model.dydb :as ddb]))

(defn check-report-table
  []
  (ddb/create-report-table-if-needed))

(defn go
  []
  (mount/start))

(defn reset
  []
  (mount/stop)
  (go))
