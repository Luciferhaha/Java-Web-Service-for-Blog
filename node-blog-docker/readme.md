## Docker构建博客镜像

使用Node.js搭建，Mac构建Docker镜像，原始项目为分布式系统课中的聊天室项目

### 构建Docker镜像：

```sh
docker build -t chatroom .
```

### 镜像部署：

```sh
docker run -d -p 8080:3000 chatroom
```

浏览器打开 http://localhost:8080
