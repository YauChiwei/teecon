pipeline {
  agent any
  stages {
    stage('build') {
      steps {
        git(url: 'https://github.com/YauChiwei/teecon.git', branch: 'master', poll: true, changelog: true)
      }
    }
  }
}