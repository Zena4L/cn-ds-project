#!/bin/sh

echo "\n🏴️ Destroying Kubernetes cluster...\n"

minikube stop --profile cn-ds

minikube delete --profile cn-ds

echo "\n🏴️ Cluster destroyed\n"