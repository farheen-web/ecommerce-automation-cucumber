
pipeline {
    agent any

   
        stage('Verify Docker Environment') {
            steps {
                bat 'where docker'
                bat 'docker --version'
                bat 'docker compose version'
                bat 'docker --context default version'
            }
        }

    }

    post {
        always {
            publishHTML(target: [
                allowMissing: true,
                alwaysLinkToLastBuild: true,
                keepAll: true,
                reportDir: 'target',
                reportFiles: 'cucumber-report.html',
                reportName: 'Cucumber HTML Report'
            ])

            archiveArtifacts artifacts: 'target/cucumber-report.html',
                             allowEmptyArchive: true
        }

        success {
            echo 'Cucumber BDD tests passed!'
        }

        failure {
            echo 'Cucumber BDD pipeline failed. Check the console output.'
        }

        cleanup {
            bat 'docker --context default compose down --remove-orphans'
        }
    }
}
