<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ペット編集選択</title>
<link rel="stylesheet"type="text/css" href="/petApp/css/petselect.css"/>
</head>
<body>
<h2>編集するペットを選択してください</h2>

<c:if test="${empty petList}">
    <p>登録されているペットがいません。</p>
</c:if>

<ul>
<c:forEach var="pet" items="${petList}">
    <li>
        ${pet.name} (${pet.species})
        <a href="${pageContext.request.contextPath}/PetEdit?id=${pet.id}">編集</a>
    </li>
</c:forEach>
</ul>

<p><a href="${pageContext.request.contextPath}/home">← ホームに戻る</a></p>
</body>
</html>