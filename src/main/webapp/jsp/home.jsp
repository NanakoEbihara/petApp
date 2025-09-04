<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ホーム画面</title>
<style>
body { font-family: Arial, sans-serif; padding: 20px; }
.header { display: flex; justify-content: space-between; align-items: center; }
.btn-container { display: flex; gap: 20px; flex-wrap: wrap; margin-top: 40px; }
.card-btn {
    display: flex; flex-direction: column; justify-content: center; align-items: center;
    padding: 30px; width: 200px; height: 100px;
    background-color: #f0f0f0; border-radius: 10px;
    text-decoration: none; color: #333; font-weight: bold; font-size: 16px;
    box-shadow: 0 2px 5px rgba(0,0,0,0.2); transition: 0.2s;
}
.card-btn:hover { background-color: #d0eaff; box-shadow: 0 4px 10px rgba(0,0,0,0.3); }
</style>
</head>
<body>
<div class="header">
    <h1>ホーム画面</h1>
    <div>
        <span>${user.name} 様ログイン中</span> |
        <a href="${pageContext.request.contextPath}/logout">ログアウト</a> |
        <a href="${pageContext.request.contextPath}/edit">ユーザー情報編集</a>
    </div>
</div>

<h2>ペット健康管理システム</h2>

<div class="btn-container">
    <a href="${pageContext.request.contextPath}/petRegister" class="card-btn">新しくペットを登録</a>
    <a href="${pageContext.request.contextPath}/PetEditSelect" class="card-btn">ペット情報編集</a>
    <a href="${pageContext.request.contextPath}/PetSelect" class="card-btn">健康記録追加・閲覧</a>
</div>
</body>
</html>
