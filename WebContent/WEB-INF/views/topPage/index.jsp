<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="../layout/app.jsp">
    <c:param name="content">
          <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
          </c:if>
        <h2><c:out value="${sessionScope.login_user.user_id}" />の食事記録</h2>
        <table id="record_list">
            <tbody>
                <tr>
                    <th class="record_date">日付</th>
                    <th class="record_breakfast">朝ごはん</th>
                    <th class="record_lunch">昼ごはん</th>
                    <th class="record_dinner">夜ごはん</th>
                    <th class="record_action">操作</th>
                </tr>
                <c:forEach var="record" items="${records}" varStatus="status">
                    <tr class="row${status.count % 2}">
                        <td class="record_date"><fmt:formatDate value='${record.date}' pattern='yyyy-MM-dd' /></td>
                        <td class="record_breakfast">${record.breakfast}</td>
                        <td class="record_lunch">${record.lunch}</td>
                        <td class="record_dinner">${record.dinner}</td>
                        <td class="record_action"><a href="<c:url value='/records/edit?id=${record.id}' />">編集する</a><br /><a href="<c:url value='/records/delete?id=${record.id}' />">削除する</a></td>
                    </tr>
               </c:forEach>
            </tbody>
        </table>

    </c:param>
</c:import>

        <%-- <div id="pagination">
        （全 ${reports_count} 件）<br />
            <c:forEach var="i" begin="1" end="${((reports_count - 1) / 15) + 1}" step="1">
                <c:choose>
                    <c:when test="${i == page}">
                        <c:out value="${i}" />&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='/?page=${i}' />"><c:out value="${i}" /></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>

        </div>
        <p><a href="<c:url value='/reports/new' />">新規日報の登録</a></p>
 --%>