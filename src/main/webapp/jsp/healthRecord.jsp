<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
  <meta charset="UTF-8">
  <title>ペットの健康記録</title>
  <link rel="stylesheet" type="text/css" href="/petApp/css/healthrecord.css"/>
</head>
<body>

<h2>ペット: ${petName}</h2>

<!-- 新しい記録を追加 -->
<h3>新しい記録を追加</h3>
<form action="${pageContext.request.contextPath}/healthRecord" method="post" accept-charset="UTF-8">
  <input type="hidden" name="action" value="insert"/>
  <input type="hidden" name="petId" value="${petId}" />

  <table>
    <tr>
      <th>日付</th>
      <th>食事量</th>
      <th>元気レベル</th>
      <th>項目</th>
      <th>メモ</th>
    </tr>
    <tr>
      <td><input type="date" name="recordDate" value="${todayDate}" required /></td>
      <td>
        <select name="mealAmount" required>
          <option value="">--選択--</option>
          <option value="少ない">少ない</option>
          <option value="普通" selected>普通</option>
          <option value="多い">多い</option>
        </select>
      </td>
      <td>
        <select name="genkiLevel">
          <option value="5" selected>5</option>
          <option value="4">4</option>
          <option value="3">3</option>
          <option value="2">2</option>
          <option value="1">1</option>
        </select>
      </td>
      <td>
        <!-- 新規項目は5個だけ空欄で出す -->
        <c:forEach var="i" begin="1" end="5">
          <div>
            <input type="text" name="itemName_new_${i}" placeholder="項目名"/>
            <input type="text" name="itemValue_new_${i}" placeholder="値"/>
            <select name="itemUnit_new_${i}">
              <option value="">単位なし</option>
              <option value="ml">ml</option>
              <option value="g">g</option>
              <option value="個">個</option>
              <option value="回">回</option>
            </select>
          </div>
        </c:forEach>
      </td>
      <td>
        <input type="text" name="memo" placeholder="気になったことを記入"/>
      </td>
    </tr>
  </table>
  <br/>
  <input type="submit" value="追加"/>
</form>

<!-- 既存の記録一覧 -->
<h3>記録一覧</h3>
<form action="${pageContext.request.contextPath}/healthRecord" method="post">
  <input type="hidden" name="action" value="update"/>
  <input type="hidden" name="petId" value="${petId}" />

  <c:forEach var="record" items="${allRecords}" varStatus="status">
    <table>
      <c:if test="${status.first}">
        <tr>
          <th>日付</th>
          <th>食事量</th>
          <th>元気レベル</th>
          <th>項目</th>
          <th>メモ</th>
          <th>操作</th>
        </tr>
      </c:if>
      <tr>
        <td>${record.recordDate}</td>
        <td>
          <select name="mealAmount_${record.id}">
            <option value="少ない" ${record.mealAmount=='少ない'?'selected':''}>少ない</option>
            <option value="普通" ${record.mealAmount=='普通'?'selected':''}>普通</option>
            <option value="多い" ${record.mealAmount=='多い'?'selected':''}>多い</option>
          </select>
        </td>
        <td>
          <select name="genkiLevel_${record.id}">
            <c:forEach var="i" begin="1" end="5">
              <option value="${i}" <c:if test="${record.genkiLevel eq i}">selected</c:if>>${i}</option>
            </c:forEach>
          </select>
        </td>
        <td id="itemsContainer_${record.id}">
          <c:forEach var="item" items="${record.items}">
            <!-- 空の項目は表示しない -->
            <c:if test="${not empty item.name or not empty item.value}">
              <div>
                <input type="hidden" name="itemId_${record.id}_${item.id}" value="${item.id}" />
                <input type="text" name="itemName_${record.id}_${item.id}" value="${item.name}" />
                <input type="text" name="itemValue_${record.id}_${item.id}" value="${item.value}" />
                <select name="itemUnit_${record.id}_${item.id}">
                  <option value="" ${item.unit==null||item.unit==''?'selected':''}>単位なし</option>
                  <option value="ml" ${item.unit=='ml'?'selected':''}>ml</option>
                  <option value="g" ${item.unit=='g'?'selected':''}>g</option>
                  <option value="個" ${item.unit=='個'?'selected':''}>個</option>
                  <option value="回" ${item.unit=='回'?'selected':''}>回</option>
                </select>
                <button type="submit" name="deleteItemId" value="${item.id}">削除</button>
              </div>
            </c:if>
          </c:forEach>
        </td>
        <td>
          <input type="text" name="memo_${record.id}" value="${record.memo}" />
        </td>
        <td>
          <button type="submit" name="deleteRecordId" value="${record.id}">レコード削除</button>
        </td>
      </tr>
    </table>
  </c:forEach>
 
	<div>
		<a href="/petApp/home" class="home-link">ホーム画面に戻る</a>
		</div>
</form>

</body>
</html>
