
### 啟動docker容器指令 持久化路徑放D槽版本 適用windows


docker run -itd --name mysql8_docker -e MYSQL_ROOT_PASSWORD=123456 -p 3306:3306 -v /d/mydata:/var/lib/mysql mysql:8.0

---

docker run
啟動並執行一個新的 Docker 容器。

-itd
多個參數的組合：

-i：讓容器進入互動模式（允許輸入資料）。
-t：分配一個虛擬終端（TTY），模擬終端介面。
-d：讓容器在背景執行。
--name mysql8_docker
指定容器名稱為 mysql8_docker，方便管理和辨識此容器。

-e MYSQL_ROOT_PASSWORD=123456
設置環境變數 MYSQL_ROOT_PASSWORD，用來設定 MySQL root 使用者的密碼為 123456。

-p 3306:3306
將容器內的 3306 端口映射到主機的 3306 端口，使得主機能透過 localhost:3306 連線到容器內的 MySQL。

-v /d/mydata:/var/lib/mysql
將主機的 /d/mydata 目錄掛載到容器內的 /var/lib/mysql，這是 MySQL 的資料存儲路徑。這樣即使容器停止或刪除，MySQL 資料庫的數據依然會保留在主機的 D:\mydata 資料夾中。

mysql:8.0
指定 MySQL 映像的版本，這裡使用的是 8.0 版本。