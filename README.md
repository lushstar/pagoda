# pagoda
基于 Spring AOP 做的一款动态插件，可以对 Spring Bean 进行动态包装。

# 结构说明

- `pagoda-web`：插件 dashboard 控制台，是一个单独的进程

- `pagoda-service`：插件的业务逻辑实现，是一个单独的进程

- `pagoda-config`：与数据库的交互层级

- `pagoda-client`：客户端引入的 jar 包

- `pagoda-embed`：内嵌的一些 aop 插件

- `pagoda-demo`：用于测试的工程

# 数据库脚本

- 暂无

# 启动顺序

- 暂无

# 实验顺序

- 先对 `pagoda-embed` 工程打包，`mvn clean package` 之后会得到一个包，把包移动到 site 目录下，当做远程站点目录

- 启动 `pagoda-demo` 工程，然后分别执行如下命令

- localhost:7777/install：插件安装

- localhost:7777/active：插件激活

- localhost:7777/test：插件测试

- localhost:7777/disable：插件禁用

- localhost:7777/test：插件测试

- localhost:7777/active：插件激活

- localhost:7777/test：插件测试

- localhost:7777/uninstall：插件卸载

- localhost:7777/test：插件测试
