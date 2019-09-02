<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ja">
    <head>
        <meta charset="UTF-8">
        <title>食事記録システム</title>
        <link rel="stylesheet" href="<c:url value='/css/reset.css' />">
        <link rel="stylesheet" href="<c:url value='/css/style.css' />">
    </head>
    <body>
        <div id="wrapper">
            <div id="header">
                <div id ="header_menu">
                <h1>食事記録システム</h1>
                </div>
                <c:if test="${sessionScope.login_user != null}">

                    <div id="user_id">
                        <c:out value="${sessionScope.login_user.user_id}" />&nbsp;さん&nbsp;&nbsp;&nbsp;
                                            <c:choose>
                    <c:when test="${index == true}">
                        <a href="<c:url value='/records/new' />">登録</a>&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='/index.html' />">一覧</a>&nbsp;
                    </c:otherwise>
                    </c:choose>
                        <a href="<c:url value='/logout' />">ログアウト</a>
                    </div>
                </c:if>
            </div>
            <div id="content">
                ${param.content}
            </div>
            <div id="footer">
                by Takeshi Chiba.
            </div>
        </div>
    </body>
</html>