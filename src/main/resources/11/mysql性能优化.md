#### 数据库的优化

引擎的优化

索引的优化

分库分表

读写分离

使用缓存

#### MySQL性能优化

从以下三个问题考虑：

1. 单条 SQL 运行慢
2. 部分 SQL 运行慢
3. 整个 SQL 运行慢

### 问题 1：单条 SQL 运行慢

#### 问题分析

造成单条 SQL 运行比较慢的常见原因有以下两个：

1. 未正常创建或使用索引；
2. 表中数据量太大。

#### 解决方案 1：创建并正确使用索引

**索引**是一种能帮助 MySQL 提高查询效率的主要手段，因此一般情况下我们遇到的单条 SQL 性能问题，通常都是由于未创建或为正确使用索引而导致的，所以在遇到单条 SQL 运行比较慢的情况下，你**首先要做的就是检查此表的索引是否正常创建**。

如果表的索引已经创建了，**接下来就要检查一下此 SQL 语句是否正常触发了索引查询**，如果发生以下情况那么 MySQL 将不能正常的使用索引：

1. 在 where 子句中使用 != 或者 <> 操作符，查询引用会放弃索引而进行全表扫描；
2. 不能使用前导模糊查询，也就是 '%XX' 或 '%XX%'，由于前导模糊不能利用索引的顺序，必须一个个去找，看是否满足条件，这样会导致全索引扫描或者全表扫描；
3. 如果条件中有 or 即使其中有条件带索引也不会正常使用索引，要想使用 or 又想让索引生效，只能将 or 条件中的每个列都加上索引才能正常使用；
4. 在 where 子句中对字段进行表达式操作。

**因此你要尽量避免以上情况**，除了正常使用索引之外，我们也可以**使用以下技巧来优化索引的查询速度**：

1. 尽量使用主键查询，而非其他索引，因为主键查询不会触发回表查询；
2. 查询语句尽可能简单，大语句拆小语句，减少锁时间；
3. 尽量使用数字型字段，若只含数值信息的字段尽量不要设计为字符型；
4. 用 exists 替代 in 查询；
5. 避免在索引列上使用 is null 和 is not null。

> 回表查询：普通索引查询到主键索引后，回到主键索引树搜索的过程，我们称为回表查询。

#### 解决方案 2：数据拆分

当表中数据量太大时 SQL 的查询会比较慢，你可以考虑拆分表，让每张表的数据量变小，从而提高查询效率。

##### 1.垂直拆分

指的是将表进行拆分，把一张列比较多的表拆分为多张表。比如，用户表中一些字段经常被访问，将这些字段放在一张表中，另外一些不常用的字段放在另一张表中，插入数据时，使用事务确保两张表的数据一致性。垂直拆分的原则：

- 把不常用的字段单独放在一张表；
- 把 text，blog 等大字段拆分出来放在附表中；
- 经常组合查询的列放在一张表中。

##### 2.水平拆分

指的是将数据表行进行拆分，表的行数超过200万行时，就会变慢，这时可以把一张的表的数据拆成多张表来存放。通常情况下，我们使用取模的方式来进行表的拆分，比如，一张有 400W 的用户表 users，为提高其查询效率我们把其分成 4 张表 users1，users2，users3，users4，然后通过用户 ID 取模的方法，同时查询、更新、删除也是通过取模的方法来操作。

##### 表的其他优化方案：

1. 使用可以存下数据最小的数据类型；
2. 使用简单的数据类型，int 要比 varchar 类型在 MySQL 处理简单；
3. 尽量使用 tinyint、smallint、mediumint 作为整数类型而非 int；
4. 尽可能使用 not null 定义字段，因为 null 占用 4 字节空间；
5. 尽量少用 text 类型，非用不可时最好考虑分表；
6. 尽量使用 timestamp，而非 datetime；
7. 单表不要有太多字段，建议在 20 个字段以内。

### 问题 2：部分 SQL 运行慢

#### 问题分析

