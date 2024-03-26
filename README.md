# SportsSystem体育场馆系统

⭐基于SpringBoot，Spring Security，Redis，JWT，websocket，hutool框架开发的广东海洋大学体育馆管理系统

github地址：https://github.com/cocochimp/SportsSystem



# 启动命令

> 运行手册

* 启动项目
  * 下载包命令：npm install
  * 启动命令：npm run serve
  * 生产环境：npm run build

* 运行Redis：redis-server.exe



> 运行环境

- java:8（jdk1.8）
- mysql:5.6.35
- redis:3.0



#  项目结构

> 核心依赖

| 依赖                         | 版本          |
| ---------------------------- | ------------- |
| Spring Boot                  | 2.3.8.RELEASE |
| springSecurity（SpringBoot） | 2.3.8.RELEASE |
| redis（SpringBoot）          | 2.3.8.RELEASE |
| validation（SpringBoot）     | 2.3.8.RELEASE |
| websocket（SpringBoot）      | 2.3.8.RELEASE |
| Mybatis Plus                 | 3.4.1         |
| mysql-connector-java         | 8.0.22        |
| junit                        | 4.13.1        |
| swagger2                     | 2.7.0         |
| jjwt                         | 0.9.0         |
| lombok                       | 1.18.16       |
| kaptcha                      | 0.0.9         |
| hutool                       | 5.3.3         |
| commons-lang3                | 3.10          |
| aliyun-sdk-oss               | 3.10.2        |
| easyexcel                    | 3.1.3         |

* 数据库简易E-R图

