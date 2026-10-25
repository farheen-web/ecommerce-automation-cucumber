
pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                echo 'Using the Cucumber BDD project workspace'
            }
        }

        stage('Run Cucumber Tests with Docker Grid') {
            steps {
                bat 'docker --context default compose up --build --abort-on-container-exit --exit-code-from test-runner'
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
