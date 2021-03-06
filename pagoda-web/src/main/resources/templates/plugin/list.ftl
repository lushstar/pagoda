<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>pagoda</title>
    <!-- Bootstrap -->
    <link href="/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/html5shiv@3.7.3/dist/html5shiv.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/respond.js@1.4.2/dest/respond.min.js"></script>
</head>
<body style="text-align: center">
<h1>
    <button type="button" class="btn btn-primary" onclick="window.location.href='/'">回退</button>
    <span>pagoda 插件管理</span>
    <button type="button" class="btn btn-primary" onclick="toAdd()">新增</button>
</h1>

<script src="/jquery-3.4.1/jquery-3.4.1.min.js"></script>
<script src="/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>

<table class="table table-hover table-bordered">
    <thead>
    <tr>
        <td>插件名称</td>
        <td>类名</td>
        <td>插件描述</td>
        <td>插件地址</td>
        <td>创建时间</td>
        <td>修改时间</td>
        <td>操作</td>
    </tr>
    </thead>
    <#list pluginVoList as pluginVo>
        <tr>
            <td>${pluginVo.name!""}</td>
            <td>${pluginVo.className!""}</td>
            <td>${pluginVo.description!""}</td>
            <td>${pluginVo.address!""}</td>
            <td>${pluginVo.createTime?datetime!}</td>
            <td>${pluginVo.updateTime?datetime!}</td>
            <td>
                <button type="button" class="btn btn-primary" onclick="toEdit(${pluginVo.id})">修改</button>
                <button type="button" class="btn btn-danger" onclick="del(${pluginVo.id})">删除</button>
            </td>
        </tr>
    </#list>
</table>
<script>
    function toAdd() {
        window.location.href = "/web/plugin/toAdd";
    }

    function toEdit(id) {
        window.location.href = "/web/plugin/toEdit/" + id;
    }

    function del(id) {
        window.location.href = "/web/plugin/del/" + id;
    }
</script>
</body>
</html>