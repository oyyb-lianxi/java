# Feign远程调用原理

![img](https://upload-images.jianshu.io/upload_images/6271376-285b0cf66bc1b24c.png?imageMogr2/auto-orient/strip|imageView2/2/w/627/format/webp)

## 1、Feign介绍

Feign是一个http请求调用的轻量级框架，可以以Java接口注解的方式调用Http请求。Spring Cloud引入 Feign并且集成了Ribbon实现客户端负载均衡调用。

#### Feign解决了什么问题？

封装了Http调用流程，更适合面向接口化的变成习惯

## 2、Feign工作原理

#### Feign远程调用流程图

![img](https://upload-images.jianshu.io/upload_images/6271376-7635e2dc9b32e3ec.png?imageMogr2/auto-orient/strip|imageView2/2/w/871/format/webp)

image.png

#### (1) 基于面向接口的动态代理方式生成实现类

在使用feign 时，会定义对应的接口类，在接口类上使用Http相关的注解，标识HTTP请求参数信息

在Feign 底层，通过基于面向接口的动态代理方式生成实现类，将请求调用委托到动态代理实现类，基本原理如下所示：

![img](https://upload-images.jianshu.io/upload_images/14126519-4949493085b0f547.png?imageMogr2/auto-orient/strip|imageView2/2/w/779/format/webp)

image

#### (2) 根据Contract协议规则，解析接口类的注解信息，解析成内部表现：

![img](https://upload-images.jianshu.io/upload_images/6271376-cebfed0fa4f18190.png?imageMogr2/auto-orient/strip|imageView2/2/w/915/format/webp)

image.png

#### (3) 基于 RequestBean，动态生成Request

根据传入的Bean对象和注解信息，从中提取出相应的值，来构造Http Request 对象

#### (4) 使用Encoder 将Bean转换成 Http报文正文（消息解析和转码逻辑）

Feign 最终会将请求转换成Http 消息发送出去，传入的请求对象最终会解析成消息体，如下所示：

![img](https://upload-images.jianshu.io/upload_images/14126519-b5c571b44f453707.png?imageMogr2/auto-orient/strip|imageView2/2/w/719/format/webp)

image

#### (5) 拦截器负责对请求和返回进行装饰处理

在请求转换的过程中，Feign 抽象出来了拦截器接口，用于用户自定义对请求的操作，比如，如果希望Http消息传递过程中被压缩，可以定义一个请求拦截器。

#### (6) 日志记录

#### (7) 基于重试器发送HTTP请求

Feign 内置了一个重试器，当HTTP请求出现IO异常时，Feign会有一个最大尝试次数发送请求

#### (8) 发送Http请求

Feign 真正发送HTTP请求是委托给 feign.Client 来做的。

Feign 默认底层通过JDK 的 java.net.HttpURLConnection 实现了feign.Client接口类,**在每次发送请求的时候，都会创建新的HttpURLConnection 链接**，这也就是为什么默认情况下Feign的性能很差的原因。可以通过拓展该接口，使用Apache HttpClient 或者OkHttp3等基于连接池的高性能Http客户端。

**Feign 整体框架非常小巧，在处理请求转换和消息解析的过程中，基本上没什么时间消耗。真正影响性能的，是处理Http请求的环节。**

## 3、Feign优化

### （1）GZIP压缩

gzip是一种数据格式，采用deflate算法压缩数据。当Gzip压缩到一个纯文本数据时，可以减少70％以上的数据大小。

gzip作用：网络数据经过压缩后实际上降低了网络传输的字节数，最明显的好处就是可以加快网页加载的速度。

只配置Feign请求-应答的GZIP压缩



```bash
# feign gzip
# 局部配置。只配置feign技术相关的http请求-应答中的gzip压缩。
# 配置的是application client和application service之间通讯是否使用gzip做数据压缩。
# 和浏览器到application client之间的通讯无关。
# 开启feign请求时的压缩， application client -> application service
feign.compression.request.enabled=true
# 开启feign技术响应时的压缩，  application service -> application client
feign.compression.response.enabled=true
# 设置可以压缩的请求/响应的类型。
feign.compression.request.mime-types=text/xml,application/xml,application/json
# 当请求的数据容量达到多少的时候，使用压缩。默认是2048字节。
feign.compression.request.min-request-size=512
```

配置全局的GZIP压缩



```bash
# spring boot gzip
# 开启spring boot中的gzip压缩。就是针对和当前应用所有相关的http请求-应答的gzip压缩。
server.compression.enabled=true
# 哪些客户端发出的请求不压缩，默认是不限制
server.compression.excluded-user-agents=gozilla,traviata
# 配置想压缩的请求/应答数据类型，默认是 text/html,text/xml,text/plain
server.compression.mime-types=application/json,application/xml,text/html,text/xml,text/plain
# 执行压缩的阈值，默认为2048
server.compression.min-response-size=512
```

### （2）替换为HttpClient客户端（使用HTTP连接池提供性能）

Feign的HTTP客户端支持3种框架，分别是；HttpURLConnection、HttpClient、OKHttp。Feign中默认使用HttpURLConnection。

- HttpURLConnection是JDK自带的HTTP客户端技术，并不支持连接池，如果要实现连接池的机制，还需要自己来管理连接对象。对于网络请求这种底层相对复杂的操作，如果有可用的其他方案，也没有必要自己去管理连接对象。
- Apache提供的**HttpClient**框架相比传统JDK自带的HttpURLConnection，它封装了访问http的请求头，参数，内容体，响应等等；它不仅使客户端发送HTTP请求变得容易，而且也方便了开发人员测试接口（基于Http协议的），即提高了开发的效率，也方便提高代码的健壮性；另外高并发大量的请求网络的时候，**还是用“HTTP连接池”提升吞吐量**。
- OKHttp是一个处理网络请求的开源项目,是安卓端最火热的轻量级框架。**OKHttp拥有共享Socket,减少对服务器的请求次数，通过连接池,减少了请求延迟等技术特点**。

本案例中，通过替换Feign底层的HTTP客户端实现为HttpClient，来提升Feign的通讯性能。

修改全局配置文件：开启feign技术对底层httpclient的依赖。 切换底层实现技术。



```bash
feign.httpclient.enabled=true
```