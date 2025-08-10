#### 一 、Redis 是单线程还是多线程？

这个问题可以一分为二：

Redis 是单线程，是因为 Redis 使用文件事件分派器来处理事件队列，且该文件事件分派器是单线程的，所以 Redis 才叫做单线程的模型。

Redis 是多线程，是因为持久化、异步删除、集群数据同步等操作都是由数据读写线程之外其他线程执行的，所以 Redis 也可以叫做多线程模型。

之所以大家都说是单线程，是因为单线程的 Redis 性能很高。

#### 二 、为什么单线程的 Redis 性能很高？

1 Redis 的数据都是存储在内存中，内存操作很快。

2 Redis 使用 IO 多路复用技术来处理并发事件。

内存这个很容易理解，不多说，接下来我们详细分析一下 IO 多路复用技术。

#### 三 、什么是 IO 多路复用技术？

如图为 Redis 的客户端与服务端整体事件处理流程图：

![img](https://img-blog.csdnimg.cn/20210418164030207.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2phY2sxbGl1,size_16,color_FFFFFF,t_70)

流程图主要由以下几部分组成：

多路 Socket。
IO 多路复用程序。
事件队列。
文件事件分派器。
多类型事件处理器。
其中 IO 多路复用程序，监听多个 Socket。

以一个 Socket 的多种事件类型举例说明。

![img](https://img-blog.csdnimg.cn/20210418223009712.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L2phY2sxbGl1,size_16,color_FFFFFF,t_70)

##### 1 建立连接事件

客户端向服务端建立连接，IO 多路复用监听到建立连接的请求事件后，将请求事件写入队列，文件事件分派器从队列中获取请求事件，交给连接应答处理器处理。

连接应答处理器与客户端创建一个 Socket。

##### 2 写请求事件

客户端发送一个 set key value 的请求，IO 多路复用监听到写事件后，将事件写入队列，文件事件分派器从队列中获取请求事件，交给命令请求处理器处理。

命令请求处理器在内存实现 set key value 的操作。

##### 3 返回结果事件

客户端准备好收到结果，IO 多路复用监听到返回结果事件后，将事件写入队列，文件事件分派器从队列中获取请求事件，交给命令回复处理器处理。

命令回复处理器返回结果。
