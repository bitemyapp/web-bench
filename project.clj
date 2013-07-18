(defproject bench "0.0.1-SNAPSHOT"
  :description ""
  :url ""
  :license {:name "All Rights Reserved Muthafucka"
            :url "nope.jpg"}
  :global-vars {*warn-on-reflection* true}
  :min-lein-version "2.0.0"
  :main bench.core
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [ring/ring-core "1.1.6"]
                 [compojure "1.0.2"]
                 [org.clojure/data.json "0.1.2"]
                 [org.clojure/tools.logging "0.2.3"]
                 [ch.qos.logback/logback-classic "1.0.1"]
                 [stencil "0.3.2"]
                 [me.shenfeng/mustache "1.1"]
                 [http-kit "2.1.5"]])
