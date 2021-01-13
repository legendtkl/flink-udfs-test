## README

This is a test repo for flink udfs.

### 1.dependency-version1
This dependency module. We can install it into local mvn.
```bash
$ cd dependency-version1
$ mvn package
$ cd target
$ mvn install:install-file -Dfile=/Path/to/dependency-version1-0.1-SNAPSHOT.jar -DgroupId=com.alibaba.vvp -DartifactId=baseutil -Dversion=1.0 -Dpackaging=jar
```

Then we can use it in pom.
```xml
<dependency>
    <groupId>com.alibaba.vvp</groupId>
    <artifactId>baseutil</artifactId>
    <version>1.0</version>
</dependency>
```

### 2.dependency-version2
Kind of same like `dependency-version1`. After build the module, we can install it to local mvn as another version.
```bash
mvn install:install-file -Dfile=/Path/to/dependency-version2-0.1-SNAPSHOT.jar -DgroupId=com.alibaba.vvp -DartifactId=baseutil -Dversion=2.0 -Dpackaging=jar
```

Use it in pom.
```xml
<dependency>
    <groupId>com.alibaba.vvp</groupId>
    <artifactId>baseutil</artifactId>
    <version>2.0</version>
</dependency>
```

`dependency-version2` is not compatible with `dependency-version1` with different method signatures.

### 3.flink-udf1
Flink udf `MyScalarFunc1` with reference to `dependency-version1`. We can upload the package to VVP and register it.

### 4.flink-udf2
Flink udf `MyScalarFunc2` with reference to `dependency-version1`.

### 5.SQL
With the following SQL to test UDF jar conflict with not compatible versions.

SQL like:
```sql
create TEMPORARY table jdbc_source (
  id STRING,
  name STRING
) with (
  'connector' = 'jdbc',
  'url' = 'jdbc:mysql://xxx.mysql.rds.aliyuncs.com:3306/jdbc',
  'table-name' = 'test_source',
  'username' = 'username',
  'password' = 'password!'
);

create TEMPORARY table jdbc_sink (
  id STRING,
  name STRING
) with (
  'connector' = 'jdbc',
  'url' = 'jdbc:mysql://xxx.mysql.rds.aliyuncs.com:3306/jdbc',
  'table-name' = 'test_sink',
  'username' = 'username',
  'password' = 'password!'
);

insert into jdbc_sink select MyScalarFunc5(id), MyScalarFunc6(name) from jdbc_source;
```