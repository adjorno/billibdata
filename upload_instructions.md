- increase version in build.gradle
- run "./gradle uploadArchives"
- login to https://oss.sonatype.org
- go to "Staging Repositories"
  scroll down to "comm14n-*" repo
  check that new version has been uploaded
- "Close" repo (takes few minutes)
- "Release" repo (may take up 1 hour)