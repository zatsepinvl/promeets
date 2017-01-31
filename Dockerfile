FROM tomcat:8.0-jre8
RUN rm -rf /usr/local/tomcat/webapps/ROOT
ADD ./target/promeets-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war