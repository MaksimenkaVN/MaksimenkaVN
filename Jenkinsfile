
pipeline {
    options {
        buildDiscarder(logRotator(numToKeepStr: '2'))
    }
    parameters {
        choice choices: ['master', 'Ans1'], description: 'Select agent', name: 'agent'
    }
    agent {
        label "${agent}"
    }
    tools {
        maven '3.8.6'
    }
    stages {
        stage('Build') {
            steps {
                dir('apps/hello-world-app') {
                    sh "mvn -B -DskipTests clean package"
                }
            }
        }
        stage('Test') {
            steps {
                dir('apps/hello-world-app') {
                    sh "mvn test"
                }
            }
        }
        stage('Copy artifact') {
            steps {
                dir('apps/hello-world-app/target') {
                    archiveArtifacts artifacts: 'my-app-1.0-SNAPSHOT.jar', followSymlinks: false
                    sh "ls -la"
                }
            }
        }
    }
    //post {
    //    always {
    //        // cleanWs()
    //    }
    //}
}
