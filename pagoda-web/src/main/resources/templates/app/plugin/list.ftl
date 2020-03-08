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
    <button type="button" class="btn btn-primary" onclick="window.location.href='/'">回退</button>
    <span>pagoda 插件管理</span>
    <button type="button" class="btn btn-primary" onclick="toAdd()">新增</button>
</h1>

<script src="/jquery-3.4.1/jquery-3.4.1.min.js"></script>
<script src="/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>

<form class="form-horizontal" action="/web/app/add" method="post" style="width: 80%;text-align: center">
    <input type="hidden" name="id" value="${appId}">
    <#--<div class="form-group">
        <div class="col-sm-offset-2 col-sm-10">
            <button type="submit" class="btn btn-success">添加</button>
        </div>
    </div>-->
</form>

<table class="table table-hover table-bordered">
    <thead>
    <tr>
        <td>插件名称</td>
        <td>类名</td>
        <td>插件描述</td>
        <td>插件地址</td>
        <td>插件状态</td>
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
            <td>${pluginVo.active?string("开启","关闭")!}</td>
            <td>${pluginVo.createTime?datetime!}</td>
            <td>${pluginVo.updateTime?datetime!}</td>
            <td>
                <button type="button" class="btn btn-primary" onclick="toEdit(${pluginVo.id})">修改</button>
                <button type="button" class="btn btn-success" onclick="install(${pluginVo.id})">安装</button>
                <button type="button" class="btn btn-info" onclick="active(${pluginVo.id})">激活</button>
                <button type="button" class="btn btn-warning" onclick="disable(${pluginVo.id})">禁用</button>
                <button type="button" class="btn btn-danger" onclick="uninstall(${pluginVo.id})">卸载</button>
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

    function install(id) {
        window.location.href = "/web/plugin/install/" + id;
    }

    function active(id) {
        window.location.href = "/web/plugin/active/" + id;
    }

    function disable(id) {
        window.location.href = "/web/plugin/disable/" + id;
    }

    function uninstall(id) {
        window.location.href = "/web/plugin/uninstall/" + id;
    }
</script>
</body>
</html>