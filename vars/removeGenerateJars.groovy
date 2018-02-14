#!/usr/bin/env groovy
/**
 * Send notifications based on build status string
 */
def call() {
	sh 'rm -rf `find -type d -name target`'
	sh 'rm -rf `find -type d -name jars`'

}
