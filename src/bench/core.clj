(ns bench.core
  (:gen-class)
  (:use org.httpkit.server
        [ring.middleware.file-info :only [wrap-file-info]]
        [clojure.tools.logging :only [info]]
        [clojure.data.json :only [json-str read-json]]
        (compojure [core :only [defroutes GET POST]]
                   [route :only [files not-found]]
                   [handler :only [site]]
                   [route :only [not-found]]))
  (:require [stencil.core :as stencil]
            [me.shenfeng.mustache :as mustache]))

(def data {:title "mustache.clj"
           :desc "Logic-less {{mustache}} templates for Clojure"
           :tags [{:tag "Clojure"}
                  {:tag "Mustache"}
                  {:tag "Performance"}]})

(defn str-handler [params]
  (str "<h1>mustache.clj</h1>
<p class=\"desc\">Logic-less {{mustache}} templates for Clojure</p>
<ul>
<li class=\"tag\">Clojure</li>
<li class=\"tag\">Mustache</li>
<li class=\"tag\">Performance</li>"))

(defn stencil-handler [params]
  (stencil/render-file "templates/simple.mustache" data))

(mustache/gen-tmpls-from-resources "templates" [".mustache"])

(defn mustache-handler [params]
  (simple data))

(defn- wrap-request-logging [handler]
  (fn [{:keys [request-method uri] :as req}]
    (let [resp (handler req)]
      (info (name request-method) (:status resp)
            (if-let [qs (:query-string req)]
              (str uri "?" qs) uri))
      resp)))

(defroutes all
  (GET "/" [] str-handler)
  (GET "/stencil" [] stencil-handler)
  (GET "/mustache" [] mustache-handler)
  (files "" {:root "static"})
  (not-found "<p>Page not found.</p>" ))

(defn -main [& args]
  (run-server (-> #'all site) {:port 9899})
  (info "server started at http://localhost:9899"))
