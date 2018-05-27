# Java-Web-Service-for-Blog
Back-End Introduction (Blog43)

Run this with intellj idea 

We are going to build a java web blog application based on restful structure. Our blog application will implement basic functions such like login, enrol, post an acticle and comment with other's contents. We already have a remoted server, and we will build related web service with Spring MVC structure. We try our effort to do the best.


Spring MVC + Hibernate + bootstrap + Mysql + Maven 

 Maven is a tool could import jars by increasings dependency tags on pom.xml
 
 Hibernate is an open source object-relational mapping framwork that could encapsulates JDBC with very lighweight
 object and maps POJO to database tables
 
 If you wanna run this ，you should change your mysql settings in the  src/main/resources/META-INF/persistence.xml
 Modifey the database name and password with your mysql settings.
 You should make sure two tables (user, Blog) in your database.
 
 mysql querys :
 
 CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nickname` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) 


 
 
 Front-End Introducation (myapp)
 
 download myapp files ,enter below in the commander line 
 
 npm i
 
 npm start 
 
 ## Docker构建博客镜像

使用Node.js搭建，Mac构建Docker镜像

### 构建Docker镜像：

```sh
docker build -t chatroom .
```

### 镜像部署：

```sh
docker run -d -p 8080:3000 chatroom
```

浏览器打开 http://localhost:8080

 
