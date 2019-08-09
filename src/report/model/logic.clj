(ns report.model.logic
  (:require
    [clojure.string :as str]
    [ring.util.response :as ring]
    [report.view.content :as view]
    [report.model.dydb :as db]))

(defn display-all-reports []
  (view/index-page (db/get-all-reports)))

(defn add-report []
  (view/add-report-page))

(defn create-report [idfrn reportname creationdate reporttype submissionmethod submitteddate submittedby tot accepted cancels rejections reportstatus]
  (when-not (str/blank? idfrn)
    (db/create-report idfrn reportname creationdate reporttype submissionmethod submitteddate submittedby tot accepted cancels rejections reportstatus))
  (ring/redirect "/"))

(defn delete-report [id]
  (when-not (str/blank? id)
    (db/delete-report id))
  (ring/redirect "/"))

(defn detail-report [id]
  (view/update-report-form (db/get-report-by-id id)))

(defn update-report [id idfrn reportname creationdate reporttype submissionmethod submitteddate submittedby tot accepted cancels rejections reportstatus]
  (when-not (str/blank? id)
    (db/update-report id idfrn reportname creationdate reporttype submissionmethod submitteddate submittedby tot accepted cancels rejections reportstatus))
  (view/index-page (db/get-all-reports)))

(defn search-report [submissionmethod reporttype submittedby]
  (view/index-page (db/get-report-by-search submissionmethod reporttype submittedby)))
