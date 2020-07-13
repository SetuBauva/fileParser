<%@ page import="com.demo.model.UserData" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <title>User Data List</title>
</head>
<body>
<div class="nav" role="navigation">
    <ul>
        <li><g:link class="create" action="upload">Upload File</g:link></li>
    </ul>
</div>
<div id="list-userData" class="content scaffold-list" role="main">
    <h1>User Data List</h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <g:if test="${flash.error}"><div class="message" role="status">${flash.error}</div></g:if>
    <table>
        <thead>
        <tr>
            <g:sortableColumn property="userId" title="User Id" />
            <g:sortableColumn property="amountOfCoins" title="Amount Of Coins" />
            <g:sortableColumn property="userName" title="User Name" />
        </tr>
        </thead>
        <tbody>
        <g:each in="${userDataInstanceList}" status="i" var="userDataInstance">
            <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                <td>${fieldValue(bean: userDataInstance, field: "userId")}</td>
                <td>${fieldValue(bean: userDataInstance, field: "amountOfCoins")}</td>
                <td>${fieldValue(bean: userDataInstance, field: "userName")}</td>
            </tr>
        </g:each>
        </tbody>
    </table>
    <div class="pagination">
        <g:paginate total="${userDataInstanceTotal}" />
    </div>
</div>
</body>
</html>