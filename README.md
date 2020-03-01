# MyBatisGeneratorRepackaging

![](https://img.shields.io/badge/build-passing-brightgreen.svg )&nbsp;![](https://img.shields.io/badge/maven-v1.0.0--beta-red.svg)&nbsp;![](https://img.shields.io/badge/github-lightingsui-blue.svg)&nbsp;![](https://img.shields.io/badge/license-apache-yellow.svg)

## 介绍

此插件是在[MybatisGenerator](http://mybatis.org/generator/)插件的基础上开发的，旨在为开发提供便利，在使用传统的mybatis generator时，配置文件项特别多，而且想要运行起来他还需要多写一些额外的代码，虽然mybaits generator插件提供了丰富的可扩展接口，但是我们使用的东西是固定的，如果我们每次都因为我们的定制来实现相同的代码，那么无疑给开发造成了巨大的压力，本着这些问题，本人开发了MyabtisGeneratorRepackaging插件。注意，此插件**目前**仅适用于MySQL数据库！

## 功能

1. 简化了配置文件(将配置文件与springboot中的配置文件结合)
2. 对model生成的注释进行了改造
3. 可以选择性的在model上启用swaggerUI的@ApiModelProperties注解
4. 简化启动代码
5. 选择性使用Lombok插件中@Data替代set和get
6. 对生成的mapper和xml中方法进行了一些拓展
7. 改善了日志功能

## 配置文件详解

这里将配置文件进行了简化，由原来的`generatorConfig.xml`转变为了和`springboot`拥有同一个配置文件，即`application.yml`、`application.yaml`，方便对配置文件的管理，并且和`springboot`中的`mybaits`数据库连接源使用同一个`jdbc`配置，减少了配置项。

```yaml
# application.yml
spring:
  datasource:
    driverClassName: com.mysql.jdbc.Driver
    url: jdbc:mysql://localhost:3306/my_db
    username: root
    password: 123456
mybatis-generator-repackaging:
  # 加载自动生成代码的插件
  plugins:
    - toString
    - serializable
    - equalsHashCode
  # model位置
  pojo-location:
    targetProject: src\main\java
    targetPackage: com.lighting.pojo
  # mapper位置
  mapper-location:
    targetProject: src\main\java
    targetPackage: com.lighting.mapper
  # xml文件位置
  mapper-xml-location:
    targetProject: src\main\resources
    targetPackage: com.lighting.mapper
  # 表信息
  tables:
    # 表名称
    - test_table
```

在上面的配置文件模板中，列出了所有的配置项。`spring.datasource`和`mybatis`中的配置项是相同的，在`plugins`中，共有三个配置点，`toString`(为生成的`model`添加`toString`方法)、`serializable`(为`model`添加`serializable`)、`equalsHashCode`(为`model`生成`equals`方法和`hashcode`方法)。

剩下的三个位置就是对应的`model`、`mapper`、`xml`对应的地址了，`targetProject`位置为存放的位置，而`targetPackage`为存放包，二者皆不可以为空。否咋会抛出异常！

对于表信息的说明在这里有一个特别注意的点，如果不指定表信息，则默认生成配置数据库下所有的表。在`model`中，对应生成的`model`名称就是将表名称转化为大驼峰命名。例如表名称为`test_table`，则生成的`model`名称为`TestTable`。

## 注释与SwaggerUI

在`mybatis generator`原生的组件中，自动生成的注释很是让人抓狂，有种匪夷所思的感觉，那么，此插件是怎么解决这个问题的呢，此插件中将注释分为了两种，一种是普通的注释，另一种是带有`swaggerUI`的注释。那么注释信息从何而来的，很明确的答复，从数据库字段的注释而来。为了便于理解，看一下建表语句

```mysql
CREATE TABLE `tyust_student` (
  `stu_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '无意义主键',
  `stu_no` varchar(20) DEFAULT NULL COMMENT '学号',
  `stu_name` varchar(20) DEFAULT NULL COMMENT '姓名',
  PRIMARY KEY (`stu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
```

那么注释即来自字段后的`commen`，生成的`model`如下

```java
/**
 * Table Name: tyust_student
 */
public class TyustStudent {
    /** 无意义主键 **/
    private Integer stuId;

    /** 学号 **/
    private String stuNo;

    /** 姓名 **/
    private String stuName;

    ... set and get ...
}
```

`SwaggerUI`格式`model`

```java
import io.swagger.annotations.ApiModelProperty;

/**
 * Table Name: tyust_student
 */
public class TyustStudent {
    @ApiModelProperty("无意义主键")
    private Integer stuId;

    @ApiModelProperty("学号")
    private String stuNo;

    @ApiModelProperty("姓名")
    private String stuName;

    ... set and get ...
}
```

## 启动代码与注解

`MybatisGeneratorRepackaging`启动代码类似于`springboot`启动代码，如下

```java
import com.lighting.annotation.EnableComments;
import com.lighting.annotation.EnableSwaggerComments;
import com.lighting.run.MybatisGeneratorRepackaging;

@com.lighting.annotation.MybatisGeneratorRepackaging
@EnableComments
public class GeneratorApplication {
    public static void main(String[] args) {
        MybatisGeneratorRepackaging.run(GeneratorApplication.class);
    }
}
```

其中`MybatisGeneratorRepackaging`是主注解，必须加，不加会抛出异常，无法继续执行。

以下列出`MybatisGeneratorRepackaging`中全部注解(全部作用在启动类上)，并依次解释其作用

+ @CloseOverride

  **关闭代码覆盖功能，关闭此功能是，如果生成的代码是重复的，则不会覆盖原来的代码，默认为开启。**

+ @EnableComments

  **启动model字段注释。**

+ @EnableExtend

  **启动拓展方法，则会比原有的mybatis generator多出一些设定好的方法，下面会详细介绍。**

+ @EnableSwaggerComments

  **启动swaggerUI注解，注意：因为此注解优先级高于@EnableComments，如果同时指定这两个，则@EnableSwaggerComments会覆盖掉@EnableComments**

+ @EnableXmlOverwrite

  **启动xml覆盖模式。当没有启用@CloseOverride时，生成重复的xml文件默认不会覆盖原有的内容，而是在原有的内容下拼接新内容，启用@EnableXmlOverwrite 就会将新内容覆盖掉原有内容。**

+ @LombokAllArgsConstructor
  **为model添加构造方法，即使用lombok插件**

+ @LombokData

  **为model添加@Data注解，并且去掉默认生成的set和get**

+ @MybatisGeneratorRepackaging

  **主注解**

## 方法的拓展

在MybatisGeneratorRepackaging插件中，对mapper中的生成的方法进行了一些拓展，目前仅对select进行一些拓展，在以后的维护中，会增加更多的拓展方法。想要使用拓展的方法，需要在启动类中使用@EnableExtend进行启用。

+ List<TyustStudent> selectAll();

  **查询当前表中的所有内容**

+ int selectCount(TyustStudent record);

  **根据条件查询当前表中记录的数量，如果条件参数为null，则查询当前表中所有记录数量**

+ List<TyustStudent> selectByCond(TyustStudent record);

  **根据条件查询记录，如果条件为空，则相当于selectAll()**

## 日志完善

在原有插件的基础上，多了一些日志信息，详细到加载了哪些插件，加载了哪些表。

## 说明

目前中央maven仓库依赖正在进行申请，待申请成功，会将依赖信息展示在此，方便大家使用。在使用过程中，如果发现bug或者有任何意见，可以issue我，有帮助的pr将会被合并。