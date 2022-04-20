cat /tmp/addhost >> /etc/hosts
mount -t nfs -o nfsvers=4.1,rsize=1048576,wsize=1048576,hard,timeo=600,retrans=2,noresvport fs-9ccb77d5.efs.us-east-1.amazonaws.com:/ /RFM2
