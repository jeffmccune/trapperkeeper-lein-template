(ns {{namespace}}-web-service
  (:require [clojure.tools.logging :as log]
            [compojure.core :as compojure]
            [{{namespace}}-web-core :as core]
            [puppetlabs.trapperkeeper.core :as trapperkeeper]
            [puppetlabs.trapperkeeper.services :as tk-services]))

(trapperkeeper/defservice hello-web-service
  [[:ConfigService get-in-config]
   [:WebroutingService add-ring-handler get-route]
   HelloService]
  (init [this context]
    (log/info "Initializing hello webservice")
    (let [url-prefix (get-route this)]
      (add-ring-handler
        this
        (compojure/context url-prefix []
          (core/app (tk-services/get-service this :HelloService))))
      (assoc context :url-prefix url-prefix))))