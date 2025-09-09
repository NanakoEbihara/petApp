<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ペット情報更新完了</title>
<link rel="stylesheet"type="text/css" href="/petApp/css/editstyle.css"/>
</head>
<body>
<h2>ペット情報の更新が完了しました</h2>

<table border="1">
    <tr><th>名前</th><td>${pet.name}</td></tr>
    <tr><th>動物名</th><td>${pet.species}</td></tr>
    <tr><th>犬種/猫種</th><td>${pet.breed}</td></tr>
    <tr><th>生年月日</th><td>${pet.birthDate}</td></tr>
    <tr><th>性別</th><td>${pet.gender}</td></tr>
    <tr><th>体重(kg)</th><td>${pet.weightKg}</td></tr>
</table>

<p><a href="${pageContext.request.contextPath}/PetEditSelect">他のペットを編集する</a></p>
<p><a href="${pageContext.request.contextPath}/home">ホームへ戻る</a></p>

</body>
</html>