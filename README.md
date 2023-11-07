<h2>spring-boot</h2>

#Spring-boot-jwt docker

#Crear red para comunicarse entre dockers
```sh
docker network create mired
```

#Desplegar base de datos
```sh
docker run -d -p 33062:3306 --network=mired --name mysql-db -e MYSQL_ROOT_PASSWORD=secret -e MYSQL_DATABASE=db_springboot mysql:8.0
```

#Se crea imagen de docker
```sh
 docker build -t spring-boot-jwt:1 .
```

#Se despliega la aplicacion
```sh
 docker run -p 8080:8080 --network=mired spring-boot-jwt:1 .
```
