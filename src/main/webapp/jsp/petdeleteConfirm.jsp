<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>削除確認画面</title>
		<link rel="stylesheet" type="text/css" href="/petApp/css/stylesheet.css"/>
	</head>
	
	<body>
		<h1>ペット情報削除確認画面</h1>
		<p>下記の内容でよろしければ、削除ボタンを押してください。</p>
		
		<c:if test="${not empty deleteError}">	
			<div style="color:red;">
				<p>${deleteError}</p>
			</div>
		</c:if>
		<h2>ペット情報</h2>
		<form action="/petApp/petdelete" method="post">
			<table border="1">
				<tr>
					<th>ペットID</th>
					<td><input type="hidden" name="deleteId" value="${pet.id}">${pet.id}</td>
				</tr>
				<tr>
					<th>ペットのお名前</th>
					<td>${pet.name}</td>
				</tr>
				<tr>
					<th>犬種/猫種</th>
					<td>${pet.breed}</td>
				</tr>
					<tr><th>生年月日</th>
					<td>${pet.birthDate}</td>
				</tr>
    			<tr>
    				<th>性別</th>
    				<td>${pet.gender}</td>
    			</tr>
			</table>
			<input type="submit" value="削除する">
		</form>
	</body>
</html>
