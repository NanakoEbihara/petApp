<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ペット情報編集確認</title>
</head>
<body>
<h2>ペット情報編集確認</h2>

<table border="1">
    <tr><th>名前</th><td>${pet.name}</td></tr>
    <tr><th>動物名</th><td>${pet.species}</td></tr>
    <tr><th>犬種/猫種</th><td>${pet.breed}</td></tr>
    <tr><th>生年月日</th><td>${pet.birthDate}</td></tr>
    <tr><th>性別</th><td>${pet.gender}</td></tr>
    <tr><th>体重(kg)</th><td>${pet.weightKg}</td></tr>
</table>

<!-- 更新処理に進む -->
<form action="${pageContext.request.contextPath}/PetEdit" method="post">
    <input type="hidden" name="mode" value="update">
    <input type="hidden" name="id" value="${pet.id}">
    <input type="hidden" name="name" value="${pet.name}">
    <input type="hidden" name="species" value="${pet.species}">
    <input type="hidden" name="breed" value="${pet.breed}">
    <input type="hidden" name="birthDate" value="${pet.birthDate}">
    <input type="hidden" name="gender" value="${pet.gender}">
    <input type="hidden" name="weight" value="${pet.weightKg}">
    <input type="submit" value="更新する">
</form>

<!-- 修正したい場合 -->
<p><a href="${pageContext.request.contextPath}/PetEdit?id=${pet.id}">← 修正する</a></p>
<p><a href="${pageContext.request.contextPath}/home">ホームへ戻る</a></p>

</body>
</html>