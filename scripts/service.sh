#!/bin/bash

###
# This script could be use to compile, deploy and create a dist 
#	You need to install git bash on Windows machine
#		see: https://git-scm.com/downloads
#
# Make sure that the env directory exist at the same level of the service.sh file
#		env
#			local.properties
#		service.sh 
#
# author: ghandalf
# email: ghandalf@ghandalf.ca
# 
# see: README.md
##

types=("java" "js" "srv" "docker" "sqs")
currentDir=`pwd`
jsInputDir=src/main/webapp
jsOutputDir=dist

source ./env/local.properties

type=$1
command=$2

###
# Will return 0 (true) if type is in the list 1 (false) otherwise
##
function isInTypes() {
	local currentType=$1
	for value in "${types[@]}"; do
		if [ "$value" == "${type}" ]; then
			return 0;
		fi
	 done
	 return 1;
}

###
# We want to avoid unnecessary work 
# if the first input is not in the list we exit
##
function inputValidation() {
	if ! isInTypes $1; then
		usage;
		exit
	fi
}

###
# Build, test, minimize, create distribution for the client site
##
function javascriptCommand() {

	echo "    Not implement yet..."
#	if [ ! -d ${jsOutputDir} ]; then
#		mkdir ${jsOutputDir}
#	fi

#	cd ${jsInputDir}
#	if [ ! -f pacakge.json ]; then
#		npm init
#	fi
		
#	case ${command} in
#		install)
#		# We need to copy the content of webapp to ${jsOutputDir}
#			npm cache clear & npm cache clear -g
#			npm install & npm install -g grunt-cli@0.1.13
#			npm install -g bower
#			npm install -g karma@1.0.0
#			grunt build --force
#			;;
#		*) usage ;;
#	esac		
}

###
# This function will manage, compile, clean, install relate to maven process 
##
function javaCommand() {
	cd ${application_path}
	case ${command} in
		package) mvn package ;;
		compile) mvn compile ;;
		install) mvn clean install ;;
		clean) mvn clean ;;
		test) mvn test ;;
		iwt) mvn clean install -DskipTests ;;
		*) usage ;;	
	esac
	cd ${currentDir}
}

###
# 
# Useful method to deploy, undeploy, start, and stop an application under the given server
#
##
function serverCommand() {	
	cd ${application_path}	

	case ${command} in
		deploy) 
			if [ -f ${primary_application_server_deploy_to}/${application_name}.war ]; then
				rm -rf ${primary_application_server_deploy_to}/${application_name}
				rm -rf ${primary_application_server_deploy_to}/${application__name}.war
			fi
			cp target/${application_name}.war ${primary_application_server_deploy_to}/
			ls -la ${primary_application_server_deploy_to}/
		  	;;
		undeploy)
			if [ -f ${primary_application_server_deploy_to}/${application_name}.war ]; then
				rm -rf ${primary_application_server_deploy_to}/${application_name}
				rm -rf ${primary_application_server_deploy_to}/${application_name}.war
			fi
			ls -la ${primary_application_server_deploy_to}/
			;;
		start)
			${primary_application_server_path}/bin/startup.bat
			;;
		stop)
			${primary_application_server_path}/bin/shutdown.bat
			;;
		*) usage ;;	
	esac
	cd ${currentDir}
}

