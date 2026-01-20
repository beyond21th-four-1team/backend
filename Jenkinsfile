pipeline {
    agent any

    environment {
        SOURCE_GITHUB_URL    = 'https://github.com/beyond21th-four-1team/backend.git'
        MANIFESTS_GITHUB_URL = 'https://github.com/beyond21th-four-1team/k8s-manifest.git'
        GIT_USERNAME         = 'sekong11'
        GIT_EMAIL            = 'sekong1108@gmail'
    }

    stages {

        stage('Checkout Source & Build') {
            steps {
                dir('source') {
                    git branch: 'main', url: "${env.SOURCE_GITHUB_URL}"
                    
                    withCredentials([string(credentialsId: 'APP_DEV_YML', variable: 'APP_DEV_YML')]) {
                        sh '''
                          mkdir -p src/main/resources
                          printf "%s" "$APP_DEV_YML" > src/main/resources/application-dev.yml
                          chmod +x ./gradlew
                        ./gradlew clean build -x test
                    '''
                }
            }
        }

        stage('Docker Build and Push') {
            steps {
                dir('source') {
                    script {
                        withCredentials([
                            usernamePassword(
                                credentialsId: 'DOCKERHUB_PASSWORD',
                                usernameVariable: 'DOCKER_USER',
                                passwordVariable: 'DOCKER_PASS'
                            )
                        ]) {
                            sh '''
                                docker build -t ${DOCKER_USER}/argocd-pipe:${BUILD_NUMBER} .
                                docker build -t ${DOCKER_USER}/argocd-pipe:latest .
                                docker login -u ${DOCKER_USER} -p ${DOCKER_PASS}
                                docker push ${DOCKER_USER}/argocd-pipe:${BUILD_NUMBER}
                                docker push ${DOCKER_USER}/argocd-pipe:latest
                                docker logout
                            '''
                        }
                    }
                }
            }
        }

        stage('Update K8S Manifest Repo') {
            steps {
                dir('manifests') {
                    git branch: 'main',
                        credentialsId: 'github',
                        url: "${env.MANIFESTS_GITHUB_URL}"

                    sh """
                       sed -i '' 's|argocd-pipe:.*|argocd-pipe:${BUILD_NUMBER}|' boot-deployment.yml
                        git add boot-deployment.yml
                        git config user.name "${env.GIT_USERNAME}"
                        git config user.email "${env.GIT_EMAIL}"
                        git commit -m "[UPDATE] ${BUILD_NUMBER} image versioning"
                        git push origin main
                    """
                }
            }
        }
    }

    post {
        success {
            echo '✅ Pipeline succeeded!'
        }
        failure {
            echo '❌ Pipeline failed!'
        }
    }
}

