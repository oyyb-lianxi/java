# Redis高级

## Redis第一天回顾

### 1、Redis常用数据类型

### 2、数据类型的应用场景

### 3、redis持久化机制；区别？如何选用？

## 今日课程目标

1. 清楚redis对于过期数据是如何删除的（三种）；
2. 清楚redis在内存满了的时候的数据淘汰策略；
3. 熟悉Redis主从复制流程，清楚其中可能存在的问题及对应解决方案；
4. 熟悉Redis高可用实现方式；
5. 了解Redis-cluster大致架构流程及基本原理；
6. 明白Redis面试常见问题并知晓如何回答；

## 01-03_ Redis 对于过期的数据删除是怎么去实现的？

访问删除（惰性删除）、定时删除（到时间就删除）、定期删除（随机抽查，重点筛查）；

![image-20210519212014236](.\assets\image-20210519212014236.png)

![image-20210519205101890](.\assets\image-20210519205101890.png)



## 04_Redis内存不足时，继续往Redis里面存储数据Redis会如何？

![image-20210519222329532](.\assets\image-20210519222329532.png)

有多种策略可以选择：使用频次低的、很久未使用的、快要过期的、随机选择、直接拒绝 报错 OOM；

如果使用了策略也无法清除出足够的空间也会OOM；可以调大内存、或者分布式部署最终解决这样的问题；

## 05_什么是主从复制

**实现高可用的方案有哪些？**

1、主从同步、读写分离；（数据量不大，写操作不频繁、访问较多）

2、哨兵模式、redis-cluster模式；

**主从复制结构：**

![image-20210519225048825](.\assets\image-20210519225048825.png)·

- slave分担master负载，并根据需求的变化，改变slave的数 量，通过多个从节点分担数据读取负载，大大提高Redis服务器并发量与数据吞吐量
- 故障恢复：当master出现问题时，由slave提供服务，实现快速的故障恢复
- 数据冗余：实现数据热备份，是持久化之外的一种数据冗余方式
- 高可用基石：基于主从复制，构建哨兵模式与集群，实现Redis的高可用方案



## 06-10_主从复制流程是怎样的？

三个阶段：

1、建立连接：slave连接master；

![image-20210520013340093](.\assets\image-20210520013340093.png)

2、数据同步：master数据同步到slave（RDB+AOF）



![image-20210520013306954](.\assets\image-20210520013306954.png)

3、命令传播：将增量命令传播到slave（命令传播）（类似AOF）

![image-20210520014118419](.\assets\image-20210520014118419.png)



主从节点在数据同步阶段，主节点会根据当前状态的不同执行不同复制操作，包括：全量复制 和 部分复制，这

- **全量复制**：用于首次复制或者其他不能进行部分复制的情况。全量复制是一个非常重的操作，一般我们都要规避它
- **部分复制**：用于从节点短暂中断的情况（网络中断、短暂的服务宕机）。部分复制是一个非常轻量级的操作，因为它只需要将中断期间的命令同步给从节点即可，相比于全量复制，它显得更加高效。

![image-20210622132456013](.\assets\image-20210622132456013.png)

## 09 本机多个端口启Redis

1、将redis.windows-service.conf复制一份，改名为相应文件：redis.windows-service-6380.conf，以6380为例

​		更改配置文件中的端口为指定端口，port 6380

2、安装服务

redis-server.exe --service-install --service-name redis_6380 redis.windows-service-6380.conf

运行成功后可进入services.msc，查看服务列表，是否安装成功：

可以直接在上面的服务列表右击启动、停止服务；也可以通过下面命令启动和停止

3.启动服务
redis-server.exe–service-start --service-name redis_6380
4.停止服务
redis-server.exe --service-stop --service-name redis_6380
5.卸载服务
redis-server.exe --service-uninstall --service-name redis_6380

## 08_Redis主从同步（新增slave）数据时，需要使用到Redis持久化方案么？存在数据丢失的风险么？如何避免？

全量复制 -> 增量复制

