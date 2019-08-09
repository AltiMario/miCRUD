(ns report.core
  (:require [compojure.api.sweet :refer :all]
            [compojure.route :as route]
            [mount.core :refer [defstate]]
            [report.config :as config]
            [report.model.dydb :as db]
            [report.model.logic :as lo]
            [report.reference :as r]
            [report.utils.generic :as ut]
            [ring.adapter.jetty :as jetty]
            [ring.middleware.defaults :refer [site-defaults wrap-defaults]]
            [ring.middleware.resource :refer [wrap-resource]]))

(defroutes webinterface
  (GET "/" []
    (lo/display-all-reports))

  (GET "/add" []
    (lo/add-report))                                        ;displays an empty report form

  (POST "/" [idfrn reportname creationdate reporttype submissionmethod submitteddate submittedby tot accepted cancels rejections reportstatus]
    (lo/create-report idfrn reportname creationdate reporttype submissionmethod submitteddate submittedby tot accepted cancels rejections reportstatus))

  (PUT "/update" [id idfrn reportname creationdate reporttype submissionmethod submitteddate submittedby tot accepted cancels rejections reportstatus]
    (lo/update-report id idfrn reportname creationdate reporttype submissionmethod submitteddate submittedby tot accepted cancels rejections reportstatus))

  (GET "/detail/:id" [id]                                   ;display a form with filled values
    (lo/detail-report id))

  (DELETE "/delete/:id" [id]
    (lo/delete-report id))

  (POST "/search" [submissionmethod reporttype submittedby]
    (lo/search-report submissionmethod reporttype submittedby)))


(def apiinterface
  (api
    {:swagger
     {:ui   "/swagger/"
      :spec "/swagger.json"
      :data {:info     {:title       "reports CRUD service"
                        :description "A simple RESTful microservice for searching and display reports"}
             :tags     [{:name "report", :description "search report"}]
             :consumes ["application/json"]
             :produces ["application/json"]}}}
    (undocumented webinterface)
    (context
      "/v1" []
      :tags ["report"]
      (GET "/health-check" []
        :return String
        :summary "Checks if the service is up and running"
        (ut/header "I'm alive"))

      (GET "/report-type" []
        :summary "Report type list"
        (ut/header r/report-type))

      (GET "/submission-method" []
        :summary "Submission method list"
        (ut/header r/submission-method))

      (GET "/report" []
        :summary "List of all available reports"
        (ut/header (db/get-all-reports)))

      (GET "/report/:idfrn" [idfrn]
        :summary "List of all reports per"
        (ut/header (db/get-report-by-idfrn idfrn)))

      (GET "/report/search/" []
        :query-params [{sm :- (describe String "submission method") nil},
                       {rt :- (describe String "report type") nil},
                       {sb :- (describe String "submitted by") nil}]
        :summary "Search reports by parameters"
        (ut/header (db/get-report-by-search sm rt sb)))

      (route/not-found "Page not found"))))


(def app
  (wrap-defaults #'apiinterface site-defaults))

(defstate web-server
  :start (jetty/run-jetty
          app
          {:port (get-in config/config [:webservice :port]) :join? false})
  :stop (.stop web-server))

