#!/bin/bash
docker-compose down -v
docker compose  up employee-api employee-api-proxy mysql -d
cd eks/ui
kubectl delete all --all -n famousdots
kubectl apply -f configmap.yaml
kubectl apply -f deployments.yaml
kubectl apply -f service.yaml
kubectl apply -f uiingress.yaml


