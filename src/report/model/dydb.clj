(ns report.model.dydb
  (:require [taoensso.faraday :refer :all]
            [mount.core :refer [defstate]]
            [report.config :as config]))

(defstate ddb
  :start (:aws config/config))

(defn create-report-table-if-needed
  []
  (when-not (contains? (set (list-tables ddb)) :report)
    (create-table
     ddb
     :report
     [:id :s]
     {:throughput {:read 1 :write 1}
      :block?     true})))

(defn skip-empty-fields
  [reportname creationdate reporttype submissionmethod submitteddate
   submittedby tot accepted cancels rejections reportstatus]
  (reduce-kv
   (fn [m k v]
     (if (not-empty v)
       (assoc m k v)
       m))
   {}
   {:reportname       reportname
    :creationdate     creationdate
    :reporttype       reporttype
    :submissionmethod submissionmethod
    :submitteddate    submitteddate
    :submittedby      submittedby
    :tot              tot
    :accepted         accepted
    :cancels          cancels
    :rejections       rejections
    :reportstatus     reportstatus}))

(defn create-report
  [idfrn reportname creationdate reporttype submissionmethod submitteddate
   submittedby tot accepted cancels rejections reportstatus]
  (put-item ddb
            :report (merge (if (empty? idfrn) (hash-map :idfrn "0") (hash-map :idfrn idfrn))
                           {:id (.toString (java.util.UUID/randomUUID))}
                           (skip-empty-fields reportname creationdate reporttype submissionmethod submitteddate submittedby tot accepted cancels rejections reportstatus))))

(defn delete-report [id]
  (delete-item ddb :report {:id id}))

(defn get-all-reports []
  (scan ddb :report))

(defn get-report-by-id [id]
  [(get-item ddb :report {:id id})])

(defn update-report
  [id idfrn reportname creationdate reporttype submissionmethod submitteddate submittedby tot accepted cancels rejections reportstatus]
  (put-item
   ddb
   :report
   (-> (skip-empty-fields reportname creationdate reporttype submissionmethod submitteddate
                          submittedby tot accepted cancels rejections reportstatus)
       (assoc :id id)
       (assoc :idfrn (or idfrn "0")))))

(defn get-report-by-idfrn [idfrn]
  (scan ddb :report {:attr-conds {:idfrn [:eq idfrn]}}))


(defn get-report-by-search
  "build the query dynamically because of the optional parameters"
  [submissionmethod reporttype submittedby]
  (scan ddb :report {:attr-conds {:submissionmethod [:eq (if (empty? submissionmethod) " " submissionmethod)]
                                  :reporttype       [:eq (if (empty? reporttype) " " reporttype)]
                                  :submittedby      [:eq (if (empty? submittedby) " " submittedby)]}}))

