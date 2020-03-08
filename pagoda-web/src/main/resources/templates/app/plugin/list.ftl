<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>pagoda 应用插件管理</title>
    <!-- Bootstrap -->
    <link href="/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/html5shiv@3.7.3/dist/html5shiv.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/respond.js@1.4.2/dest/respond.min.js"></script>
</head>
<body style="text-align: center">
<h1>
    <button type="button" class="btn btn-primary" onclick="window.location.href='/'">回退</button>
    <span>pagoda 插件管理</span>
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
        <td>操作</td>
    </tr>
    </thead>
    <#list pluginVoList as pluginVo>
        <tr>
            <td>${pluginVo.name!""}</td>
            <td>${pluginVo.className!""}</td>
            <td>${pluginVo.description!""}</td>
            <td>${pluginVo.address!""}</td>
            <td>
                <#if pluginVo.install?string("1","0")=="0">
                    <button type="button" class="btn btn-success" onclick="install(${appId},${pluginVo.id})">安装</button>
                </#if>
                <#if pluginVo.active?string("1","0")=="0" && pluginVo.install?string("1","0")=="1">
                    <button type="button" class="btn btn-info" onclick="active(${appId},${pluginVo.id})">激活</button>
                </#if>
                <#if pluginVo.active?string("1","0")=="1">
                    <button type="button" class="btn btn-warning" onclick="disable(${appId},${pluginVo.id})">禁用</button>
                </#if>
                <#if pluginVo.install?string("1","0")=="1">
                    <button type="button" class="btn btn-danger" onclick="uninstall(${appId},${pluginVo.id})">卸载
                    </button>
                </#if>
            </td>
        </tr>
    </#list>
</table>
<script>
    function install(appId, pluginId) {
        window.location.href = "/web/app/plugin/install/" + appId + "/" + pluginId;
    }

    function active(appId, pluginId) {
        window.location.href = "/web/app/plugin/active/" + appId + "/" + pluginId;
    }

    function disable(appId, pluginId) {
        window.location.href = "/web/app/plugin/disable/" + appId + "/" + pluginId;
    }

    function uninstall(appId, pluginId) {
        window.location.href = "/web/app/plugin/uninstall/" + appId + "/" + pluginId;
    }
</script>
</body>
</html>