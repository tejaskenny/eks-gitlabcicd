#!/bin/bash
docker compose  up employee-api employee-api-proxy mysql
cd eks/ui
kubectl apply -f configmap.yaml
kubectl apply -f deployments.yaml
kubectl apply -f service.yaml
kubectl apply -f uiingress.yaml


