//vars/sendNotifications.groovy
#!/usr/bin/env groovy




/**
 * Send notifications based on build status string
 */
def call(String buildStatus = 'STARTED', channel, credentialsGroup, message) {
colorCodeYellow = '#FFFF00'
colorCodeGreen = '#00FF00'
colorCodeRed = '#FF0000'
}