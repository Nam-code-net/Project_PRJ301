<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Update</title>
</head>
<body>
    <h2>Update Account</h2>
    <form action="UpdateAccountServlet" method="POST">
        <!-- Username (non-editable) as a hidden field -->
        <input type="hidden" name="txtUsername" value="${param.txtUsername}"/>

        <label for="password">Password:</label>
        <input type="password" name="txtPassword" value="${param.txtPassword}"/>
        <c:if test="${not empty requestScope.UPDATE_ERROR.passwordLengthErr}">
            <span style="color: red;">${requestScope.UPDATE_ERROR.passwordLengthErr}</span>
        </c:if>
        <br/>

        <label for="role">Admin:</label>
        <input type="checkbox" name="chkAdmin" value="ON" <c:if test="${param.chkAdmin == 'ON'}">checked</c:if> />
        <br/>

        <input type="hidden" name="lastSearchValue" value="${param.lastSearchValue}"/>

        <input type="submit" value="Update" name="btAction"/>
        <input type="reset" value="Reset"/>
    </form>
    
   
</body>
</html>