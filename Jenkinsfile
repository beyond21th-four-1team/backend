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
                            set -e
                            mkdir -p src/main/resources

                            # base64 decode (Linux: -d, macOS: -D)
                            if base64 --help 2>&1 | grep -q -- "-d"; then
                                echo "$APP_DEV_YML" | base64 -d > src/main/resources/application-dev.yml
                            else
                                echo "$APP_DEV_YML" | base64 -D > src/main/resources/application-dev.yml
                            fi

                            sed -n '1,40p' src/main/resources/application-dev.yml

                            chmod +x ./gradlew
                            ./gradlew clean build -x test
                        '''
                    }
                }
            }
        }

        stage('Jib Build and Push') {
            steps {
                dir('source') {
                    withCredentials([
                        usernamePassword(
                            credentialsId: 'DOCKERHUB_PASSWORD',
                            usernameVariable: 'DOCKER_USER',
                            passwordVariable: 'DOCKER_PASS'
                        )
                    ]) {
                        sh '''
                            set -e
                            chmod +x ./gradlew

                            ./gradlew jib \
                              -Djib.to.image=${DOCKER_USER}/argocd-pipe:${BUILD_NUMBER} \
                              -Djib.to.auth.username=${DOCKER_USER} \
                              -Djib.to.auth.password=${DOCKER_PASS}

                            ./gradlew jib \
                              -Djib.to.image=${DOCKER_USER}/argocd-pipe:latest \
                              -Djib.to.auth.username=${DOCKER_USER} \
                              -Djib.to.auth.password=${DOCKER_PASS}
                        '''
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
                        set -e
                        sed -i '' 's|argocd-pipe:.*|argocd-pipe:${BUILD_NUMBER}|' boot-deployment.yml
                        git add boot-deployment.yml
                        git config user.name "${env.GIT_USERNAME}"
                        git config user.email "${env.GIT_EMAIL}"
                        git commit -m "[UPDATE] ${BUILD_NUMBER} image versioning" || echo "No changes to commit"
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
