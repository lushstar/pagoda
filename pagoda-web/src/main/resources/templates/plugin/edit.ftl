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
    <button type="button" class="btn btn-primary" onclick="history.go(-1)">回退</button>
    <span>pagoda 插件修改</span>
</h1>

<script src="/jquery-3.4.1/jquery-3.4.1.min.js"></script>
<script src="/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>

<form class="form-horizontal" action="/web/plugin/edit" enctype="multipart/form-data" method="post"
      style="width: 80%;text-align: center">
    <input type="hidden" name="id" value="${pluginVo.id}">
    <input type="hidden" name="del" value="${pluginVo.del?string("1","0")}">
    <input type="hidden" name="name" value="${pluginVo.name!""}">
    <input type="hidden" name="address" value="${pluginVo.address!""}">
    <div class="form-group">
        <label class="col-sm-2 control-label">name</label>
        <div class="col-sm-10">
            <input type="text" class="form-control" placeholder="请输入插件名称" name="name" value="${pluginVo.name!""}"
                   disabled>
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 control-label">description</label>
        <div class="col-sm-10">
            <input type="text" class="form-control" placeholder="请输入详细描述" name="description"
                   value="${pluginVo.description!""}">
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 control-label">className</label>
        <div class="col-sm-10">
            <input type="text" class="form-control" placeholder="请输入类名" name="className"
                   value="${pluginVo.className!""}">
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 control-label">address</label>
        <div class="col-sm-10">
            <input type="file" placeholder="请输入地址" name="jarFile">
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 control-label">createTime</label>
        <div class="col-sm-10">
            <input type="text" class="form-control" placeholder="创建时间" name="createTime"
                   value="${pluginVo.createTime?datetime!""}" disabled>
        </div>
    </div>
    <div class="form-group">
        <label class="col-sm-2 control-label">updateTime</label>
        <div class="col-sm-10">
            <input type="text" class="form-control" placeholder="修改时间" name="updateTime"
                   value="${pluginVo.updateTime?datetime!""}" disabled>
        </div>
    </div>
    <div class="form-group">
        <div class="col-sm-offset-2 col-sm-10">
            <button type="submit" class="btn btn-success">修改</button>
        </div>
    </div>
</form>

</body>
</html>