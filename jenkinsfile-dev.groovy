def app
def massage = "_${env.JOB_NAME}_[#${env.BUILD_NUMBER}] <${env.BUILD_URL}|OPEN>"

node {
    try{
    //Slack send notify - start
    slackSend(channel: '#backend-bulid-log', message: "*Build start(${massage})*")
    
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
    //Slack send notify - result
    slackSend(channel: '#backend-bulid-log', color: '#00FF00', message: """
*Build successful*
Job : ${massage}
""")
    } catch (Exception e) {
        slackSend(channel: '#backend-bulid-log', color: 'danger', message: """ 
*Build failed*
Job : ${massage}
""")
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
