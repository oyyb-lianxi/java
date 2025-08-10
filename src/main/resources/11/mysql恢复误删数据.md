# 如何快速恢复MySQL误删表数据

相信后端研发的同学在开发过程经常会遇到产品临时修改线上数据的需求，如果手法很稳那么很庆幸可以很快完成任务，很不幸某一天突然手一抖把表里的数据修改错误或者误删了，这个时候你会发现各种问题反馈接踵而来。

如果身边有BDA或者有这方面经验的同事那么可以很快解决这个问题，如果没有那么希望这篇文章可以帮到你。

**第一步**：保证mysql已经开启binlog，查看命令：

查看binlog是否开启

```
show variables like '%log_bin%';
```

查看binlog存放日志文件目录（本案例如下图）：

```
show variables like '%datadir%';
```

![image-20230430174106045](C:\Users\Liu\AppData\Roaming\Typora\typora-user-images\image-20230430174106045.png)

OFF，需开启，值为ON，已开启。

如果没有开启binlog，也没有预先生成回滚SQL，那可能真的无法快速回滚了。对存放重要业务数据的MySQL，强烈建议开启binlog。

**第二步**：进入binlog文件目录，找出日志文件

![image-20230430174151108](C:\Users\Liu\AppData\Roaming\Typora\typora-user-images\image-20230430174151108.png)

**第三步**：切换到mysqlbinlog目录（当线上数据出现错误的时候首先可以询问具体操作人记录时间点，这个时候可以借助mysql自带的binlog解析工具mysqlbinlog，具体位置在mysql安装目录**/mysql/bin/下）

![image-20230430174418002](C:\Users\Liu\AppData\Roaming\Typora\typora-user-images\image-20230430174418002.png)

**第四步**：通过mysqlbinlog工具命令查看数据库增删改查记录（必须切换到mysqlbinlog目录才有效）

例子1：查询2018-11-12 09:00:00到2018-11-13 20:00:00 数据库为 youxi 的操作日志，输入如下命令将数据写入到一个备用的txt文件中

```
 mysqlbinlog --no-defaults --database=youxi --start-datetime="2018-11-12 09:00:00" --stop-datetime="2018-11-13 20:00:00" /data/mysql/mysql-bin.000015    > template_coupon_tb_product_category.txt
```

例子2：查询2018-11-12 09:00:00到2018-11-13 20:00:00 数据库为 youxi 的操作日志，并输出到屏幕上

```
mysqlbinlog --no-defaults --database=youxi --start-datetime="2018-11-12 09:00:00" --stop-datetime="2018-11-13 20:00:00" /data/mysql/mysql-bin.000015 |more
```

例子3：查询2018-11-12 09:00:00到2018-11-13 20:00:00 数据库为 youxi 的操作日志，并且过滤出 只包括 template_coupon_tb_product_category 表数据的操作记录 ，输入如下命令将数据写入到一个备用的txt文件中

```
mysqlbinlog --no-defaults --database=youxi --start-datetime="2018-11-12 09:00:00" --stop-datetime="2018-11-13 20:00:00" /data/mysql/mysql-bin.000015   | grep template_coupon_tb_product_category > template_coupon_tb_product_category.txt

```

![image-20230430174453164](C:\Users\Liu\AppData\Roaming\Typora\typora-user-images\image-20230430174453164.png)

```java

mysqlbinlog 命令的语法格式：
mysqlbinlog mysql-bin.0000xx | mysql -u用户名 -p密码 数据库名

--------------------------------------------------------
常用参数选项解释：
--start-position=875 起始pos点
--stop-position=954 结束pos点
--start-datetime="2016-9-25 22:01:08" 起始时间点
--stop-datetime="2019-9-25 22:09:46" 结束时间点
--database=zyyshop 指定只恢复zyyshop数据库(一台主机上往往有多个数据库，只限本地log日志)
-------------------------------------------------------- 
不常用选项： 
-u --user=name 连接到远程主机的用户名
-p --password[=name] 连接到远程主机的密码
-h --host=name 从远程主机上获取binlog日志
--read-from-remote-server 从某个MySQL服务器上读取binlog日志
```

**第五步**：利用第四步输出的sql语句或者txt文本进行语句过滤，重新插入数据或更新数据。