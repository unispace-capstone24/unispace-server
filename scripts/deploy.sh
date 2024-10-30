#!/bin/bash

REPOSITORY=/home/ec2-user/app/deploy
PROJECT_NAME=withSpace-server/withSpace

echo "> Build 파일복사"

cp $REPOSITORY/zip/withSpace/build/libs/*.jar $REPOSITORY/

echo "> 현재 구동중인 애플리케이션 pid 확인"

CURRENT_PID=$(pgrep -fl withSpace | grep jar | awk '{print $1}')

echo "현재 구동중인 애플리케이션 pid : $CURRENT_PID"

if [ -z "$CURRENT_PID" ]; then
	echo "> 현재 구동중인 애프리케이션pid: $CURRENT_PID"
else
	echo "> kill -15 $CURRENT_PID"
	kill -15 $CURRENT_PID
	sleep 5
fi

echo "> 새 애플리케이션 배포"
JAR_NAME=$(ls -tr $REPOSITORY/*.jar | grep -v "plain" | tail -n 1)

echo "> JAR Name: $JAR_NAME"

echo "> JAR Name 에 실행 권한 추가"

chmod +x $JAR_NAME

echo "> $JAR_NAME 실행"

nohup java -jar \
	-Dspring.config.location=classpath:/application.yml,/home/ec2-user/app/application-real-db.properties,classpath:application-real.properties \
  -Dspring.profiles.active=real \
	$JAR_NAME > $REPOSITORY/nohup.out 2>&1 &