**一、使用技术**
1. 前端技术 
   - iview  基于Vue.js的开源UI组件库
   - axios  HTTP异步请求框架
2. 后端技术
   - jdk1.8
   - spring mvc 4.3.10.RELEASE
   - shiro 1.3.2 安全框架
   - druid 0.2.23 数据库连接池
   - mybatis 3.4.4 ORM框架
   - mybatis-plus 2.1.2 mybatis插件 简化分页、CURD操作
   - cors-filter 2.6 HTTP跨域处理工具包
   - guava 23.0 google JAVA工具包
   - freemarker 2.3.26 模板解析
   
**二、前端开发**
1. 安装Node.js，安装iview 执行 npm install iview --save
2. 在com.warrior.web/src/main/webapp/WEB-INF/views目录下执行 npm install
3. 安装 axios、qs 插件 npm install axios --save  npm install qs --save
4. 启动服务(开发环境) npm run dev 服务启动后 打开 http://127.0.0.1:8080
5. 编译打包(生成环境) npm run build

**三、后端开发**
1. 安装Lombok插件
   - Intellij IDEA 安装 File > Setting > Plugins  -> 点击 Browse repositories... -> 搜索 Lombok Plugin -> 点击 Install plugin -> 重启
   - Eclipse 安装 运行 java -jar lombok.jar -> 点击 Install/Update -> 重启