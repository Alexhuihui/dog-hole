pipeline {
  agent any
   environment {
      PROJECT_NAME="dog-hole"
      IMAGE_NOW="docker.io:5000/gkapp/${PROJECT_NAME}:${BUILD_TIMESTAMP}-${BUILD_NUMBER}"
    }
  stages {
    stage('git') {
      steps {
         
         checkout scm
      }
    }
    
   stage('Build') {
            steps {
                echo '开始执行打包操作.......'
                sh "mvn clean package deploy -U"
            }
        }

   stage('docker') {
      steps {
        sh 'docker build  -f ${PROJECT_NAME}/Dockerfile -t ${IMAGE_NOW} . --build-arg PROJECT_NAME=${PROJECT_NAME} && docker push ${IMAGE_NOW}'
      }
   }
   stage('deploy') {
      steps {
        script {
          if("${ENV}" == 'preview' || "${ENV}" == 'uat'){
           sh 'kubectl config use-context preview-config'
          }else if("${ENV}" == 'prod'){
           sh 'kubectl config use-context prod-config'
          }
        }
        sh 'sed -i "s#IMAGE_NAME#${IMAGE_NOW}#g" ${PROJECT_NAME}/deploy/deployment_${ENV}.yaml && kubectl apply -f ${PROJECT_NAME}/deploy/deployment_${ENV}.yaml'
      }
   }
   }
}