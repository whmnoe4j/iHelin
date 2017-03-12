<html>
<head>
    <title>图片上传</title>
</head>
<body>
<form enctype="multipart/form-data" method="post" action="${request.contextPath}/upload">
    <input type="file" name="file" id="image">
    <input type="submit" value="上传">
</form>
<#if msg??>
<p>${msg!}</p>
</#if>
<#if src??>
<img src="${src!}"></img>
</#if>
</body>
</html>