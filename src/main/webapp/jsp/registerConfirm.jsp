<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>

<head>
	<meta charset="UTF-8">
	<title>会員登録確認</title>
	<link rel="stylesheet"type="text/css" href="/petApp/css/register.css"/>
</head>

<body>
	<h1>新規会員登録</h1>
	<h2>登録内容確認</h2>
		<p>下記の内容でよろしければ、登録ボタンを押してください</p>
	<form action="/petApp/registerConfirm" method="post">
		<table border="1">
			<tr>
				<th>ログイン画面</th>
				<td><input type ="hidden" name="loginId" value="${user.loginId}">${user.loginId}</td>
			</tr>
			<tr>
				<th>パスワード</th>
				<td><input type ="hidden" name="password" value="${user.password}">${user.password}</td>
			</tr>
			<tr>
				<th>お名前</th>
				<td><input type ="hidden" name="name" value="${user.name}">${user.name}</td>
			</tr>
		</table>
		<input type="submit" value="登録">
	</form>
</body>
</html>