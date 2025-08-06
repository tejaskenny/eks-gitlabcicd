FROM mysql:5.7
COPY mysql-init  docker-entrypoint-initdb.d
ENV MYSQL_ROOT_PASSWORD = rootpass
ENV MYSQL_DATABASE=test_db
