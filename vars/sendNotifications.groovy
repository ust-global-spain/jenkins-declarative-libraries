/**
 * Send notifications based on build status string
 */
def call(String buildStatus = 'STARTED', String channel, String credentialsGroup, String message) {
	// build status of null means successful
	buildStatus = buildStatus ?: 'SUCCESS'

	colorCodeYellow = '#FFFF00'
	colorCodeGreen = '#00FF00'
	colorCodeRed = '#FF0000'

	// Default values
	def colorCode = colorCodeGreen
	def subject = "${buildStatus}: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'"
	def summary = "${subject} (${env.BUILD_URL})"
	def details = """<p>${buildStatus}: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]':</p>
	  <p>Check console output at &QUOT;<a href='${env.BUILD_URL}'>${env.JOB_NAME} [${env.BUILD_NUMBER}]</a>&QUOT;</p>""" + message
	
	// Override default values based on build status
	if (buildStatus == 'STARTED') {
	  colorCode = colorCodeYellow
	} else if (buildStatus == 'QUESTION') {
	  colorCode = colorCodeYellow
	} else if (buildStatus == 'SUCCESS') {
	  colorCode = colorCodeGreen
	} else {
	  colorCode = colorCodeGreen
	}
	echo colorCode
	// Send notifications
	sendSlackMessage (channel, credentialsGroup, summary, colorCode)

}

def sendSlackMessage(channel, credentialsGroup, message, color) {
	credentialsId = 'slack-' + credentialsGroup+ '-' + channel
	withCredentials([string(credentialsId: credentialsId + '-url', variable: 'SLACK_BASE_URL')]) {
		slackSend (
			baseUrl: env.SLACK_BASE_URL,
			channel: channel,
			tokenCredentialId: credentialsId + '-token',
			color: color, 
			message: message
		)
	}
}