pipeline {
    
    options {
        buildDiscarder(logRotator(numToKeepStr: '2'))
    }
    
    parameters {
        choice choices: ['master', 'Ans1'], description: 'Select agent', name: 'agent'
    }
    
    agent {
        node { label "${agent}"
        customWorkspace '/home/vasiliy/jenkins/WS'
        }
    }
    
    stages {
        stage('Build') {
            steps {
                echo 'Building..'
            }
        }
        stage('Test') {
            steps {
                echo 'Testing..'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
    }
    
    post { 
        always { 
            echo 'I will always say Hello again!'
        }
    }
}
