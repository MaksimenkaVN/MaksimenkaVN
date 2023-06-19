
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
                dir("apps/${app}") {                    
                  sh """
                  mkdir target
                  docker create --name ${app}
                  docker cp ${app}:/app/target/my-app-1.0-SNAPSHOT.jar target/
                  ls -la target/
                  """
                  // sh "docker run"
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
