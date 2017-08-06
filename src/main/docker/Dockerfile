FROM tomcat:8.0-jre8
RUN rm -rf /usr/local/tomcat/webapps
ENV POSTGRES_DB promeets_db
ENV POSTGRES_URL localhost:5432
ENV POSTGRES_USER pmadmin
ENV POSTGRES_PASSWORD admin
EXPOSE 8080
COPY promeets-*.war /usr/local/tomcat/webapps/ROOT.war