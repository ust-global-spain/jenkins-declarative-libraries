/**
 * Send notifications based on build status string
 */
def call(String buildStatus = 'STARTED', String channel, String credentialsGroup, String message) {
	// build status of null means successful
	buildStatus = buildStatus ?: 'SUCCESS'


}