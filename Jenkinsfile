pipeline {
  agent any
  stages {
    stage('Prepare') {
      steps {
        echo 'Preparing..'
      }
    }
    stage('Build') {
      steps {
        echo 'Building..'
        sh 'mvn install'
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
