The Spring Cloud Kubernetes leader election mechanism implements the leader election API of Spring Integration using a Kubernetes ConfigMap. Multiple application instances compete for leadership, but leadership will only be granted to one
references: https://docs.spring.io/spring-cloud-kubernetes/docs/current/reference/html/leader-election.html

Implementing the leader election using spring-cloud-kubernetes.



i) create a k8s configmap in your namespace.
example ; create a configmap name as 'leader-config-map' in your namespace 'test1' or default

ii) provide the required RBAC to Kubernetes Service Account (KSA) (which will be used for K8s deployment)
Terraform  code to create role and role_binding. Suppose you have  KSA 'deployment-account'

resource "kubernetes_role" "accessRole" {

    metadata {
            name = "terraform-example"
    }

    rule {
        api_groups     = [""]
        resources      = ["pods", "services"]
        verbs          = ["get", "list", "watch"]
    }
    rule {
        api_groups = [""]
        resources  = ["configmap"]
        verbs      = ["get", "list","update"]
    }
}

resource "kubernetes_role_binding_v1" "example" {
       
         metadata {
            name      = "accessRole-binding"
            namespace = "test1"
        }
        
        role_ref {
            api_group = "rbac.authorization.k8s.io"
            kind      = "Role"
            name      = "accessRole"
        }
        
        subject {
            kind        = "ServiceAccount"
            name        = "deployment-account"
            namespace   = "test1"
        }
}


iii) add config in application.yml for configmap, namespace etc

iv) implement K8sFabric8LeaderService to get the leader

v) ovveride the actuator health endpoint.

**To use this solution for implementing the Active and Passive inside k8s cluster.
change the readiness probe to point to ovveridden health endpoint**
Hence the leader pod will be ready, non leader will wait to get leader flag inorder to become ready/active.
Any request via LB/Ingress will be send to leader pod only.


