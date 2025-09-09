<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>


<html>
<head>
	<meta charset="UTF-8">
    <title>ペットの健康記録</title>
    <link rel="stylesheet" type="text/css" href="/petApp/css/healthrecord.css"/>
    <script>
        // 新しい行を追加する関数
        function addNewItem(rowId) {
            const container = document.getElementById('itemsContainer_' + rowId);
            const index = container.children.length + 1;
            const html = `
                <input type="text" name="itemName_${rowId}_${index}" placeholder="項目名"/>
                <input type="text" name="itemValue_${rowId}_${index}" placeholder="値"/>
                <select name="itemUnit_${rowId}_${index}">
                    <option value="">単位なし</option>
                    <option value="ml">ml</option>
                    <option value="g">g</option>
                    <option value="個">個</option>
                    <option value="回">回</option>
                </select>
                <button type="button" onclick="this.parentNode.remove()">削除</button><br/>
            `;
            const div = document.createElement('div');
            div.innerHTML = html;
            container.appendChild(div);
        }
    </script>
</head>
<body>
<h2>ペット: ${petName}</h2>

<!-- 新規追加フォーム -->
<h3>新しい記録を追加</h3>
<form action="${pageContext.request.contextPath}/healthRecord" method="post" accept-charset="UTF-8">
    <input type="hidden" name="action" value="insert"/>
    <input type="hidden" name="petId" value="${petId}" />

    <table border="1" cellpadding="5" cellspacing="0">
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
                    <option value="1" >1</option>
                </select>
            </td>
            <td id="itemsContainer_new">
                <div>
                    <input type="text" name="itemName_new_1" placeholder="項目名"/>
                    <input type="text" name="itemValue_new_1" placeholder="値"/>
                    <select name="itemUnit_new_1">
                        <option value="">単位なし</option>
                        <option value="ml">ml</option>
                        <option value="g">g</option>
                        <option value="個">個</option>
                        <option value="回">回</option>
                    </select>
             <td>
                 <input typetype="text" name="memo" placeholder="気になったことを記入"/>
            </td>
                    <button type="button" onclick="this.parentNode.remove()">削除</button><br/>
                </div>
            </td>
        </tr>
    </table>
    <button type="button" onclick="addNewItem('new')">項目を追加</button>
    <br/><br/>
    <input type="submit" value="追加"/>
</form>

<!-- 既存のレコード一覧 -->
<h3>記録一覧</h3>
<form action="${pageContext.request.contextPath}/healthRecord" method="post" >
    <input type="hidden" name="action" value="update"/>
    <input type="hidden" name="petId" value="${petId}" />

    <c:forEach var="record" items="${allRecords}">
        <table border="1" cellpadding="5" cellspacing="0" style="margin-bottom:20px;">
            <tr>
                <th>日付</th>
                <th>食事量</th>
                <th>元気レベル</th>
                <th>項目</th>
                <th>メモ</th>
                <th>操作</th>
            </tr>
            <tr>
                <td>${record.recordDate}</td>
                <td>
                    <select name="mealAmount_${record.id}">
                        <option value="少ない" ${record.mealAmount == '少ない' ? 'selected' : ''}>少ない</option>
                        <option value="普通" ${record.mealAmount == '普通' ? 'selected' : ''}>普通</option>
                        <option value="多い" ${record.mealAmount == '多い' ? 'selected' : ''}>多い</option>
                    </select>
                </td>
                <td>
                    <select name="genkiLevel_${record.id}">
                        <c:forEach var="i" begin="1" end="5" step="1">
    						<option value="${i}" <c:if test="${record.genkiLevel eq i}">selected</c:if>>${i}</option>
						</c:forEach>
                    </select>
                </td>
                <td id="itemsContainer_${record.id}">
                    <c:forEach var="item" items="${record.items}">
                        <div>
                            <input type="hidden" name="itemId_${record.id}_${item.id}" value="${item.id}" />
                            <input type="text" name="itemName_${record.id}_${item.id}" value="${item.name}" />
                            <input type="text" name="itemValue_${record.id}_${item.id}" value="${item.value}" />
                            <select name="itemUnit_${record.id}_${item.id}">
                                <option value="" ${item.unit == null || item.unit == '' ? 'selected' : ''}>単位なし</option>
                                <option value="ml" ${item.unit == 'ml' ? 'selected' : ''}>ml</option>
                                <option value="g" ${item.unit == 'g' ? 'selected' : ''}>g</option>
                                <option value="個" ${item.unit == '個' ? 'selected' : ''}>個</option>
                            </select>
                            <button type="submit" name="deleteItemId" value="${item.id}">削除</button><br/>
                        </div>
                    </c:forEach>

                    <!-- 新しい追加項目 -->
                    <div>
                        <input type="text" name="itemName_${record.id}_new_1" placeholder="項目名"/>
                        <input type="text" name="itemValue_${record.id}_new_1" placeholder="値"/>
                        <select name="itemUnit_${record.id}_new_1">
                            <option value="">単位なし</option>
                            <option value="ml">ml</option>
                            <option value="g">g</option>
                            <option value="個">個</option>
                            <option value="回">回</option>
                        </select>
                        <button type="button" onclick="addNewItem(${record.id})">追加</button><br/>
                    </div>
                </td>
                <td>
                 <input type="text" name="memo_${record.id}" value="${record.memo}">
                 </td>
                <td>
                    <button type="submit" name="deleteRecordId" value="${record.id}">レコード削除</button>
                </td>
            </tr>
        </table>
    </c:forEach>
    <input type="submit" value="更新"/>
</form>
</body>
</html>
