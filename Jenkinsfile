
pipeline {
    agent any

    parameters {
        choice(
            name: 'CUCUMBER_TAGS',
            choices: [
    '@regression',
    '@smoke',
    '@negative',
    '@regression and not @negative',
    '@noMatchingScenario'
],
            description: 'Choose which Cucumber scenarios to execute'
        )
    }

    environment {
        COMPOSE_EXE = 'C:\\Users\\farhe\\AppData\\Local\\Programs\\DockerDesktop\\resources\\cli-plugins\\docker-compose.exe'
    }

    stages {
        stage('Verify Docker Environment') {
            steps {
                bat 'where docker'
                bat 'docker --version'
                bat '"%COMPOSE_EXE%" version'
                bat 'docker --context default version'
            }
        }

        stage('Run Cucumber Tests with Docker Grid') {
            steps {
                withEnv(["CUCUMBER_TAGS=${params.CUCUMBER_TAGS}"]) {
    bat '"%COMPOSE_EXE%" --context default up --abort-on-container-exit --exit-code-from test-runner'
}
            }
        }

        stage('Validate Cucumber Results') {
            steps {
                bat 'powershell.exe -NoProfile -ExecutionPolicy Bypass -File scripts\\validate-cucumber-results.ps1'
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

            archiveArtifacts(
                artifacts: 'target/cucumber-report.html',
                allowEmptyArchive: true
            )
        }

        success {
            echo 'Cucumber BDD tests passed!'
        }

        failure {
            echo 'Cucumber BDD pipeline failed. Check the console output.'
        }

        cleanup {
            bat(
                returnStatus: true,
                script: '"%COMPOSE_EXE%" --context default down --remove-orphans'
            )
        }
    }
}
