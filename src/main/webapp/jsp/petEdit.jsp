<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title>ペット情報編集</title></head>
<body>
<h2>ペット情報編集</h2>

<c:if test="${not empty errorMessage}">
    <p style="color:red;">${errorMessage}</p>
</c:if>

<form action="PetEdit" method="post">
    <input type="hidden" name="mode" value="confirm">
    <input type="hidden" name="id" value="${pet.id}">

    名前: <input type="text" name="name" value="${pet.name}"><br>
    動物名: <input type="text" name="species" value="${pet.species}"><br>
    犬種/猫種: <input type="text" name="breed" value="${pet.breed}"><br>
    生年月日: <input type="date" name="birthDate" value="${pet.birthDate}"><br>
    性別:
    <select name="gender">
        <option value="male" <c:if test="${pet.gender=='male'}">selected</c:if>>Boy</option>
        <option value="female" <c:if test="${pet.gender=='female'}">selected</c:if>>Girl</option>
    </select><br>
    体重(kg): <input type="text" name="weight" value="${pet.weightKg}"><br>

    <input type="submit" value="確認へ進む">
</form>

<a href="home">キャンセル</a>
</body>
</html>