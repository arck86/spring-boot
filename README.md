<h2>spring-boot</h2>

#Spring-boot-jwt docker

#Crear red para comunicarse entre dockers
```sh
docker network create mired
```

#Desplegar base de datos
```sh
docker run -d -p 33060:3306 --net=mired --name mysql-db  -e MYSQL_ROOT_PASSWORD=prueba --mount src=mysql-db-data,dst=/var/lib/mysql mysql
```

#Se crea imagen de docker
```sh
 docker build -t spring-boot-jwt:2 .
```

#Se despliega la aplicacion
```sh
 docker run -p 8080:8080 --net=mired spring-boot-jwt:2 .
```
