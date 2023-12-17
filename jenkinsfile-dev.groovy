def app

node {
    stage('Checkout') {
        checkout scm 
    }

    stage('Ready') {      
        echo 'Ready to build'
        gradleHome = tool 'gradle' 
    }

    stage('Build') {
        sh "${gradleHome}/bin/gradle clean build"
    }
}
  post {
        success {
            slackSend (
                channel: '#build-log', 
                color: '#00FF00', 
                message: """
SUCCESS 
Job : ${env.JOB_NAME} - [#${env.BUILD_NUMBER}]
<${env.BUILD_URL}|OPEN>
"""
            )
        }
        failure {
            slackSend (
                channel: '#build-log', 
                color: '#FF0000', 
                message: """
FAIL 
- Job : ${env.JOB_NAME} - [#${env.BUILD_NUMBER}]
<${env.BUILD_URL}|OPEN>
} 
                """
            )
        }
