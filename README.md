# 🚀 ContinuityIns 后端项目安装指南（Windows）

## 📋 系统要求

- ✅ Windows 10/11
- ✅ JDK 17 或更高版本
- ✅ Maven 3.6.0+
- ✅ MySQL 8.0+
- ✅ 至少 2GB 内存

## 🛠️ 环境配置

### 📥 安装 JDK

1. 从 [Oracle官网](https://www.oracle.com/java/technologies/downloads/#java17) 下载 JDK 17
2. 运行安装程序，按照向导完成安装
3. 设置环境变量:
   - 右键 `此电脑` → `属性` → `高级系统设置` → `环境变量`
   - 新建系统变量 `JAVA_HOME`: `C:\Program Files\Java\jdk-17`
   - 编辑 `Path` 变量，添加 `%JAVA_HOME%\bin`
4. 验证安装:
   ```cmd
   java -version
   ```

### 📥 安装 Maven

1. 从 [Maven官网](https://maven.apache.org/download.cgi) 下载最新版本
2. 解压到合适的位置，如 `C:\Program Files\Maven`
3. 设置环境变量:
   - 新建系统变量 `MAVEN_HOME`: `C:\Program Files\Maven`
   - 编辑 `Path` 变量，添加 `%MAVEN_HOME%\bin`
4. 验证安装:
   ```cmd
   mvn -version
   ```

### 📥 安装 MySQL

1. 从 [MySQL官网](https://dev.mysql.com/downloads/installer/) 下载 MySQL Installer
2. 运行安装程序，选择 `Server only` 或 `Custom` 安装
3. 设置 root 密码并记住它
4. 完成安装并确保 MySQL 服务已启动
5. 验证安装:
   ```cmd
   mysql -u root -p
   ```

## 🗄️ 数据库配置

1. 打开命令提示符，登录 MySQL:
   ```cmd
   mysql -u root -p
   ```

2. 创建数据库:
   ```sql
   CREATE DATABASE IF NOT EXISTS ContinuityIns
   DEFAULT CHARACTER SET utf8mb4
   DEFAULT COLLATE utf8mb4_unicode_ci;
   ```

3. 导入项目数据库脚本:
   ```cmd
   mysql -u root -p ContinuityIns < 项目路径\src\main\resources\mysql.sql
   ```

## 📁 获取项目代码

1. 通过 Git 克隆项目:
   ```cmd
   git clone [项目Git仓库URL]
   cd ContinuityIns
   ```

2. 或下载项目ZIP包并解压到合适位置

## ⚙️ 项目配置

1. 在 `src\main\resources` 目录下找到 `application.properties.template`
2. 复制并重命名为 `application.properties`
3. 编辑配置文件，填写以下必要信息:

```properties
# 数据库配置
spring.datasource.username=root
spring.datasource.password=你的MySQL密码

# 邮箱配置
spring.mail.username=你的邮箱地址
spring.mail.password=邮箱授权码

# 阿里云OSS配置（如果使用图片上传）
aliyun.oss.endpoint=oss终端节点
aliyun.oss.accessKeyId=访问ID
aliyun.oss.accessKeySecret=访问密钥
aliyun.oss.bucketName=存储桶名称
aliyun.oss.roleArn=RAM角色ARN
CDNPoint=CDN访问点

# AI API配置
aiApi.baseURL=AI服务基础URL
aiApi.apiId=AI服务API密钥

# 搜索API配置
search.baseURL=搜索服务基础URL
search.apiId=搜索服务API密钥
```

## 🚀 构建和运行项目

### 📦 使用命令行

1. 在项目根目录打开命令提示符
2. 构建项目:
   ```cmd
   mvn clean package
   ```

3. 运行项目:
   ```cmd
   java -jar target\ContinuityIns-*.jar
   ```

### 💻 使用 IDE (IntelliJ IDEA)

1. 打开 IntelliJ IDEA
2. 选择 `File` → `Open` 并选择项目目录
3. 等待 Maven 加载依赖
4. 找到主类 `org.ContinuityIns.Application`
5. 右键点击 → `Run Application`

## ✅ 验证安装

1. 打开浏览器，访问: `http://localhost:8081/`

2. 测试 AI 聊天功能:
   - 使用 Postman 或其它 API 测试工具
   - 向 `http://localhost:8081/ai/chat` 发送 POST 请求
   - 请求体:
   ```json
   {
     "messages": [{"role": "user", "content": "你好"}],
     "model": "deepseek-v3"
   }
   ```

3. 支持的 AI 模型:
   - deepseek-v3
   - deepseek-r1
   - qwq-plus
   - qwen-max-2025-01-25

## ❓ 常见问题

### 🔴 数据库连接失败

- 检查 MySQL 服务是否启动:
  1. 按 `Win+R` 输入 `services.msc`
  2. 找到 `MySQL` 服务，确保状态为"正在运行"
- 验证数据库用户名和密码是否正确
- 确认数据库名称为 `ContinuityIns`

### 🔴 Java 相关错误

- 确认 Java 版本为 17+: `java -version`
- 检查 JAVA_HOME 路径是否正确
- 如遇内存问题，可修改 JVM 参数:
  ```cmd
  java -Xmx1024m -jar target\ContinuityIns-*.jar
  ```

### 🔴 端口占用

- 如果 8081 端口被占用:
  1. 修改 `application.properties` 中的 `server.port` 值
  2. 或关闭占用端口的应用:
     ```cmd
     netstat -ano | findstr 8081
     taskkill /F /PID [进程ID]
     ```

### 🔴 Maven 构建失败

- 检查 Maven 设置: `mvn -version`
- 尝试清理依赖缓存:
  ```cmd
  mvn clean
  mvn dependency:purge-local-repository
  ```

## 📞 技术支持

若遇到无法解决的问题，请提交 Issue 到项目 GitHub 仓库或联系技术支持团队。

---

💡 **提示**: 项目默认端口为 **8081**，请确保该端口未被占用!
