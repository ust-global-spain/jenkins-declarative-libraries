#!/usr/bin/env groovy
/**
 * Send notifications based on build status string
 */

@Grab('org.codehaus.groovy.modules.http-builder:http-builder:0.7.1')
import groovyx.net.http.HTTPBuilder

def call(String buildStatus = 'STARTED', String channel, String credentialsGroup, String message) {
  // build status of null means successful
  buildStatus = buildStatus ?: 'SUCCESS'


  // Default values
  def subject = "**${buildStatus}:** Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'"
  def summary = "${subject} (${env.BUILD_URL})"
  def details = """<p>**${buildStatus}:** Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]':</p>
    <p>Check console output at &QUOT;<a href='${env.BUILD_URL}'>${env.JOB_NAME} [${env.BUILD_NUMBER}]</a>&QUOT;</p>""" + message
  def detailsCisco = "\n" + message
    

  
  // Send notifications
  sendWebexTeamsMessage(channel, credentialsGroup, summary + detailsCisco)
}

def sendWebexTeamsMessage(channel, credentialsGroup, message) {
  credentialsId = 'webexteams-' + credentialsGroup+ '-' + channel
  echo "CredentialsId:" + credentialsId
  echo "Webex Teams channel:" + channel
  withCredentials([
    string(credentialsId: credentialsId + '-room', variable: 'ROOM_ID'),
    string(credentialsId: credentialsId + '-token', variable: 'WEBEXTEAM_TOKEN')
    ]) {
      def http = new HTTPBuilder( 'https://api.ciscospark.com' )
      http.setHeaders([Authorization: 'Bearer ' + WEBEXTEAM_TOKEN])
      def postBody = [roomId: ROOM_ID, markdown: message]
 
      http.post( path: '/v1/messages', body: postBody,requestContentType: 'application/x-www-form-urlencoded' ) { 
         resp ->  println "POST Success: ${resp.statusLine}"
      }
  }
}