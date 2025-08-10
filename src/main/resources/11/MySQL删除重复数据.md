下面详细介绍去重步骤：

```
DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `id` int(10) DEFAULT NULL,
  `name` varchar(200) DEFAULT NULL,
  `age` int(3) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

insert  into `student`(`id`,`name`,`age`) values (1,'cat',12),(2,'dog',13),(3,'camel',25),(4,'cat',32),(5,'dog',42);
```

目标：我们要去掉name相同的数据。

先看看哪些数据重复了

```
SELECT name,count( 1 ) 
FROM
 student 
GROUP BY
NAME 
HAVING
 count( 1 ) > 1;
```

输出：

| name | count(1) |
| ---- | -------- |
| cat  | 2        |
| dog  | 2        |



name为cat和dog的数据重复了，每个重复的数据有两条；

```
Select * From 表 Where 重复字段 In (Select 重复字段 From 表 Group By 重复字段 Having Count(1)>1)
```

## 1、删除全部重复数据，一条不留

直接删除会报错

```
DELETE 
FROM
 student 
WHERE
 NAME IN (
 SELECT NAME 
 FROM
  student 
 GROUP BY
 NAME 
HAVING
 count( 1 ) > 1)
```

报错：

```
1093 - You can't specify target table 'student' for update in FROM clause, Time: 0.016000s
```

**原因是**：更新这个表的同时又查询了这个表，查询这个表的同时又去更新了这个表，可以理解为死锁。mysql不支持这种更新查询同一张表的操作

**解决办法**：把要更新的几列数据查询出来做为一个第三方表，然后筛选更新。

```
DELETE 
FROM
 student 
WHERE
 NAME IN (
 SELECT
  t.NAME 
FROM
 ( SELECT NAME FROM student GROUP BY NAME HAVING count( 1 ) > 1 ) t)
```

## 2、删除表中删除重复数据，仅保留一条

在删除之前，我们可以先查一下，我们要删除的重复数据是啥样的

```
SELECT
 * 
FROM
 student 
WHERE
 id NOT IN (
 SELECT
  t.id 
 FROM
 ( SELECT MIN( id ) AS id FROM student GROUP BY `name` ) t 
 )
```

啥意思呢，就是先通过name分组，查出id最小的数据，这些数据就是我们要留下的火种，那么再查询出id不在这里面的，就是我们要删除的重复数据。

## 开始删除重复数据，仅留一条

很简单，刚才的select换成delete即可

```
DELETE 
FROM
 student 
WHERE
 id NOT IN (
 SELECT
  t.id 
 FROM
 ( SELECT MIN( id ) AS id FROM student GROUP BY `name` ) t 
 )
```

