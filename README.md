# TeachingAssistant
课程教学辅助网站

主要完成3个功能：

```
1.课程信息的查看、上传和下载
2.论坛
3.在线聊天室
```


## 技术栈

```
前端 vue，vue-router ,vuex
后端 JFinal
数据库 mysql
通讯 websocket
脚手架工具 vue-cli
```


在线访问：http://120.79.213.75/ta/#/

本地部署：
后端：

```
运行resource/properties/taSQL下sql语句,修改sql连接信息。
部署到tomcat运行。
```

前端：

```
vue/config/index修改proxyTable ip为本地（例http://localhost:8000）
main.js修改axios绑定ip为本地
components/chatRoom/chatRoomDetail修改websocket ip 为本地
在vue路径下npm install
npm run dev.
```


```
访问localhost.
```



