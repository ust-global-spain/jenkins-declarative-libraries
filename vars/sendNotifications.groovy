/**
 * Send notifications based on build status string
 */
def call(String buildStatus = 'STARTED') {
	// build status of null means successful
	buildStatus = buildStatus ?: 'SUCCESS'


}