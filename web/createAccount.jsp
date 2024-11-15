<%-- 
    Document   : createAccount
    Created on : Oct 29, 2024, 7:14:24 PM
    Author     : VICTUS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
     <body>
        <h1>Create Account </h1>
        <form action="DispatchServlet" method="POST">
            <c:set var="errors" value="${requestScope.CREATE_ERROR}" />
            Username* <input type="text" name="txtUsername" value="${param.txtUsername}"/> (6-20 character) <br/>
            <c:if test="${not empty errors.usernameLengthErr}">
                <font color="red">
                    ${errors.usernameLengthErr}
                    </font></br>
            </c:if>
            Password* <input type="password" name="txtPassword" value=""/> (6-30 character) <br/>
            <c:if test="${not empty errors.passwordLengthErr}">
                <font color="red">
                    ${errors.passwordLengthErr}
                    </font></br>
            </c:if>
            Confirm*  <input type="password" name="txtConfirm" value=""/><br/>
            <c:if test="${not empty errors.confirmNotMatched}">
                <font color="red">
                    ${errors.confirmNotMatched}
                    </font></br>
            </c:if>
            Full name*  <input type="text" name="txtFullname" value="${param.txtFullName}"/> (2-50 chars)<br/>
            <c:if test="${not empty errors.fullnameLengthErr}">
                <font color="red">
                    ${errors.fullnameLengthErr}
                    </font></br>
            </c:if>
            <input type="submit" value="Create New Account" name="btAction" />
            <input type="submit" value="Reset" />
        </form>
             
    </body>
</html>
