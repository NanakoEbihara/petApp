<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>ペットの健康記録</title>
</head>
<body>
<h2>ペット: ${petName}</h2>

<!-- 新規追加フォーム -->
<h3>新しい記録を追加</h3>
<form action="${pageContext.request.contextPath}/healthRecord" method="post">
    <input type="hidden" name="action" value="insert"/>
    <input type="hidden" name="petId" value="${petId}" />

    <table border="1" cellpadding="5" cellspacing="0">
        <tr>
            <th>日付</th>
            <th>食事量</th>
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
            <!-- メモ -->
                <td>
                    <input type="hidden" name="memoId_${record.id}" value="${record.memoItem.id}" />
                    <input type="text" name="memo_${record.id}" value="${record.memoItem.value}" />
                    <button type="submit" name="deleteItemId" value="${record.memoItem.id}">削除</button>
                </td>

                <!-- 追加項目 -->
                <td>
                    <c:forEach var="item" items="${record.items}">
                        <input type="hidden" name="itemId_${record.id}_${item.id}" value="${item.id}" />
                        <input type="text" name="item_${record.id}_${item.id}" value="${item.value}" />
                        <button type="submit" name="deleteItemId" value="${item.id}">削除</button><br/>
                    </c:forEach>
                     <!-- 新しい追加項目を追加する欄 -->
                    <input type="text" name="item_new_${record.id}" placeholder="追加項目" />
                </td>
        	</tr>
      </c:forEach>
    </table>
    <br/>
    <input type="submit" value="追加"/>
</form>

<!-- 既存のレコード一覧 -->
<h3>記録一覧</h3>
<form action="${pageContext.request.contextPath}/healthRecord" method="post">
    <input type="hidden" name="action" value="update"/>
    <input type="hidden" name="petId" value="${petId}" />

    <table border="1" cellpadding="5" cellspacing="0">
        <tr>
            <th>日付</th>
            <th>食事量</th>
        </tr>
        <c:forEach var="record" items="${allRecords}">
            <tr>
                <td>${record.recordDate}</td>
                <td>
                    <input type="hidden" name="recordId" value="${record.id}" />
                    <select name="mealAmount_${record.id}">
                        <option value="少ない" ${record.mealAmount == '少ない' ? 'selected' : ''}>少ない</option>
                        <option value="普通" ${record.mealAmount == '普通' ? 'selected' : ''}>普通</option>
                        <option value="多い" ${record.mealAmount == '多い' ? 'selected' : ''}>多い</option>
                    </select>
                </td>
            </tr>
        </c:forEach>
    </table>
    <br/>
    <input type="submit" value="更新"/>
</form>
</body>
</html>
