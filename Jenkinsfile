pipeline {
  agent {
    docker {
      image 'maven:3-alpine' 
      args '-v /Users/mac/.m2:/Users/mac/.m2' 
    }
  }
  stages {
    stage('Build') { 
      steps {
        sh 'mvn -B -DskipTests clean package' 
      }
    }
  }
}