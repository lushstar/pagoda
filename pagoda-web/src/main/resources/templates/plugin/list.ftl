<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>pagoda 插件管理</title>
    <!-- Bootstrap -->
    <link href="/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/html5shiv@3.7.3/dist/html5shiv.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/respond.js@1.4.2/dest/respond.min.js"></script>
</head>
<body style="text-align: center">
<h1>
    <button type="button" class="btn btn-primary" onclick="history.go(-1)">回退</button>
    pagoda 插件管理
    <button type="button" class="btn btn-primary" onclick="toAdd()">新增</button>
</h1>

<script src="/jquery-3.4.1/jquery-3.4.1.min.js"></script>
<script src="/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>

<table class="table table-hover table-bordered">
    <thead>
    <tr>
        <td>插件名称</td>
        <td>插件描述</td>
        <td>插件状态</td>
        <td>创建时间</td>
        <td>修改时间</td>
        <td>操作</td>
    </tr>
    </thead>
    <tr>
        <#list pluginVoList as pluginVo>
            <td>${pluginVo.name}</td>
            <td>${pluginVo.description}</td>
            <td>${pluginVo.status?string("开启","关闭")}</td>
            <td>${pluginVo.createTime?string("yyyy-MM-dd HH:mm:ss")}</td>
            <td>${pluginVo.updateTime?string("yyyy-MM-dd HH:mm:ss")}</td>
            <td>
                <button type="button" class="btn btn-success">安装</button>
                <button type="button" class="btn btn-info">激活</button>
                <button type="button" class="btn btn-warning">禁用</button>
                <button type="button" class="btn btn-danger">卸载</button>
            </td>
        </#list>
    </tr>
</table>
<script>
    function toAdd() {
        window.location.href = "/web/plugin/toAdd";
    }
</script>
</body>
</html>