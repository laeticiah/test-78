/usr/bin/aws s3 create-cp s3://inspector-agent-us-west-2/linux/install.sh .
tag = aws ec2 describe-tags --filters "Name=resource-id,Values=$(curl -s http://
