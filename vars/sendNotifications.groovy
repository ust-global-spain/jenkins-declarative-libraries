//vars/sendNotifications.groovy
#!/usr/bin/env groovy




/**
 * Send notifications based on build status string
 */
def call(String buildStatus = 'STARTED', String channel, String credentialsGroup, String message) {
colorCodeYellow = '#FFFF00'
colorCodeGreen = '#00FF00'
colorCodeRed = '#FF0000'
}