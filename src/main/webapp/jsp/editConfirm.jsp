<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>変更確認画面</title>
		<link rel="stylesheet"type="text/css" href="/petApp/css/stylesheet.css"/>
	</head>
	
	<body>
		<h1>メンバー情報確認画面</h1>
		<p style="text-align: center">${user.name} 様ログイン中</p> 
		<p style="text-align: center"><a href="/miniApp/logout">ログアウト</a></p>
		<!-- ログアウトリンクを右寄せ表示。クリックすると /logout に飛ぶ。-->
		<h2>変更内容確認</h2>
		<p>下記の内容でよろしければ、変更ボタンを押してください。</p>
		<form action ="/petApp/editConfirm" method="post">
		<!--フォームの開始。データは POST メソッドで /editConfirm に送信 -->
		<table border="1">
			<tr>
			<th>お名前</th>
			<td><input type="hidden" name="editName" value="${editName}">${editName}</td>
			</tr>	
			<!-- type="text"にするとユーザーが変更できてしまうので確認画面として使いたい場合には不適切 -->
			<!-- ${editName}（単なる表示）で、変更できず送信もされない-->
			<tr>
			<th>パスワード</th>
			<td><input type="hidden" name="editPassword" value="${editPassword}">${editPassword}</td>
			</tr>
			<input type="hidden" name="loginId" value="${user.loginId}" />
		</table>
		<input type="submit" value="変更する">
		<!-- 「変更する」ボタンを表示。 -->
		<!-- これがフォームの中にあるのが大事で、submit ボタンがないとユーザーが送信操作できない＝ブラウザは フォームを送信しない-->
		<!-- ボタンがないとブラウザやフォーム構造によって動作が異なるので、動作が不安定・予測できない -->
	</form>
	<p><a href="/petsApp/jsp/login.jsp">トップ画面に戻る</a></p>
	</body>
	
</html>