<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:if test="${errors != null}">
    <div id="flush_error">
        入力内容にエラーがあります。<br />
        <c:forEach var="error" items="${errors}">
            ・<c:out value="${error}" /><br />
        </c:forEach>
    </div>
</c:if>
<label for ="date">日付</label><br />
<input type="date" name="date" value="<fmt:formatDate value='${record.date}' pattern='yyyy-MM-dd' />" />
<br /><br />

<label for="breakfast">朝食</label><br />
<textarea name="breakfast" rows="5" cols="20">${record.breakfast}</textarea>
<br /><br />

<label for="lunch">昼食</label><br />
<textarea name="lunch" rows="5" cols="20">${record.lunch}</textarea>
<br /><br />

<label for="dinner">夕食</label><br />
<textarea name="dinner" rows="5" cols="20">${record.dinner}</textarea>
<br /><br />

<input type="hidden" name="_token" value="${_token}" />
<button type="submit">投稿</button>