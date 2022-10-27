# property-file-comparator
This project compares property file values.

## install chocolatey

```Set-ExecutionPolicy Bypass -Scope Process -Force; [System.Net.ServicePointManager]::SecurityProtocol = [System.Net.ServicePointManager]::SecurityProtocol -bor 3072; iex ((New-Object System.Net.WebClient).DownloadString('https://community.chocolatey.org/install.ps1'))```

check if successfully installed
``choco -?``
## Install minikube
```choco install minikube -y```

### Start minikube pointing to local registry
```minikube start vm-driver=hyperv```

```minikube -p minikube docker-env --shell powershell | Invoke-Expression```

### Start minikube dashboard

```minikube dashboard```

## Build the jar
```mvn clean install```

## Run springboot package before deployment to cluster

```mvn package spring-boot:run```


## Build docker image

```docker image build -t property-file-docker:1.0 .```

### Create deployment

```kubectl apply -f .\deployment.yaml```

### Port forward to test

```kubectl port-forward svc/property-file-api 8080:8080```