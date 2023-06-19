
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
                    app = "hello-world-app"
                    dockerRegistry = "ghcr.io"
                    dockerOwner = "maksimenkavn"
                    dockerImageTag = "${dockerOwner}/${app}:${env.BUILD_NUMBER}"
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
                withCredentials([usernamePassword(credentialsId: 'MaksimenkaVN', passwordVariable: 'pass', usernameVariable: 'user')]) {
                    sh """
                    echo ${pass} | docker login ${dockerRegistry} -u ${user} --password-stdin
                    docker push ${dockerRegistry}
                    """
                }                   
            }
        }  
    }
    post {
        always {
           cleanWs()
        }
    }
}
