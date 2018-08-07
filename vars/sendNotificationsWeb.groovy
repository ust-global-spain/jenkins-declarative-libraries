#!/usr/bin/env groovy
/**
 * Send notifications based on build status string
 */

@Grab('org.codehaus.groovy.modules.http-builder:http-builder:0.7.1')
import groovyx.net.http.HTTPBuilder

def call(String buildStatus = 'STARTED', String channel, String credentialsGroup, String message) {
  // build status of null means successful
  /*buildStatus = buildStatus ?: 'SUCCESS'

  colorCodeYellow = '#FFFF00'
  colorCodeGreen = '#00FF00'
  colorCodeRed = '#FF0000'*/

  // Default values
  /*def colorCode = colorCodeGreen
  def subject = "${buildStatus}: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'"
  def summary = "${subject} (${env.BUILD_URL})"
  def details = """<p>${buildStatus}: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]':</p>
    <p>Check console output at &QUOT;<a href='${env.BUILD_URL}'>${env.JOB_NAME} [${env.BUILD_NUMBER}]</a>&QUOT;</p>""" + message
  def detailsCisco = "\n" + message
    
  if (buildStatus == 'STARTED') {
    colorCode = colorCodeYellow
  } else if (buildStatus == 'QUESTION') {
    colorCode = colorCodeYellow
  } else if (buildStatus == 'SUCCESS') {
    colorCode = colorCodeGreen
  } else if (buildStatus == 'FAILURE') {
    colorCode = colorCodeRed
    detailsCisco += "\n@channel"
  } else {
    colorCode = colorCodeGreen
  }*/
  
  // Send notifications
  //sendWebexTeamsMessage(channel, credentialsGroup, summary + detailsCisco, colorCode)
  sendWebexTeamsMessage(channel, credentialsGroup, "test", "test")
}

def sendWebexTeamsMessage(channel, credentialsGroup, message, color) {
  credentialsId = 'webexteams-' + credentialsGroup+ '-' + channel
  echo "CredentialsId:" + credentialsId
  echo "Webex Teams channel:" + channel
  withCredentials([
    string(credentialsId: credentialsId + '-room', variable: 'ROOM_ID'),
    string(credentialsId: credentialsId + '-token', variable: 'WEBEXTEAM_TOKEN')
    ]) {
      def http = new HTTPBuilder( 'https://api.ciscospark.com' )
      http.setHeaders([Authorization: 'Bearer ' + WEBEXTEAM_TOKEN])
      def postBody = [roomId: ROOM_ID, text: message]
 
      http.post( path: '/v1/messages', body: postBody,requestContentType: 'application/x-www-form-urlencoded' ) { 
         resp ->  println "POST Success: ${resp.statusLine}"
      }
  }
}