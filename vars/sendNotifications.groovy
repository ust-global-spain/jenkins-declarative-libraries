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
	def detailsSlack = "\n" + message
		
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
	
	// Send notifications
	sendSlackMessage (channel, credentialsGroup, summary + detailsSlack, colorCode)

}

def sendSlackMessage(channel, credentialsGroup, message, color) {
	credentialsId = 'slack-' + credentialsGroup+ '-' + channel
	echo "CredentialsId:" + credentialsId
	echo "Slack channel:" + channel
	withCredentials([string(credentialsId: credentialsId + '-subdomain', variable: 'SLACK_TEAM_DOMAIN'),
		string(credentialsId: credentialsId + '-url', variable: 'SLACK_BASE_URL'),
		string(credentialsId: credentialsId + '-token', variable: 'SLACK_TOKEN')
		]) {
		slackSend (
			baseUrl: env.SLACK_BASE_URL,
			teamDomain: env.SLACK_TEAM_DOMAIN,
			channel: channel,
			token: env.SLACK_TOKEN,
			color: color, 
			message: message
		)
	}
}