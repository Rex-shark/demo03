
### mysql 啟動docker容器指令 持久化路徑放在D槽的版本 適用windows

- docker run -itd --name mysql8_docker -e MYSQL_ROOT_PASSWORD=123456 -p 3306:3306 -v /d/mydata:/var/lib/mysql mysql:8.0

### mysql 啟動docker容器指令 持久化路徑放在~/mydata 適用nac

- docker run -itd --name mysql8_docker -e MYSQL_ROOT_PASSWORD=123456 -p 3306:3306 -v ~/mydata:/var/lib/mysql mysql:8.0

---

### 查docker內MySQL或redis的ip

1. 先用 docker ps 查出容器名稱與Id
2. inspect -f "{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}" 容器名稱或Id 

---

### 建立redis鏡像與容器

1. docker pull redis:latest
2. docker run --name my-redis -d redis:latest(這是錯誤的，沒有映射埠)
3. docker run --name my-redis -d -p 6379:6379 redis:latest
4. docker run --name my-redis -d -p 6379:6379 -e REDIS_PASSWORD=123456 redis:latest(有密碼版本)

---

### 建立RabbitMQ鏡像與容器

1. docker pull rabbitmq:3.12-management
2. docker run -d --name rabbitmq -p 5672:5672  -p 15672:15672  -e RABBITMQ_DEFAULT_USER=admin -e RABBITMQ_DEFAULT_PASS=123456 rabbitmq:3.12-management
3. http://localhost:15672 查看MQ

---

### 建立oracle鏡像

1. 下載鏡像 :  docker pull container-registry.oracle.com/database/express:latest
2. run container : docker container create -it --name oracle-test  -p 1521:1521  -e ORACLE_PWD=123456  container-registry.oracle.com/database/express:latest

---

###  建立 PostgreSQL鏡像


---

###  建立Java image 將jar與dockerfile放置同資料夾
- docker build -t image名稱 .
- docker build -t test-api-i .

---

###  建立Java container (~路徑應該是MAC版，待確認)
- docker run -d -p {網址要連的port}:{application.properties指定的port} --name {container名稱} -v {~/tmp4log:/springboot} {image名稱}
- docker run -d -p 8030:8030 --name test-api-c -v ~/tmp4log:/app/springboot test-api-i