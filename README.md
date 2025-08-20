# famousdots
eksctl create cluster   --name famousdots-eks   --version 1.29   --region eu-north-1   --nodegroup-name famousdots-nodes   --node-type t3.medium   --nodes 1   --nodes-min 1   --nodes-max 2   --managed
aws iam list-roles | grep famousdots
aws iam attach-role-policy   --role-name eksctl-famousdots-eks-nodegroup-fa-NodeInstanceRole-jQh6YKTl6gaz   --policy-arn arn:aws:iam::571631915203:policy/AWSLoadBalancerControllerIAMPolicy
 
or
aws iam attach-role-policy \
>   --role-name $(aws iam list-roles --query "Roles[?contains(RoleName, 'NodeInstanceRole')].RoleName" --output text) \
>   --policy-arn arn:aws:iam::571631915203:policy/AWSLoadBalancerControllerIAMPolicy
 
eksctl get cluster --name famousdots-eks --region eu-north-1 -o yaml | grep vpc-
helm install aws-load-balancer-controller eks/aws-load-balancer-controller   -n kube-system   --set clusterName=famousdots-eks   --set serviceAccount.create=true   --set region=eu-north-1   --set vpcId=vpc-01b3f5b467473bc80   --set serviceAccount.name=aws-load-balancer-controller
kubectl get pods -n kube-system | grep aws-load-balancer
helm upgrade --install argocd argo/argo-cd -n argocd -f argoCD/values.yaml --create-namespace
kubectl port-forward svc/argocd-server -n argocd 8080:80
argocd login localhost:8080 --username admin --passwor
d `kubectl -n argocd get secret argocd-initial-admin-secret -o jsonpath="{.data.password}" | base64 -d`
argocd repo add https://github.com/tejaskenny/famosdots.git --username tejaskenny --password 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
 
 
kubectl apply -f argoCD/famousdots-app.yaml
eksctl utils associate-iam-oidc-provider \
>   --region eu-north-1 \
>   --cluster famousdots-eks \
>   --approve

aws eks describe-cluster \
>   --name famousdots-eks \
>   --region eu-north-1 \
>   --query "cluster.identity.oidc.issuer" \
>   --output text

 
aws iam list-open-id-connect-providers
 
vim trust-policy.json
 
aws iam create-role \
>   --role-name AmazonEKS_EBS_CSI_DriverRole \
>   --assume-role-policy-document file://trust-policy.json

 
 
aws iam attach-role-policy \
>   --role-name AmazonEKS_EBS_CSI_DriverRole \
>   --policy-arn arn:aws:iam::aws:policy/service-role/AmazonEBSCSIDriverPolicy

 
eksctl create addon \
>   --name aws-ebs-csi-driver \
>   --cluster famousdots-eks \
>   --region eu-north-1 \
>   --service-account-role-arn arn:aws:iam::571631915203:role/AmazonEKS_EBS_CSI_DriverRole
 
