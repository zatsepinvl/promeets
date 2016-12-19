FROM tomcat:8.0-jre8
COPY /target/promeets-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war