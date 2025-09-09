<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ペット登録完了</title>
<link rel="stylesheet"type="text/css" href="/petApp/css/stylesheet.css"/>
</head>
<body>
<h2>◆◇ペット登録完了◇◆</h2>
<p>ペット「${pet.name}」の登録が完了しました。</p>

<a href="${pageContext.request.contextPath}/home">ホームへ戻る</a>
</body>
</html>