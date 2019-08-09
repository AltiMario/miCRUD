(defproject reportcrud "0.1"
  :description "mini report microservice"
  :main report.system
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [com.taoensso/faraday "1.9.0"]
                 [hiccup "1.0.5"]
                 [org.clojure/java.jdbc "0.7.9"]
                 [metosin/compojure-api "1.1.12"]
                 [metosin/ring-http-response "0.9.1"]
                 [ring/ring-defaults "0.3.2"]
                 [ring/ring-core "1.7.1"]
                 [ring/ring-json "0.4.0"]
                 [ring-json-response "0.2.0"]
                 [ring/ring-jetty-adapter "1.7.1"]
                 [mount "0.1.16"]]



  :profiles {:uberjar {:aot :all :uberjar-name "report.jar"}
             :dev     {:dependencies [[midje "1.9.8"]]
                       :plugins      [[lein-midje "3.2.1"]]
                       :source-paths ["dev"]}}
  :repl-options {:init-ns user}
  :plugins [[lein2-eclipse "2.0.0"]
            [lein-ring "0.8.10"]]
  :ring {:handler report.core/app})
