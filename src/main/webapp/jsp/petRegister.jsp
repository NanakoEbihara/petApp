<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ペット登録</title>
<link rel="stylesheet"type="text/css" href="/petApp/css/register.css"/>
</head>
<body>
<h2>ペット登録</h2>

<% String errorMessage = (String) request.getAttribute("errorMessage"); %>
<% if(errorMessage != null) { %>
    <p style="color:red;"><%= errorMessage %></p>
<% } %>

<form action="${pageContext.request.contextPath}/petRegister" method="post">
    <input type="hidden" name="mode" value="confirm">
    <label>名前: <input type="text" name="name" required></label><br/>
    <label>種類: <input type="text" name="species"></label><br/>
    <label>犬種/猫種: <input type="text" name="breed"></label><br/>
    <label>生年月日: <input type="date" name="birthDate"></label><br/>
    <label>性別:
        <select name="gender">
            <option value="male">Boy</option>
            <option value="female">Girl</option>
        </select>
    </label><br/>
    <label>体重(kg): <input type="text" name="weight"></label><br/>
    <input type="submit" value="確認へ進む">
</form>
</body>
</html>