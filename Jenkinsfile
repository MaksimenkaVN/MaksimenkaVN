import groovy.json.JsonSlurperClassic

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
    
    stages {
        stage('Prepare') {
            steps {
                script {
                    // json = readFile "services.json"
                    // apps = new JsonSlurperClassic().parseTest(json)
                    // print(apps)
                    app = "hello-world-app"
                    dockerRegistry = "ghcr.io"
                    dockerOwner = "maksimenkavn"
                    dockerImageTag = "${dockerOwner}/${app}:${env.BUILD_NUMBER}"
                    CR_PAT = "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"
                }
            }
        }
        stage('Build') {
            steps {                
               dir('apps') {
                  sh "docker build -t ${app} -f ${app}/Dockerfile-jenkins-build ${app}"
                }               
            }
        }
        stage('Test') {
            steps {
                dir('apps') {
                  sh "docker run ${app} mvn test"
                }                            
            }
        }        
        stage('Create Image') {
            steps {
                dir("apps") {                    
                  sh """
                  mkdir ${app}/target
                  docker create --name ${app} ${app}
                  docker cp ${app}:/app/target/my-app-1.0-SNAPSHOT.jar ${app}/target/
                  ls -la ${app}/target
                  docker rm -f ${app}
                  docker rmi -f ${app}
                  docker build -t ${dockerImageTag} -f ${app}/Dockerfile-jenkins-create ${app}
                  """                  
                }                            
            }
        }
        stage('Publish') {
            steps {                
                sh """
                echo ${CR_PAT} | docker login ghcr.io -u maksimenkavn --password-stdin                
                docker push ${dockerRegistry}
                """                                   
            }
        }  
    }
    post {
        always {
           cleanWs()
        }
    }
}
