pipeline {
    agent any

    stages {
        stage('Checkout code') {
            steps {
              git branch: 'main', url: 'https://github.com/keattiwut/workshop-docker-observability-cicd.git'
            }
        }
        stage('Code analysis') {
            steps {
                echo 'Code analysis'
            }
        }
        stage('Unit test') {
            steps {
                echo 'Unit test'
            }
        }
        stage('Code coverage') {
            steps {
                echo 'Code coverage'
            }
        }
        stage('Build images') {
            steps {
                dir("01-pipeline") {
                    sh 'docker compose build json_server'
                }
            }
        }
        stage('Setup & Provisioning') {
            steps {
                dir("01-pipeline") {
                    sh 'docker compose up json_server -d'
                }
            }
        }
        stage('Run api automate test') {
            steps {
                dir("01-pipeline") {
                    sh 'docker compose build postman'
                    sh 'docker compose up postman --abort-on-container-exit'
                }
            }
        }
        stage('Push Docker Image to Docker Hub') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'docker_hub', passwordVariable: 'DOCKER_PASS', usernameVariable: 'DOCKER_USER')]) {
                    dir("01-pipeline") {
                        sh 'docker login -u $DOCKER_USER -p $DOCKER_PASS'
                        sh '''docker image tag my_json_server:1.0 $DOCKER_USER/my_json_server:$BUILD_NUMBER
                            docker image push $DOCKER_USER/my_json_server:$BUILD_NUMBER'''
                    }
                }        
            }
        }
        stage('Deploy application') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'docker_hub', passwordVariable: 'DOCKER_PASS', usernameVariable: 'DOCKER_USER')]) {
                    dir("01-pipeline") {
                        sh 'docker stop my_json_server_dev || true'
                        sh 'docker rm my_json_server_dev || true'
                        sh 'docker run -p 3001:3000 --name my_json_server_dev -d $DOCKER_USER/my_json_server:$BUILD_NUMBER'
                    }
                }
            }
        } 
    }
    post {
        always {
            dir("pipeline") {
                sh 'docker compose down json_server postman'
            }
        }
    }
}