**复制缓冲区**（存储增量命令），同时会执行bgsave（RDB）持久化，发送RDB文件给slave，slave会清空原数据，执行RDB文件恢复数据，发送确认给master，全量同步完成；发送缓冲区的增量指令给slave，slave执行aof增量恢复；初始化数据同步完成；

**数据同步过程中存在数据丢失的风险**

![image-20210418112638535](.\assets\image-20210418112638535.png)

**slave同步数据时避免被访问**

![image-20210418112904543](.\assets\image-20210418112904543.png)

切记：不要同时添加多台slave节点进行数据同步，适当错峰，避免master网络IO因同时同步多个slave被占满；

**命令传播：**

slave接入成功，初始化同步完成后，接下来就进行命令传播实现实时增量同步数据了；增量同步数据同样需要用到复制缓冲区；

![image-20210418114950182](.\assets\image-20210418114950182.png)

## 11_Redis主从同步存在哪些问题？如何解决？

- master重启问题 -- runid设置相同
- 复制缓冲区过小 -- 修改复制缓冲区大小
- 网络中断 -- 通过设置合理的超时时间、提高ping指令发送的频度
- 数据不一致 -- 放置在同一个机房部署、slave延迟过大，暂时屏蔽程序对该slave的数据访问

## 14_手动恢复故障步骤

我们先来回想下在主从架构模式下，当主节点发生故障后，我们是如何手动恢复的呢？

1. 我们选定一个从节点对其执行 `slaveof no one` 命令，使其变成一个新的主节点
2. 将其他从节点变成新主节点的从节点，执行命令 `slaveof 新IP 新port`
3. 更新客户端的 Redis 连接信息，重启应用
4. 启动发送了故障的主节点，执行 `slaveof 新IP 新port` 使其变成新主节点的从节点

## 12-16_Redis master 宕机了怎么办？

Redis 主从部署时，可以启动 哨兵模式、（拥有监控及选择的功能）

![image-20210520140130132](.\assets\image-20210520140130132.png)

![image-20210418184613525](.\assets\image-20210418184613525.png)

![image-20210418184644644](.\assets\image-20210418184644644.png)

**redis 哨兵模式下，重新选举如何实现的？**

1、监控阶段 - 获取各个redis节点信息（数据节点+哨兵）；建立信息网络，哨兵跟哨兵、哨兵跟redis各个数据节点；

2、定时通信，通知；（检测网络）

3、检测master状态，投票表决master最终状态确认；

4、投票选择哨兵领导(Sentinel 选举)；

5、领导确认新master；

- 1、从从节点列表中选择一个节点作为新的主节点，选择的策略如下：
  - 过滤掉不健康的节点（主观下线、断线），5 秒内没有回复过 Sentinel 节点 ping 响应、与主节点失联超过 `down-after-milliseconds * 10`秒
  - 选择优先级最高级别的节点，如果不存在则继续
  - 选择复制偏移量最大的节点（数据最完整），存在则返回，不存在则继续
  - 选择 runid 最小的节点
- 2、在新的主节点上执行 `slaveof no one`，让其变成主节点
- 3、向剩余的从节点发送命令,让它们成为新主节点的从节点



## 17-21_Redis分布式集群如何理解？集群中哈希槽有什么意义？

Redis3.0版本开始支持分布式集群部署，优点：提升存储空间、访问效率、并能够保证高可用；

槽：每个Redis节点上都对应一些槽范围，使用Redis存储跟访问数据时可以根据key通过算法计算出对应的槽位置，就会去对应的节点上存储、查询数据；

**集群模式下访问key**,如果key不在我们直接访问的Redis节点上，客户端会进行自动重定向请求目标节点获取数据；

![image-20210418205745366](.\assets\image-20210418205745366.png)

## 22_缓存预热是什么操作？

提前将需要缓存的数据保存到Redis中；

## 23-27-综合问题

1. **Redis缓存数据存在哪些具体问题？**

   

