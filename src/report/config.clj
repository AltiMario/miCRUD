(ns report.config
  (:require [mount.core :refer [defstate]]
            [report.utils.generic :as utils]))

(defstate config
  :start (utils/load-memo "config.edn"))

