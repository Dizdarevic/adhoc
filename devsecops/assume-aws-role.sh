
unset AWS_ACCESS_KEY_ID
unset AWS_SECRET_ACCESS_KEY
unset AWS_SESSION_TOKEN

ROLE="arn:aws:iam::<ACCOUNT-NUMBER>:role/<ROLE>"
kubectl config use-context arn:aws:eks:us-east-1:<ACCOUNT-NUMBER>:cluster/<CLUSTER-NAME>
OUTPUT=$(aws sts assume-role --role-arn $ROLE --role-session-name cli-session)

export AWS_ACCESS_KEY_ID=$(echo $OUTPUT | jq -r '.Credentials.AccessKeyId')
export AWS_SECRET_ACCESS_KEY=$(echo $OUTPUT | jq -r '.Credentials.SecretAccessKey')
export AWS_SESSION_TOKEN=$(echo $OUTPUT | jq -r '.Credentials.SessionToken')

echo "current caller identity:"
aws sts get-caller-identity

