pipeline {
    agent any

    stages {
        stage('Checkout Code') {
            steps {
                // Clones code automatically if using Pipeline from SCM
                checkout scm
            }
        }

        stage('Compile') {
            steps {
                // Compiles framework and test classes
                bat 'mvn clean test-compile' // On Windows: bat 'mvn clean test-compile'
            }
        }

        stage('Run Automated Tests') {
            steps {
                // Runs testng.xml via Maven Surefire
                bat 'mvn test' // On Windows: bat 'mvn test'
            }
        }
    }

    post {
        always {
            // Publishes TestNG / Surefire reports regardless of pass/fail
            junit '**/target/surefire-reports/*.xml'
        }
        failure {
            echo 'Test execution failed! Review the surefire report or screenshots.'
        }
    }
}