2. **缓存雪崩、穿透、击穿如何解决？**

   **雪崩：**

   多级缓存(不同的过期时间)、让大批量的key不同时失效（根据不同类别的数据设置不一样的过期时间、设置过期时间后面加随机值）

   **击穿：**

   多级缓存(不同的过期时间)、分布式锁、高热数据永不过期（过期时间设置长一些）

   **穿透：**

   key设置一定的规则，让攻击者不容易随意模仿key；ip、userId做 监控判断 黑名单策略；布隆过滤器

3. **redis监控用什么实现？**

   benchmark、zabbix（运维使用）



## 额外拓展面试题

### 1、Redis是单线程的，redis为何设置为单线程呢？单线程还那么快，为什么？

跟Redis IO模型有关，还有因为Redis的瓶颈不是cpu的运行速度，而往往是网络带宽和机器的内存大小。再说了，单线程切换开销小，容易实现，既然单线程容易实现，而且CPU不会成为瓶颈，那就顺理成章地采用单线程的方案了。 4.0版本支持多线程异步删除；

https://www.cnblogs.com/wuwuyong/p/11756823.html、https://blog.csdn.net/weixin_43582101/article/details/88908647

### 2、Redis多指令操作如何保证原子性？

因为redis执行指令的单线程特性，所以单个指令的执行是能够保证原子性的；

 如果存在多指令同时操作，需要保证原子性，使用lua脚本，https://zhuanlan.zhihu.com/p/77484377 （redis分布式锁实现中就需要使用到）

### 3、Redis分布式锁是如何实现的？

https://www.jianshu.com/p/fa602453413b

#加锁姿势
1、setNotExist命令了解；
2、为了防止死锁，需要设置超时时间；
3、设置锁：key-value时需要同时指定超时时间，避免服务突然宕机产生死锁问题；
4、加锁成功设置延迟检查任务，比如超时时间的一半执行锁状态检查，若资源依旧处于处理占有中，则需要对锁做‘续命’操作，防止超时自动释放锁导致进程间并发安全问题，并且需要设置续命次数上限，必要时打印关键日志警告；
5、设置锁时需要将value设置为一个能够区分各个客户端的id(比如uuid)，用于释放锁时判断，避免误删；(意思就是a节点设置的锁，只能是a节点来删除(除非达到续命上限，超时自动释放))；

```java
    private static final String LOCK_SUCCESS = "OK";
    // nxxx NX|XX, NX -- Only set the key if it does not already exist. XX -- Only set the key if it already exist.
    private static final String SET_IF_NOT_EXIST = "NX";
    // expire time units: EX = seconds; PX = milliseconds
    private static final String SET_WITH_EXPIRE_TIME = "PX";
    public static boolean tryGetDistributedLock(Jedis jedis, String lockKey, String requestId, int expireTime) {
        // nxxx NX|XX, NX -- Only set the key if it does not already exist. XX
        // expire time units: EX = seconds; PX = milliseconds
        String result = jedis.set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
        if (LOCK_SUCCESS.equals(result)) {
            return true;
        }
        return false;
    }
```
#解锁姿势
1、解锁需要保证命令的原子性，使用脚本进行封装；谁加的锁谁来解
```java
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));
        // 根据result做判断
```

## 课程目标问题：

1. **redis对于已经过期的数据是如何删除的？**

   访问删除（惰性删除）、定时删除（到时间就删除）、定期删除（随机抽查，重点筛查）；

2. **redis内存满了，再往里面存数据会如何？**

   有多种策略可以选择使用，频次低的、很久未使用的、快要过期的、随机选择、直接拒绝 报错 OOM；如果使用了策略也无法清除出足够的空间也会OOM；可以调大内存、或者分布式部署最终解决这样的问题；

3. **redis如何保证高可用？master挂了怎么办？**

   Redis 主从部署时，可以启动 哨兵模式、（拥有监控及选择的功能）；

   redis-cluster 分布式集群；

4. **你们公司redis采用的哪种方式部署的？有几个节点？**

   集群模式 3主3从，槽的概念

5. **了解redis主从复制的大致流程么？**

   建立连接、数据同步（全部复制、部分复制）、命令传播；

   复制缓冲区、运行id、偏移量 的概念；



































