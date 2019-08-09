(ns report.model.dydb-test
  (:require [report.model.dydb :refer :all]
            [midje.sweet :refer :all]))

(facts "skip-empty-fields"
  (skip-empty-fields "" "2019-01-01T10:00:00" "some-type" nil "" "Mario" nil nil nil nil nil)
  =>
  {:creationdate "2019-01-01T10:00:00"
   :reporttype   "some-type"
   :submittedby  "Mario"}

  (skip-empty-fields nil nil nil nil nil nil nil nil nil nil nil) => {}

  (skip-empty-fields nil nil nil nil nil nil nil nil nil nil {}) => {}

  (skip-empty-fields "" "" "" "" "" [] '() nil nil nil {}) => {})

