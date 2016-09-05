#!groovy

node () {
    try {
       // Mark the code checkout 'stage'....
       stage 'Checkout'
    
       // Get some code from a GitHub repository
       checkout scm
    
       // Get the maven tool.
       String mvnHome = tool 'mvn-3.3.9'
    
       stage 'Build global-shopping-car'
       // Run the maven build
       build("global-shopping-cart", mvnHome) 
	 
       stage 'Build realtime-inventory'
       build("realtime-inventory", mvnHome)
	   
	   stage 'Build event-driven-personalization'
       build("event-driven-personalization", mvnHome)
    } catch(e) {
        if (currentBuild.result != "ABORTED") {
            currentBuild.result = "FAILED"
            sendEmail()
            throw e
        }
    }
}
def build(String moduleName, String mvnHome) {
	if (isUnix()) {
		sh "${mvnHome}/bin/mvn -f " + moduleName + "/pom.xml clean package"
	} else {
		bat "\"${mvnHome}\\bin\\mvn\" -f " + moduleName + "\\pom.xml clean package"
	}
}
def sendEmail() {
    def to = emailextrecipients([
        [$class: 'DevelopersRecipientProvider'],
        [$class: 'RequesterRecipientProvider'],
        [$class: 'FirstFailingBuildSuspectsRecipientProvider']
    ])
    if (to != null && to != "") {
        emailext (
            to: to,
            subject: "FAILED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'",
            body: """<p>FAILED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]':</p>
            <p>Check console output at &quot;<a href='${env.BUILD_URL}'>${env.JOB_NAME} [${env.BUILD_NUMBER}]</a>&quot;</p>"""
        )
    }
}