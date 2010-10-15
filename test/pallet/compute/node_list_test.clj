(ns pallet.compute.node-list-test
  (:require
   [pallet.compute.node-list :as node-list]
   [pallet.compute :as compute])
  (:use
   [clojure.test]))

(deftest make-node-test
  (is (= (pallet.compute.node-list.Node.
          "n" "t" "1.2.3.4" :ubuntu "n-1-2-3-4" 22 "4.3.2.1" false true)
         (node-list/make-node
          "n" "t" "1.2.3.4" :ubuntu :private-ip "4.3.2.1" :is-64bit false))))

(deftest service-test
  (is (instance?
       pallet.compute.ComputeService
       (compute/service "node-list" :node-list [])))
  (is (instance?
       pallet.compute.node-list.NodeList
       (compute/service "node-list" :node-list []))))

(deftest nodes-test
  (let [node (node-list/make-node "n" "t" "1.2.3.4" :ubuntu)]
    (is (= [node]
             (compute/nodes
               (compute/service "node-list" :node-list [node]))))))

(deftest make-localhost-node-test
  (let [node (node-list/make-localhost-node)]
    (is (= "127.0.0.1" (compute/primary-ip node)))))
