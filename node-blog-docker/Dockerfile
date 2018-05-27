
FROM node:alpine
MAINTAINER Abdollo Tian

WORKDIR /app
COPY package.json .
RUN npm install --registry=https://registry.npm.taobao.org

COPY . /app
CMD ["npm","start"]

EXPOSE 3000
