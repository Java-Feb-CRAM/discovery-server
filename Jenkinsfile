pipeline {
    agent any
    environment {
        COMMIT_HASH="${sh(script:'git rev-parse --short HEAD', returnStdout: true).trim()}"
    }
    stages {
      stage('Test') {
        steps {
          echo 'Testing..'
          script {
            sh "mvn test"
          }
        }
      }
        stage('Package') {
            steps {
                echo 'Packging jar file..'
                script {
                    sh "mvn clean package"
                }
            }
        }
        stage('Build') {
            steps {
                echo 'Building docker image..'
                sh "$AWS_LOGIN"                
                sh "docker build --tag utopia-discovery-server:$COMMIT_HASH ."
                sh "docker tag utopia-discovery-server:$COMMIT_HASH 038778514259.dkr.ecr.us-east-1.amazonaws.com/utopia-discovery-server:$COMMIT_HASH"
                echo 'Pushing docker image to ECR..'
                sh "docker push 038778514259.dkr.ecr.us-east-1.amazonaws.com/utopia-discovery-server:$COMMIT_HASH"
            }
        }
        // stage('Deploy') {
        //    steps {
        //        sh "touch ECSService.yml"
        //        sh "rm ECSService.yml"
        //        sh "wget https://raw.githubusercontent.com/SmoothstackUtopiaProject/CloudFormationTemplates/main/ECSService.yml"
        //        sh "aws cloudformation deploy --stack-name UtopiaFlightMS --template-file ./ECSService.yml --parameter-overrides ApplicationName=UtopiaFlightMS ECRepositoryUri=$AWS_ID/utopiaairlines/flightms:$COMMIT_HASH DBUsername=$DB_USERNAME DBPassword=$DB_PASSWORD SubnetID=$SUBNET_ID SecurityGroupID=$SECURITY_GROUP_ID TGArn=$UTOPIA_FLIGHTMS_TARGETGROUP --capabilities \"CAPABILITY_IAM\" \"CAPABILITY_NAMED_IAM\""
        //    }
        // }
        stage('Cleanup') {
            steps {
              echo 'Cleaning up..'
                sh "docker system prune -f"
            }
        }
    }
}