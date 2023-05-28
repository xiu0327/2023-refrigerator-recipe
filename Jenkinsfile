pipeline {
	agent any
	stages {
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
