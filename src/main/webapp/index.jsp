<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<h1><%= "Hello World!" %>
</h1>
<br/>
<a href="/product?action=LIST">Danh sách sp</a>
<form action="/upload" method="post" enctype="multipart/form-data">
    <input type="file" name="files" multiple/>
    <button type="submit">Upload Image</button>
</form>
</body>
</html>