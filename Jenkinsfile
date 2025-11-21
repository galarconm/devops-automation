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

        stage('Code Quality - Checkstyle' ){
            
            steps{
                sh 'mvn checkstyle:check || true'
                recordIssues(tools: [checkStyle(pattern: '**/checkstyle-result.xml')])
            }
        }

        stage('Dockerfile Linting - Hadolint' ){
            steps{
                sh 'hadolint Dockerfile > hadolint-report.txt || true'
                recordIssues(tools: [hadolint(pattern: 'hadolint-report.txt')])
            }
        }

        stage('Unit Tests'){
            steps{
                sh 'mvn test'
            }
        }

        stage('Code Coverage'){
            steps{
                script{
                    sh 'mvn cobertura:cobertura'
                    cobertura autoUpdateHealthReport: false, autoUpdateStabilityReport: false, coberturaReportFile: 'target/site/cobertura/coverage.xml', conditionalCoverageTargets: '70, 0, 0', failNoReports: true, lineCoverageTargets: '80, 0, 0', maxNumberOfBuilds: 0, methodCoverageTargets: '80, 0, 0', onlyStable: false, sourceEncoding: 'ASCII', zoomCoverageChart: false
                }
            }
        }

        stage('Build Docker image | Code Stability'){
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

        stage("Deploy to EKS AWS"){
            steps{
                script{
                    withCredentials([string(credentialsId: 'aws_secret_access_key', variable: 'aws_secret_access_key'), string(credentialsId: 'aws_access_key_id', variable: 'aws_access_key_id')]) {
                        sh 'aws configure set aws_access_key_id $aws_access_key_id'
                        sh 'aws configure set aws_secret_access_key $aws_secret_access_key'
                        sh 'aws configure set region us-east-1'
                        sh 'aws eks update-kubeconfig --region us-east-1 --name skynet-eks-cluster'
                        sh 'kubectl apply -f deploymentservice.yaml'
                    }
                }
            }
        }
    }
}