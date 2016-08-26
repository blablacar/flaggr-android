# Release a new version
1. Update `VERSION_NAME` in `gradle.properties`
2. In a shell run `./gradlew build & ./gradlew bintrayUpload`