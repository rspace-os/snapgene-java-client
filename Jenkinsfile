#!groovy
@Library('rspace-shared') _ 
pipeline {
    agent any
    // these are defined in Jenkins global tool configurations
    tools {
        maven 'maven3.8.8'
        jdk 'OPEN-JDK-11'
    }
    
    parameters {            
        booleanParam(name: 'CREATE_DOCKER_IMG', defaultValue: false, description: 'Run packer build to push image to Dockerhub')
        booleanParam(name: 'ARTIFACTORY_DEPLOY', defaultValue: false, description: 'Package and Deploy to Artifactory')
    }

    environment {
        BUILD_FAILURE_EMAIL_LIST="dev@researchspace.com"
        JENKINS_HOME="/var/lib/jenkins"
        PACKER_BUILD_DIR="${JENKINS_HOME}/workspace/integrations/snapgene-daemon/build/packer"
        // Ignore strict key checking; this will be different each time as we are often using a new AWS instance.
        SSH_SETTINGS="-i builder.pem -o StrictHostKeyChecking=no -o UserKnownHostsFile=/dev/null"
        DOCKERHUB_PWD=credentials("DOCKER_HUB_RSPACEOPS")
        DOCKERHUB_REPO="rspaceops/rspace-services"
        APP_NAME="snapgene-daemon-4-DEV"
    }

    stages {
        stage ('Initialize') {
            steps {
                sh '''
                    echo "PATH = ${PATH}"
                    echo "M2_HOME = ${M2_HOME}"
                    echo "Using Java: `java -version`"
                '''
            }
        }    

        stage ('Junit tests') {
            steps {
               sh 'mvn clean test'   
            }
            post {
                success {
                   cloc()
                }
                failure {
                   notify currentBuild.result
                } 
                
                fixed {
                    notify currentBuild.result
                }

                always {
                    saveJUnits()
                }
 
            }
        } 
        
        stage ('CREATE_DOCKER_IMG') {
            when {
                expression { return params.CREATE_DOCKER_IMG }
            }
             steps {
                echo "Creating Docker image using Packer"
                sh '''
                  cd ${PACKER_BUILD_DIR}
                  docker login --username rspaceops --password ${DOCKERHUB_PWD}
                  chmod 755 runPacker-docker.sh
                  chmod 755 provision-docker.sh
                  ./runPacker-docker.sh
                '''
            }
            post {
                success {             
                    notify (currentBuild.result, "new Docker image ${DOCKERHUB_REPO}:${APP_NAME} on Dockerhub", "#release-notifications")
                }
            }
        }

        stage ('Package and Deploy to Artifactory') {
            when {
                anyOf {
                    branch 'main'; branch 'master'
                    expression { return params.ARTIFACTORY_DEPLOY}
                }
            }

            steps {
               deployToArtifactory()
            }
          }
    }//end stages
}