### BOCEPayJavaBridge

[![Build Status](https://github.com/chinayin/BOCEPayJavaBridge/workflows/JavaCI/badge.svg)](https://github.com/chinayin/BOCEPayJavaBridge/actions)
[![Author](https://img.shields.io/badge/author-@chinayin-blue.svg)](https://github.com/chinayin)
![license](https://img.shields.io/github/license/chinayin/BOCEPayJavaBridge.svg)

中行数字人民币验签JavaBride Demo.

### 部署

- 生成打包文件

```bash
./mvnw clean package
```

- 命令行方式运行

```bash
java -jar webapp-runner-[version].jar BOCEPayJavaBridge-[version].war --port 8080
```

- Docker镜像

```bash
docker pull chinayin:bocepay-java-bridge:[version]
```

### 附录：

#### 使用Alibaba开源的Java诊断工具 `Arthas`

https://arthas.aliyun.com/

下载arthas-boot.jar，然后用java -jar的方式启动：

```bash
curl -O https://arthas.aliyun.com/arthas-boot.jar
java -jar arthas-boot.jar
```

打印帮助信息：

```bash
java -jar arthas-boot.jar -h
```

选择应用java进程：

```bash
$ $ java -jar arthas-boot.jar
* [1]: 12345
```

输入dashboard，按回车/enter，会展示当前进程的信息，按ctrl+c可以中断执行。

#### References

- https://maven.apache.org/wrapper/