###
#
# Prerequisite: 
# Have the 200GB Oracle Data files
# Process:
# Add these Docker commands 
# docker run --name redis -p 6379:6379 -v $REDIS_DIR:/data -d redis  
# docker run -d -p 8021:8081 -p 1521:1521 --name rmidevdb –v ORACLE_DATA_DIR:/u01/app/oracle sath89/oracle-12c# 
##
function dockerCommand() {
	
	case ${command} in
		install)
			# Check if docker image exists
			ora=`docker images | grep ${ORACLE_IMAGE_NAME} | awk '{print $1}'`
			
			if [ ${ora} ] && [ "${ora}" == "${ORACLE_IMAGE_NAME}" ]; then
				echo "    We have an image ${ora}, checking if it is running..."
				ora=`docker ps -f name=${ORACLE_DATA_NAME} | grep ${ORACLE_DATA_NAME} | awk '{print $13}'`
				
				if [ -z ${ora} ]; then
					echo "    Starting ${ORACLE_DATA_NAME}"
					docker start ${ORACLE_DATA_NAME}
				else 
					echo "    ${ORACLE_DATA_NAME} is already running..."
				fi
			else
				echo "    We don't have an image ${ORACLE_IMAGE_NAME}, we are creating it"
				docker run -d -p 8021:8081 -p 1521:1521 --name ${ORACLE_DATA_NAME} –v ${ORACLE_DATA_DIR}:/u01/app/oracle ${ORACLE_IMAGE_NAME}
			fi
			
			redis=`docker images | grep ${REDIS_IMAGE_NAME} | awk '{print $1}'`
			
			if [ ${redis} ] && [ "${redis}" == "${REDIS_IMAGE_NAME}" ]; then
				echo "    We have an image ${redis}, checking if it is running..."
				redis=`docker ps -f name=${REDIS_DATA_NAME} | grep ${REDIS_DATA_NAME} | awk '{print $12}'`
				
				if [ -z ${redis} ]; then
					echo "    Starting ${REDIS_DATA_NAME}"
					docker start ${REDIS_DATA_NAME}
				else 
					echo "    ${REDIS_DATA_NAME} is already running..."
				fi
			else
				echo "    We don't have an image ${REDIS_IMAGE_NAME}, we are creating it"
				docker run --name ${REDIS_DATA_NAME} -p 6379:6379 -v ${REDIS_DATA_DIR}:/data -d ${REDIS_IMAGE_NAME}
			fi
			docker ps
		  	;;
		start)
			# We assume that docker images are already installed
			ora=`docker ps -f name=${ORACLE_DATA_NAME} | grep ${ORACLE_DATA_NAME} | awk '{print $13}'`
			if [ -z ${ora} ]; then
				echo "    Starting ${ORACLE_DATA_NAME}..."
				docker start ${ORACLE_DATA_NAME}
			fi
			redis=`docker ps -f name=${REDIS_DATA_NAME} | grep ${REDIS_DATA_NAME} | awk '{print $12}'`
			if [ -z ${redis} ]; then
				echo "    Starting ${REDIS_DATA_NAME}..."			
				docker start ${REDIS_DATA_NAME}
			fi
			;;
		stop)
			# We assume that docker images are already installed
			#ora=`docker ps -f name=${ORACLE_DATA_NAME} | grep ${ORACLE_DATA_NAME} | awk '{print $13}'`
			ora=`docker ps -f name=${ORACLE_DATA_NAME}`
			if [ ${ora} ]; then
				echo "    Stopping ${ORACLE_DATA_NAME}..."		
				docker stop ${ORACLE_DATA_NAME}
			else
				echo "    Container ${ORACLE_DATA_NAME} is already stopped..."
			fi
			#redis=`docker ps -f name=${REDIS_DATA_NAME} | grep ${REDIS_DATA_NAME} | awk '{print $12}'`
			redis=`docker ps -f name=${REDIS_DATA_NAME}`
			if [ -z ${redis} ]; then
				echo "    Stopping ${REDIS_DATA_NAME}..."			
				docker stop ${REDIS_DATA_NAME}
			else
				echo "    Container ${REDIS_DATA_NAME} is already stopped..."
			fi
			;;
		process)
			docker ps
			;;
		*) usage ;;	
	esac
}

function sqsCommand() {
	case ${command} in
		start)
			java -Dconfig.file=elasticMQ/sqs.conf -jar elasticMQ/elasticmq-server-0.13.8.jar
			;;
		*) usage ;;
	esac
}


function usage() {
	echo " "
	echo "    usage:"
	echo "      ${0} <type> <options> where type in [${types[@]}]"
	echo "        ${0} java <option> in [package, compile, install, clean, test, iwt]"
	echo "        ${0} js -- Not Implemented yet --"
	echo "        ${0} srv <option> in [deploy, undeploy, start, stop]"
	echo "        ${0} docker <option> in [install, start, stop, process]"
	echo "        ${0} sqs <option> in [start, stop]"
}

inputValidation $type
case $type in	
	java) javaCommand ;;
	js) javascriptCommand ;;
	srv) serverCommand ;;
	docker) dockerCommand ;;
	sqs) sqsCommand ;;
	*) usage ;;
esac
	