![简易ER图](https://cocochimp-img.oss-cn-beijing.aliyuncs.com/23-03/20230904230439.png)



# 📚页面展示📚

## 登录界面

### 管理员登录

* 点击登录时，登录模块SpringSecurity整合JWT实现用户身份认证和授权，控制用户的资源访问权限；
* 其中验证码是通过JWT技术实现的；

![image-20230602122052036](https://cocochimp-img.oss-cn-beijing.aliyuncs.com/23-03/20230602122052.png)

| 身份       | 账号  | 密码   |
| ---------- | ----- | ------ |
| 超级管理员 | admin | 123456 |
| 器材管理员 | equip | 123456 |
| 场地管理员 | place | 123456 |
| 赛事管理员 | comp  | 123456 |
| 普通管理院 | user  | 123456 |



### 重置信息

* 点击重置登录信息会清空

![image-20230602123031589](https://cocochimp-img.oss-cn-beijing.aliyuncs.com/23-03/20230602123031.png)



## 客户端

### 首页展示

* 用户前端展示模块分为：”首页“、”器材列表“、”竞赛列表“和”公告列表“，以下是首页展示：

![image-20230602123159975](https://cocochimp-img.oss-cn-beijing.aliyuncs.com/23-03/20230602123200.png)

* 其中，首页有“场地信息”、“通知公告”、“场地预约情况”以及“赛事详细信息”
  * 在“场地信息”中，用户可以通过场地信息简述了解到各个场地的基本信息，包括：”联系人“、”联系电话“、”场地地址”等信息；
  * 在“通知公告”中，用户可以第一时间知道管理员发布的公告信息，了解到学校新的运动比赛成绩；
  * 在“场地预约”中，用户可以通过自己的实际情况，预约每个场地今天、明天两天12个时间段的场地，不需要进行管理员审批就可以进行预约；
  * 在“赛事详细信息”中，用户可以通过检索所有的赛事信息，对所有的比赛进行查看、报名等操作；



### 器材列表

* 客户端可以对器材进行租借操作

![image-20230602124706790](https://cocochimp-img.oss-cn-beijing.aliyuncs.com/23-03/20230602124706.png)

* 填写信息：租用数量&租用时间

![image-20230602124804962](https://cocochimp-img.oss-cn-beijing.aliyuncs.com/23-03/20230602124805.png)

* 通过WebSocket技术，超级管理员/器材管理员弹出器材审批通知

![image-20230602124957013](https://cocochimp-img.oss-cn-beijing.aliyuncs.com/23-03/20230602124957.png)

* 进行处理操作

![image-20230602125122278](https://cocochimp-img.oss-cn-beijing.aliyuncs.com/23-03/20230602125122.png)

* 管理员进行器材审批操作：“通过”

![image-20230602125146155](https://cocochimp-img.oss-cn-beijing.aliyuncs.com/23-03/20230602125146.png)

* 用户端：点击我的订单【已经通过租用状态】

![image-20230602125242738](https://cocochimp-img.oss-cn-beijing.aliyuncs.com/23-03/20230602125242.png)

* 点击归还，提交订单

![image-20230602125328862](https://cocochimp-img.oss-cn-beijing.aliyuncs.com/23-03/20230602125328.png)

* 管理员可以对器材状态进行判断：“回收”或“损坏”
* 当点击“损坏”时：【填写金额和原因】

![image-20230602125514297](https://cocochimp-img.oss-cn-beijing.aliyuncs.com/23-03/20230602125514.png)

* 用户端再进行租借时会失效：用户进入失信名单

![image-20230602125648495](https://cocochimp-img.oss-cn-beijing.aliyuncs.com/23-03/20230602125648.png)

* 用户需要在“我的订单”中将订单进行赔偿才能继续进行租借



### 竞赛列表

* 首页

![image-20230602111703445](https://cocochimp-img.oss-cn-beijing.aliyuncs.com/23-03/20230602121821.png)

* 报名详情页面

![image-20230602130024611](https://cocochimp-img.oss-cn-beijing.aliyuncs.com/23-03/20230602130024.png)



### 公告列表

* 展示所有公告（列出10条）

![image-20230602130225112](https://cocochimp-img.oss-cn-beijing.aliyuncs.com/23-03/20230602130225.png)

* 详情信息页面

![image-20230602130235229](https://cocochimp-img.oss-cn-beijing.aliyuncs.com/23-03/20230602130235.png)



## 系统管理

### 用户管理

* 展示信息页面

![image-20230602130413733](https://cocochimp-img.oss-cn-beijing.aliyuncs.com/23-03/20230602130413.png)

* 分配角色

![image-20230602130501824](https://cocochimp-img.oss-cn-beijing.aliyuncs.com/23-03/20230602130501.png)

* 重置密码：简化操作，初始密码为888888

![image-20230602130513324](https://cocochimp-img.oss-cn-beijing.aliyuncs.com/23-03/20230602130513.png)

* 编辑：对用户进行统一操作

![image-20230602130540093](https://cocochimp-img.oss-cn-beijing.aliyuncs.com/23-03/20230602130540.png)



### 角色管理

* 展示信息页面

![image-20230602130635515](https://cocochimp-img.oss-cn-beijing.aliyuncs.com/23-03/20230602130635.png)

* 分配权限：对角色的权限分配进行精细化（按钮）分配

![image-20230602130710470](https://cocochimp-img.oss-cn-beijing.aliyuncs.com/23-03/20230602130710.png)

* 编辑：

![image-20230602130745819](https://cocochimp-img.oss-cn-beijing.aliyuncs.com/23-03/20230602130745.png)



### 菜单管理

* 展示信息页面：
* 类型分为“目录”、“菜单”和“按钮”

![image-20230602130841982](https://cocochimp-img.oss-cn-beijing.aliyuncs.com/23-03/20230602130842.png)

* 编辑：

![image-20230602130931264](https://cocochimp-img.oss-cn-beijing.aliyuncs.com/23-03/20230602130931.png)



### 公告管理

* 展示信息页面：

![image-20230602131022616](https://cocochimp-img.oss-cn-beijing.aliyuncs.com/23-03/20230602131022.png)

* 编辑：

![image-20230602131044295](https://cocochimp-img.oss-cn-beijing.aliyuncs.com/23-03/20230602131044.png)



## 器材管理

### 器材列表管理

* 页面信息展示：

![image-20230602131142859](https://cocochimp-img.oss-cn-beijing.aliyuncs.com/23-03/20230602131142.png)

* 编辑：

![image-20230602131205116](https://cocochimp-img.oss-cn-beijing.aliyuncs.com/23-03/20230602131205.png)



### 器材租借管理

* 页面信息展示：

![image-20230602131234057](https://cocochimp-img.oss-cn-beijing.aliyuncs.com/23-03/20230602131234.png)

* 租借状态：
  * 待审批：审批
  * 归还：回收/损坏
  * 归还成功

* 待审批：【审批】

![image-20230602131330874](https://cocochimp-img.oss-cn-beijing.aliyuncs.com/23-03/20230602131330.png)

* 归还：【回收】

![image-20230602131401860](https://cocochimp-img.oss-cn-beijing.aliyuncs.com/23-03/20230602131401.png)

* 归还：【损坏】

![image-20230602131417845](https://cocochimp-img.oss-cn-beijing.aliyuncs.com/23-03/20230602131417.png)

* 归还成功：

![image-20230602131450407](https://cocochimp-img.oss-cn-beijing.aliyuncs.com/23-03/20230602131450.png)



## 赛事管理

### 赛事列表管理

* 页面信息展示：

![image-20230602131548306](https://cocochimp-img.oss-cn-beijing.aliyuncs.com/23-03/20230602131548.png)

* 编辑：赛事图片使用OSS进行云存储

![image-20230602131603181](https://cocochimp-img.oss-cn-beijing.aliyuncs.com/23-03/20230602131603.png)



## 场地管理

### 场地信息管理

* 页面信息展示：

![image-20230602131709491](https://cocochimp-img.oss-cn-beijing.aliyuncs.com/23-03/20230602131709.png)

* 编辑：

![image-20230602131742426](https://cocochimp-img.oss-cn-beijing.aliyuncs.com/23-03/20230602131742.png)



### 场地预约管理

* 页面信息展示：

![image-20230602131837043](https://cocochimp-img.oss-cn-beijing.aliyuncs.com/23-03/20230602131837.png)
