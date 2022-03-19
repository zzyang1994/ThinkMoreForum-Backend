pipeline {
  agent any
  environment {
    AWS_REGION = 'ap-southeast-2'
    TAG = "${env.BUILD_ID}"
    //AWS ECR
    ECR_TAG = 'thinkmorecluster2'
    ECR_REPO = '981456608012.dkr.ecr.ap-southeast-2.amazonaws.com/thinkmorecluster2'
    AWS_CREDS = 'aws_accessid'
    AWS_ECS_CLUSTER = 'thinkmore-cluster-withenv'
    AWS_ECS_SERVICE = 'task-withenv'
  }

  stages {
    stage('build project') {
      steps {
        echo "PATH IS :${PATH}"
        sh 'ls -la ./'
      }
    }
    stage('Push image') {
      steps {
        script {
          echo '===========Push image to AWS ECR and Update Service=========='
          withAWS(credentials: "${AWS_CREDS}", region: "${AWS_REGION}") {
            sh "aws ecr get-login-password  --region ${AWS_REGION} | docker login --username AWS --password-stdin ${ECR_REPO}"
            sh "aws ecr batch-delete-image --repository-name ${ECR_TAG} --image-ids imageTag=${ECR_TAG}:latest || aws ecr list-images --repository-name ${ECR_TAG}"
            sh "docker build -t ${ECR_TAG} ."
            sh "docker tag ${ECR_TAG}:latest ${ECR_REPO}:latest"
            sh "docker push ${ECR_REPO}:latest"
            sh("aws ecs update-service --cluster ${AWS_ECS_CLUSTER} --service ${AWS_ECS_SERVICE} --force-new-deployment >/dev/null")
          }
        }
      }
    }
  }
    post {
      success {
        echo 'Compiled succeed!'
      }
      failure {
        echo 'Build failed.'
      }
    }
}
