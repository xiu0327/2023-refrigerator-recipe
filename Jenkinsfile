pipeline {
	agent any
	stages {
        stage('Checkout') {
            steps {
                git branch: 'back-nh-v3', url: 'https://github.com/xiu0327/2023-refrigerator-recipe.git'
            }
        }
		stage("build") {
			steps {
                cd back
				sh './gradlew clean build -x test'
			}
		}
		stage("test") {
			steps {
				echo 'testing the applicaiton...'
			}
		}
		stage("deploy") {
			steps {
				echo 'deploying the applicaiton...'
			}
		}
	}
}
