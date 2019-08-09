(ns report.view.content
  (:require
    [hiccup.page :refer :all]
    [hiccup.element :refer :all]
    [hiccup.core :refer [h]]
    [hiccup.form :refer :all]
    [report.view.layout :as layout]
    [report.reference :as r]
    [ring.util.anti-forgery :as anti-forgery]))

(defn display-search []
  [:div {:class "card text-center"}
   [:h1 "Search for report"]
   [:br]
   [:table {:class "table table-bordered"}
    [:th "Submission Method"]
    [:th "Report Type"]
    [:th "Submitted by"]
    (form-to [:post "/search"]
             (anti-forgery/anti-forgery-field)
             [:tr
              [:td (drop-down {:class "form-control"} :submissionmethod (map (juxt :name :value) r/submission-method) nil)]
              [:td (drop-down {:class "form-control"} :reporttype (map (juxt :name :value) r/report-type) nil)]
              [:td (text-field {:class "form-control"} "submittedby")]
              ]
             [:tr [:td] [:td (submit-button {:class "btn btn-primary btn-lg btn-block"} "Search")][:td]]
             )]])


(defn display-all-reports [reports]
  [:div {:class "card text-center"}
   [:h1 "Report History for all"]
   [:br]
   [:table {:class "table table-bordered"}
    [:th "ID"]
    [:th "Report Name"]
    [:th "Creation Date"]
    [:th "Report Type"]
    [:th "Submission Method"]
    [:th "Submitted Date"]
    [:th "Submitted By"]
    [:th "Total"]
    [:th "Accepted"]
    [:th "Cancels"]
    [:th "Rejections"]
    [:th "Report Status"]
    [:th "delete"]
    [:th "update"]
    (map
      (fn [report]
        [:tr
         [:td (h (:idfrn report))]
         [:td (h (:reportname report))]
         [:td (h (:creationdate report))]
         [:td (h (:reporttype report))]
         [:td (h (:submissionmethod report))]
         [:td (h (:submitteddate report))]
         [:td (h (:submittedby report))]
         [:td (h (:tot report))]
         [:td (h (:accepted report))]
         [:td (h (:cancels report))]
         [:td (h (:rejections report))]
         [:td (h (:reportstatus report))]
         [:td [:a {:href (str "/delete/" (h (:id report)))} "delete"]]
         [:td [:a {:href (str "/detail/" (h (:id report)))} "update"]]
         ]) reports)]
   (link-to {:class "btn btn-primary"} "/add" "Add report")])

(defn add-report-form []
  [:div {:class "form-group card"}
   [:h1 {:class "text-center"} "Add report"]
   (form-to [:post "/"]
            (anti-forgery/anti-forgery-field)
            [:div {:class "form-group"}
             (label "idfrn" "ID ")
             (text-field {:class "form-control"} "idfrn")]
            [:div {:class "form-group"}
             (label "reportname" "Report name ")
             (text-field {:class "form-control"} "reportname")]
            [:div {:class "form-group"}
             (label "creationdate" "Creation Date ")
             (text-field {:class "form-control"} "creationdate")]
            [:div {:class "form-group"}
             (label "reporttype" "Report type ")
             (drop-down {:class "form-control"} :reporttype (map (juxt :name :value) r/report-type) nil)]
            [:div {:class "form-group"}
             (label "submissionmethod" "Submission Method ")
             (drop-down {:class "form-control"} :submissionmethod (map (juxt :name :value) r/submission-method) nil)]
            [:div {:class "form-group"}
             (label "submitteddate" "Submitted Date ")
             (text-field {:class "form-control"} "submitteddate")]
            [:div {:class "form-group"}
             (label "submittedby" "Submitted By ")
             (text-field {:class "form-control"} "submittedby")]
            [:div {:class "form-group"}
             (label "tot" "Total ")
             (text-field {:class "form-control"} "tot")]
            [:div {:class "form-group"}
             (label "accepted" "Accepted ")
             (text-field {:class "form-control"} "accepted")]
            [:div {:class "form-group"}
             (label "cancels" "Cancels ")
             (text-field {:class "form-control"} "cancels")]
            [:div {:class "form-group"}
             (label "rejections" "Rejections ")
             (text-field {:class "form-control"} "rejections")]
            [:div {:class "form-group"}
             (label "reportstatus" "Report Status ")
             (text-field {:class "form-control"} "reportstatus")]
            (submit-button {:class "btn btn-primary btn-lg btn-block"} "Add report")
            [:br])])

(defn index-page [reports]
  (layout/common-layout ""
                        [:div {:class "col-lg-1"}]
                        (display-search)
                        [:div {:class "col-lg-18"}
                         (display-all-reports reports)]
                        [:div {:class "col-lg-1"}]
                        ))

(defn add-report-page []
  (layout/common-layout ""
                        [:div {:class "col-lg-18"}
                         (add-report-form)]
                        ))


(defn update-report-form [report]
  (layout/common-layout ""
                        [:div {:class "form-group card"}
                         [:h1 {:class "text-center"} "Report detail"]
                         (map
                           (fn [report]
                             (form-to [:put "/update"]
                                      (anti-forgery/anti-forgery-field)
                                      (hidden-field "id" (:id report))
                                      [:div {:class "form-group"}
                                       (label "idfrn" "ID ")
                                       (text-field {:class "form-control"} "idfrn" (:idfrn report))]
                                      [:div {:class "form-group"}
                                       (label "reportname" "Report name ")
                                       (text-field {:class "form-control"} "reportname" (:reportname report))]
                                      [:div {:class "form-group"}
                                       (label "creationdate" "Creation Date ")
                                       (text-field {:class "form-control"} "creationdate" (:creationdate report))]
                                      [:div {:class "form-group"}
                                       (label "reporttype" "Report type ")
                                       (drop-down {:class "form-control"} :reporttype (map (juxt :name :value) r/report-type) (:reporttype report))]
                                      [:div {:class "form-group"}
                                       (label "submissionmethod" "Submission Method ")
                                       (drop-down {:class "form-control"} :submissionmethod (map (juxt :name :value) r/submission-method) (:submissionmethod report))]
                                      [:div {:class "form-group"}
                                       (label "submitteddate" "Submitted Date ")
                                       (text-field {:class "form-control"} "submitteddate" (:submitteddate report))]
                                      [:div {:class "form-group"}
                                       (label "submittedby" "Submitted By ")
                                       (text-field {:class "form-control"} "submittedby" (:submittedby report))]
                                      [:div {:class "form-group"}
                                       (label "tot" "Total ")
                                       (text-field {:class "form-control"} "tot" (:tot report))]
                                      [:div {:class "form-group"}
                                       (label "accepted" "Accepted ")
                                       (text-field {:class "form-control"} "accepted" (:accepted report))]
                                      [:div {:class "form-group"}
                                       (label "cancels" "Cancels ")
                                       (text-field {:class "form-control"} "cancels" (:cancels report))]
                                      [:div {:class "form-group"}
                                       (label "rejections" "Rejections ")
                                       (text-field {:class "form-control"} "rejections" (:rejections report))]
                                      [:div {:class "form-group"}
                                       (label "reportstatus" "Report Status ")
                                       (text-field {:class "form-control"} "reportstatus" (:reportstatus report))]
                                      (submit-button {:class "btn btn-primary btn-lg btn-block"} "Update report")
                                      [:br])) report)]
                        ))


