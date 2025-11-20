pipeline {
    agent any
    tools{
        maven 'maven_3_5_0'
    }
    stages{
        stage('Build Maven'){
            steps{
                checkout scmGit(branches: [[name: '*/main']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/galarconm/devops-automation']])
                sh 'mvn clean install'
            }
        }
        stage('Build Docker image'){
            steps{
                script{
                    sh 'docker build -t galarconm/devops-integration .'
                }
            }
        }
        stage('Push image to DockerHub'){
            steps{
                script{
                    withCredentials([string(credentialsId: 'dockerpass', variable: 'dockerpass')]) {
        // some block
                    sh " docker login -u galarconm -p ${dockerpass} "
                    sh 'docker push galarconm/devops-integration'

                    }
                    
                }
            }
        }
    }
}