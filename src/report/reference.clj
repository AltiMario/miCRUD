(ns report.reference
  (:require [report.utils.generic :as ut]))


(def report-type (ut/load-memo "report-type.edn"))
(def submission-method (ut/load-memo "submission-method.edn"))
