FROM postgres:alpine

ENV POSTGRES_DB promeets_db
ENV POSTGRES_USER pmadmin
ENV POSTGRES_PASSWORD admin

EXPOSE 5432

COPY /sql/scheme.sql /docker-entrypoint-initdb.d/
