pipeline {
	agent any
	stages {
		stage("build") {
			steps {
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
