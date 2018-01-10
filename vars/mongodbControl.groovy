#!/usr/bin/env groovy

def mongodbStart() {
	sh "mongod --quiet --fork --noauth --pidfilepath ${WORKSPACE}/mongopid --logpath ${WORKSPACE}/data/log --dbpath ${WORKSPACE}/data/db"
}

def mongodbStop() {
	sh "kill -HUP `cat ${WORKSPACE}/mongopid`"
}