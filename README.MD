## 系统简介
[FEBS](https://gitee.com/github-16661027/project)是一个简单高效的后台权限管理系统。项目基础框架采用全新的Java Web开发框架 —— Spring Boot，消除了繁杂的XML配置，使得二次开发更为简单；数据访问层采用Mybatis，同时引入了通用Mapper和PageHelper插件，可快速高效的对单表进行增删改查操作，消除了大量传统XML配置SQL的代码；安全框架采用时下流行的Apache Shiro，可实现对按钮级别的权限控制；前端页面使用Bootstrap构建，主题风格为时下Google最新设计语言Material Design，并提供多套配色以供选择。FEBS意指：Fast，Easy use，Beautiful和Safe🙄。
<!--more-->
## 功能模块
系统功能模块组成如下所示：
```
├─系统管理
│  ├─字典管理
│  ├─用户管理
│  ├─菜单管理
│  ├─角色管理
│  └─部门管理
├─系统监控
│  ├─在线用户
│  └─系统日志
├─任务调度
│  ├─定时任务
│  └─调度日志
└─网络资源
    ├─One一个
    │  ├─散文
    │  ├─绘画
    │  └─语文
    ├─天气查询
    ├─影视资讯
    │  ├─即将上映
    │  └─正在热映
    └─每日一文
```
## 技术选型
### 后端

- 基础框架：Spring Boot

- 持久层框架：Mybatis

- 安全框架：Apache Shiro

- 摸板引擎：Thymeleaf

- 数据库连接池：阿里巴巴Druid

- 缓存框架：Ehcache

- 日志打印：logback

- 其他：fastjson，poi，javacsv，quartz等。

### 前端
 
- 基础框架：Bootstrap

- JavaScirpy框架：jQuery

- 消息组件：Bootstrap notify

- 提示框插件：SweetAlert2

- 树形插件：jsTree

- 树形表格插件：jqTreeGrid

- 表格插件：BootstrapTable

- 表单校验插件：jQuery-validate

- 多选下拉框插件：multiple-select

- 图表插件：Highcharts

### 开发环境

- 语言：Java

- IDE：Eclipse Oxygen

- 依赖管理：Maven

- 数据库：Oracle 11g

- 版本管理：SVN，git

## 系统预览

![QQ截图20180319141311.png](http://mrbird.cc/img/FEBS/QQ截图20180319141311.png)

![QQ截图20180319141358.png](http://mrbird.cc/img/FEBS/QQ截图20180319141358.png)

![QQ截图20180319141531.png](http://mrbird.cc/img/FEBS/QQ截图20180319141531.png)

![QQ截图20180319141602.png](http://mrbird.cc/img/FEBS/QQ截图20180319141602.png)

![QQ截图20180319141642.png](http://mrbird.cc/img/FEBS/QQ截图20180319141642.png)

![QQ截图20180319141722.png](http://mrbird.cc/img/FEBS/QQ截图20180319141722.png)

![QQ截图20180319141750.png](http://mrbird.cc/img/FEBS/QQ截图20180319141750.png)

![QQ截图20180319141940.png](http://mrbird.cc/img/FEBS/QQ截图20180319141940.png)

## 主题预览

![QQ截图20180319103921.png](http://mrbird.cc/img/FEBS/QQ截图20180319103921.png)

![QQ截图20180319104022.png](http://mrbird.cc/img/FEBS/QQ截图20180319104022.png)

![QQ截图20180319104249.png](http://mrbird.cc/img/FEBS/QQ截图20180319104249.png)

![QQ截图20180319104347.png](http://mrbird.cc/img/FEBS/QQ截图20180319104347.png)

![QQ截图20180319104436.png](http://mrbird.cc/img/FEBS/QQ截图20180319104436.png)

![QQ截图20180319104529.png](http://mrbird.cc/img/FEBS/QQ截图20180319104529.png)

![QQ截图20180319104634.png](http://mrbird.cc/img/FEBS/QQ截图20180319104634.png)


## 部署方式

码云地址： https://gitee.com/github-16661027/project 

GitHub 地址： https://github.com/wuyouzhuguli/FEBS

下载后以maven项目导入，建议打包成war包部署。账号mrbird，密码123456，访问地址[localhsot](localhost)。

本软件使用 [Apache License 2.0](http://www.apache.org/licenses/LICENSE-2.0) 协议，请严格遵照协议内容