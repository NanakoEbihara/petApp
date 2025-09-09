<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>ペット登録確認</title>
    <link rel="stylesheet"type="text/css" href="/petApp/css/register.css"/>
</head>
<body>
<h2>登録内容の確認</h2>
<p>名前: ${pet.name}</p>
<p>動物: ${pet.species}</p>
<p>種類: ${pet.breed}</p>
<p>性別: ${pet.gender}</p>
<p>生年月日: ${pet.birthDate}</p>
<p>体重: ${pet.weightKg}</p>

<form action="${pageContext.request.contextPath}/petRegister" method="post">
    <input type="hidden" name="mode" value="register">
    <input type="hidden" name="name" value="${pet.name}">
    <input type="hidden" name="species" value="${pet.species}">
    <input type="hidden" name="breed" value="${pet.breed}">
    <input type="hidden" name="gender" value="${pet.gender}">
    <input type="hidden" name="birthDate" value="${pet.birthDate}">
    <input type="hidden" name="weight" value="${pet.weightKg}">
    <input type="submit" value="登録">
</form>
<a href="${pageContext.request.contextPath}/home">キャンセルしてホームへ戻る</a>
</body>
</html>