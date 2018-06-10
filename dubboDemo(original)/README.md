


1)第三次作业创建MAVEN项目
项目结构：
主要分三大模块：
dubbo-api : 存放公共接口；
dubbo-consumer :　调用远程服务；
dubbo-consumer2 :　调用远程服务；
dubbo-provider : 提供远程服务。






2)在项目中定义服务接口：该接口需单独打包，在服务提供方和消费方共享。


```
package com.alibaba.dubbo.demo;
import java.util.List;

public interface DemoService {
    List<String> getPermissions(Long id);
}


3)创建dubbo-provider的MAVEN项目(有独立的pom.xml，用来打包供消费者使用)。
实现公共接口，此实现对消费者隐藏：

```
package com.alibaba.dubbo.demo.impl;

import com.alibaba.dubbo.demo.DemoService;

import java.util.ArrayList;
import java.util.List;
public class DemoServiceImpl implements DemoService {
    public List<String> getPermissions(Long id) {
        List<String> demo = new ArrayList<String>();
        demo.add(String.format("Permission_%d", id - 1));
        demo.add(String.format("Permission_%d", id));
        demo.add(String.format("Permission_%d", id + 1));
        return demo;
    }
}
```



4)用Spring配置声明暴露服务

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:dubbo="http://code.alibabatech.com/schema/dubbo"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://code.alibabatech.com/schema/dubbo
       http://code.alibabatech.com/schema/dubbo/dubbo.xsd">
    <!--定义了提供方应用信息，用于计算依赖关系；在 dubbo-admin 或 dubbo-monitor 会显示这个名字，方便辨识-->
    <dubbo:application name="demotest-provider" owner="programmer" organization="dubbox"/>
    <!--使用 zookeeper 注册中心暴露服务，注意要先开启 zookeeper-->
    <dubbo:registry address="zookeeper://localhost:2181"/>
    <!-- 用dubbo协议在20880端口暴露服务 -->
    <dubbo:protocol name="dubbo" port="20880" />
    <!--使用 dubbo 协议实现定义好的 api.PermissionService 接口-->
    <dubbo:service interface="com.alibaba.dubbo.demo.DemoService" ref="demoService" protocol="dubbo" />
    <!--具体实现该接口的 bean-->
    <bean id="demoService" class="com.alibaba.dubbo.demo.impl.DemoServiceImpl"/>
</beans>
```

5)启动远程服务：
```
package com.alibaba.dubbo.demo.impl;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.io.IOException;
public class Provider {
        public static void main(String[] args) throws IOException {
            ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("provider.xml");
            System.out.println(context.getDisplayName() + ": here");
            context.start();
            System.out.println("服务已经启动...");
            System.in.read();
        }
    }
```
6)创建dubbo-consumer的MAVEN项目(可以有多个consumer，但是需要配置好)。
调用所需要的远程服务：

通过Spring配置引用远程服务：

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:dubbo="http://code.alibabatech.com/schema/dubbo"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
       http://code.alibabatech.com/schema/dubbo http://code.alibabatech.com/schema/dubbo/dubbo.xsd">
    <dubbo:application name="demotest-consumer" owner="programmer" organization="dubbox"/>
    <!--向 zookeeper 订阅 provider 的地址，由 zookeeper 定时推送-->
    <dubbo:registry address="zookeeper://localhost:2181"/>
    <!--使用 dubbo 协议调用定义好的 api.PermissionService 接口-->
    <dubbo:reference id="permissionService" interface="com.alibaba.dubbo.demo.DemoService"/>
</beans>
```

7)启动Consumer,调用远程服务：
```
package com.alibaba.dubbo.consumer;
import com.alibaba.dubbo.demo.DemoService;
import org.springframework.context.support.ClassPathXmlApplicationContext;
public class Consumer {
    public static void main(String[] args) {
        //测试常规服务
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("consumer.xml");
        context.start();
        System.out.println("consumer start");
        DemoService demoService = context.getBean(DemoService.class);
        System.out.println("consumer");
        System.out.println(demoService.getPermissions(1L));
    }
}
```
8）运行项目，先确保provider已被运行后再启动consumer模块：

