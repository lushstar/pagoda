# pagoda
基于 Spring AOP 做的一款动态插件，可以对 Spring Bean 进行动态包装

# 结构说明

- `pagoda-client`：客户端引入的 jar 包

- `pagoda-config`：与数据库的交互层

- `pagoda-samples`: demo 项目

- `pagoda-service`：插件的业务逻辑实现, 对外提供了 open-api, 可以部署多台节点

- `pagoda-web`：pagoda 的简易控制台, 需要单独启动

# 数据库脚本

- 详细见 `doc/init.sql`

- 注意 `plugin` 中 `address` 字段地址需要修改

# 启动顺序

- 先启动 `pagoda-service`

- 再启动 `pagoda-web`

- 最后启动 `pagoda-sample-spring-boot`

# 实验顺序

- 先对 `pagoda-samples/pagoda-embed-plugin` 工程打包，然后把包移动到 site 目录下，当做远程站点目录

- 启动 `pagoda-sample-spring-boot` 工程，访问 `localhost:7777/test` 测试地址

- 然后分别执行 `安装`、`激活`、`禁用`、`卸载` 操作，观看 console 日志打印