部分 SQL 运行比较慢，我们首先要做的就是先定位出这些 SQL，然后再看这些 SQL 是否正确创建并使用索引。也就是说，我们先要使用慢查询工具定位出具体的 SQL，然后再使用问题 1 的解决方案处理慢 SQL。

#### 解决方案：慢查询分析

MySQL 中自带了慢查询日志的功能，开启它就可以用来记录在 MySQL 中响应时间超过阀值的语句，具体指运行时间超过 long_query_time 值的 SQL，则会被记录到慢查询日志中。long_query_time 的默认值为 10，意思是运行 10S 以上的语句。默认情况下，MySQL 数据库并不启动慢查询日志，需要我们手动来设置这个参数，如果不是调优需要的话，一般不建议启动该参数，因为开启慢查询日志会给 MySQL 服务器带来一定的性能影响。慢查询日志支持将日志记录写入文件，也支持将日志记录写入数据库表。使用 `mysql> show variables like '%slow_query_log%';` 来查询慢查询日志是否开启，执行效果如下图所示：![img](https://mmbiz.qpic.cn/mmbiz_png/HrWw6ZuXCshrFNZFo2b0P7MnibH9iaic0g1SUeUaMDHyIZufwOtE6SGqzia440vMjapNniav4a8TTzQ7tcanZibjj2hA/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)slow_query_log 的值为 OFF 时，表示未开启慢查询日志。

##### 开启慢查询日志

开启慢查询日志，可以使用如下 MySQL 命令：

> mysql> set global slow_query_log=1

不过这种设置方式，只对当前数据库生效，如果 MySQL 重启也会失效，如果要永久生效，就必须修改 MySQL 的配置文件 my.cnf，配置如下：

> slow_query_log =1 slow_query_log_file=/tmp/mysql_slow.log

**当你开启慢查询日志之后，所有的慢查询 SQL 都会被记录在 slow_query_log_file 参数配置的文件内，默认是 /tmp/mysql_slow.log 文件**，此时我们就可以打开日志查询到所有慢 SQL 进行逐个优化。

### 问题 3：整个 SQL 运行慢

#### 问题分析

当出现整个 SQL 都运行比较慢就说明目前数据库的承载能力已经到了峰值，因此我们需要使用一些数据库的扩展手段来缓解 MySQL 服务器了。

#### 解决方案：读写分离

一般情况下对数据库而言都是“读多写少”，换言之，数据库的压力多数是因为大量的读取数据的操作造成的，我们可以采用数据库集群的方案，使用一个库作为主库，负责写入数据；其他库为从库，负责读取数据。这样可以缓解对数据库的访问压力。

MySQL 常见的读写分离方案有以下两种：

##### **1.应用层解决方案**

可以通过应用层对数据源做路由来实现读写分离，比如，使用 SpringMVC + MyBatis，可以将 SQL 路由交给 Spring，通过 AOP 或者 Annotation 由代码显示的控制数据源。优点：路由策略的扩展性和可控性较强。缺点：需要在 Spring 中添加耦合控制代码。

##### **2.中间件解决方案**(Mycat(重量级)  shardingJDBC（轻量级）)

通过 MySQL 的中间件做主从集群，比如：Mysql Proxy、Amoeba、Atlas 等中间件都能符合需求。优点：与应用层解耦。缺点：增加一个服务维护的风险点，性能及稳定性待测试，需要支持代码强制主从和事务。

## 扩展知识：SQL 语句分析

在 MySQL 中我们可以使用 explain 命令来分析 SQL 的执行情况，比如：

> explain select * from t where id=5;

如下图所示：

![img](https://mmbiz.qpic.cn/mmbiz_png/HrWw6ZuXCshrFNZFo2b0P7MnibH9iaic0g1RWibJ9dic3ciaMKGACJk6BLIicqMIDd75ibCibdcdtmP0Ze849KhiciaNPuRLw/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

其中：

（1）id: select查询的序列号，包含一组数字，表示查询中执行select字句或操作的顺序。

id有三种值：

id相同，执行顺序由上至下。
id不同，如果是子查询，id的序号会递增，id越大优先级越高，先被执行。
id相同不同，如1，1，2，id相同可以认为是一组，从上往下顺序执行。在所有组中，id越大，优先级越高，越先被执行。

（2）select_type：查询的类型，主要是用于区别普通查询，联合查询，子查询等的复杂查询。

Select_type的值有：simple, primary, subQuery, derived, union, union result.

Simple：简单的select查询，查询中不包含子查询或者union；
Primary：主查询，查询中若包含任何子查询，最外层查询则被标记为主查询；
subQuery：子查询，在select或者where列表中包含子查询。
Derived：临时表，在from列表中包含的子查询被标记为derived(衍生)；
Union：第二个select出现在union之后，则被标记为union；
Union result：从union表中获取结构的select；

（3）table：显示这一行的数据是关于哪张表的；

  (4）type：访问类型排列；

显示查询使用了哪一种类型，从最好到最差依次是：
System > const > eq_ref > ref > range > index > ALL

一般来说，得保证查询到range级别，最好能到ref级别。

值解释：

System：表中只有一行记录（等于系统表），这是const类型的特例，平时不会出现，这个可以忽略不计。

Const：表示通过索引一次就找到了，const用于比较primary key或者unique索引。因为只匹配一行数据，所以很快。例如将主键置于where列表中，mysql将该查询转为一个常量。

Eq_ref：唯一性索引扫描，对于每个索引键，表中只有一条记录与之匹配。常见于主键或唯一索引扫描。

Ref：非唯一性索引扫描，返回匹配某个单独值得所有行。

Range：只检索给定范围的行，使用一个索引来选择行。Key列显示使用了哪个索引，一般就是在你的where语句中出现了between, <, >, in等的查询。这种范围扫描比全表扫描，因为它只需要开始于索引的某一点，而结束于另一点，不用扫描全部索引。

Index：full index scan全索引扫描，index与all的区别为：index类型只遍历索引树，这通常比all快，因为索引文件通常比数据文件小。也就是说虽然index和all都是读全表，但index是从索引中读的，而all是从硬盘中读的。

All：全表扫描，是最慢的类型。


（5）possible_keys：显示可能应用在这张表中的索引，一个或者多个。

查询涉及到的字段上若存在索引，则该索引将被列出，但不一定被查询实际使用。

（6）Key：实际使用的索引。

如果为null，则没有使用索引。查询中若出现了覆盖索引，则该索引仅出现在key列表中。

（7）Key_len：表示索引中使用的字节数，可通过该列计算查询中使用的索引的长度。

在不损失精确性的情况下，长度越短越好。

Key_len显示的值为索引字段的最大可能长度，并非实际使用长度，即key_len是根据表定义计算而得，不是通过表内检索出的。

（8）Ref：显示索引的具体一列被使用了，也可能是一个const。

哪些列或者常量被用于查找索引列上的值。

（9）Rows：根据表统计信息及索引选用情况，大致估算出找到所需的记录所需要读取的行数。数值越低越好。

（10）Extra：不适合在其他列显示，但十分重要的额外信息。

Extra有如下几个值：

Using filesort
Using temporary
Using index
Using where
using join buffer
impossible where,
Select tables optimized away
distinct
值的解释：

Using filesort：
说明mysql会对数据使用一个外部的索引排序，而不是按照表内的索引顺序进行读取。Mysql中无法利用索引完成的排序操作称为“文件排序”。

Using temporary：
使用了临时表保存中间结果。常见于排序order by和分组查询group by。
Using filesort和Using temporary都是不太好的结果，会影响性能。

Using index：表示相应的select操作中使用了覆盖索引，避免访问了表的数据行，效率不错！
如果同时出现了using where，表明索引被用来执行索引键值的查找；
如果没有同时出现using where，表明索引用来读取数据而非执行查找动作。

Using where：表示使用了where过滤。

Using join buffer：使用了连接缓存。

impossible where：表示where条件总是false。例如：where name=”zs” and name=”ls”;

Select tables optimized away：没有group by子句下，基于索引优化Max/Min操作。

Distinct：优化distinct，在找到第一匹配的元组后即停止找到同样值得动作。

