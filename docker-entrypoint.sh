#!/bin/sh

#export ECS_INSTANCE_IP_ADDRESS=$(/usr/bin/curl --retry 5 --connect-timeout 3 -s ${ECS_CONTAINER_METADATA_URI_V4}/task | /usr/bin/jq .Containers[0].Networks[1].IPv4Addresses[0] -r)

export ECS_INSTANCE_IP_ADDRESS=$(tail -1 /etc/hosts | awk '{print $1}')
exec java ${JAVA_OPTS} -Deureka.instance.ip-address=${ECS_INSTANCE_IP_ADDRESS} -jar /app/app.jar "$@"
