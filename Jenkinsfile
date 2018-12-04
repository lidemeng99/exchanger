pipeline {
    agent any

    stages {
        stage('Prepare') {
            steps {
                echo 'Preparing..'
                echo "[+] --> Job: ${env.BUILD_URL}"

            }
        }
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
}
