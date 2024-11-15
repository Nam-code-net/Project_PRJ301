<%-- 
    Document   : search
    Created on : Oct 11, 2024, 2:09:06 PM
    Author     : VICTUS
--%>
    <%--<% @page import="namnmt.registration.RegistrationDTO"%>
<%@page import="java.util.List"%>
%>--%>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search</title>
    </head>
    <body>
        <form action="DispatchServlet" method="POST">
            <input type="submit" name="btAction" value="Logout" />
        </form>
        <font color ="red">
        Welcome, ${sessionScope.USER.fullName}
        </font>
        <h1>Search Page </h1>
        <form action="DispatchServlet" > <!-- i like it -->
            Search Value <input type="text" name="txtSearchValue"
                                value="${param.txtSearchValue}" /><br/>
            
            <input type="submit" value="Search" name="btAction" />
        </form>
    <c:set var="searchValue" value="${param.txtSearchValue}" /> 
    <c:if test="${not empty  searchValue}">
        <c:set var="result" value ="${requestScope.SEARCH_RESULT}" />
               <c:if test="${not empty result}">
                   <table border="1">
                       <thead>
                            <tr>
                                <th>No.</th>
                                <th>Username</th>
                                <th>Password</th>
                                <th>Full name</th>
                                <th>Role</th>
                                <th>Update</th>
                            </tr>
                       </thead>
                       <tbody>
                           <c:forEach var="dto" items="${result}" varStatus="counter">
                           <form action="DispatchServlet" method="POST">
                            <tr>
                                <td>
                                    
                                    ${counter.count}
                                .</td>
                                <td>
                                    ${dto.username}
                                    <input type="hidden" name="txtUsername"
                                           value="${dto.username}">
                                </td>
                                
                                <td>
                                    
                                    <input type="text" name="txtPassword"
                                           value="${dto.password}">
                                </td>
                                
                                <td>
                                    ${dto.fullName}
                                    
                                </td>
                                <td>
                                    ${dto.role}
                                    <input type="checkbox" name="chkAdmin" value="ON" />
                                    <c:if test="${dto.role}">
                                        checked="checked"
                                        
                                    </c:if>
                                </td>
                                <td>
                                <c:url var="deleteLink" value="DispatchServlet">
                                    <c:param name="btAction" value="delete" />
                                     <c:param name="pk" value="${dto.username}" />
                                      <c:param name="lastSearchValue" value="${txtSearchValue}" />
                                </c:url>
                           <a href="${deleteLink}">Delete</a>
                                </td>  
                                <td>
                                    <input type="submit" value="Update" name="btAction" />
                                    <input type="hidden" name="lastSearchValue" value="${searchValue}" />
                                    
                                </td>
                            </tr>
                           </form>
                            </c:forEach>
                       </tbody>
                   </table>

               </c:if>
               <c:if test="${empty result}">
                   <h2>
                       <font color="red">
                           No record is matched!!!!
                       </font>
                   </h2>
               </c:if>
               
    </c:if>
                                
                                
                                
                               
        <%--<%
            //1. get all cookies
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                //2. if cookies existed, get newest cookie
                Cookie newestCookie = cookies[cookies.length - 1];
                //3. get username to show
                String username = newestCookie.getName();
        %>
        <font color="red">
        Welcome, <%= username %>
        </font>
        <%
            } //cookies hâve existed
%>
      
        <h1>Search Page </h1>
        <form action="DispatchServlet" > <!-- i like it -->
            Search Value <input type="text" name="txtSearchValue"
                                value="<%= request.getParameter("txtSearchValue") %>" /><br/>
            
            <input type="submit" value="Search" name="btAction" />
        </form>
        <%
            String searchValue = request.getParameter("txtSearchValue");// phải kiểm tra searchValue khác null nếu không sẽ lỗi
            if (searchValue != null){
                // Kết quả search đang nằm ở atribute của request scope 
                // Kiểu dữ liệu List<RegistrationDTO> 
                // Phải ép kiểu tường minh
                
                List<RegistrationDTO> result = (List<RegistrationDTO>)
                        request.getAttribute("SEARCH_RESULT");
                
                if (result != null){ // has atlease result
                    %>
                    <table border="1">
                        <thead>
                            <%
                                
                                
                            %>
                            <tr>
                                <th>No.</th>
                                <th>Username</th>
                                <th>Password</th>
                                <th>Full name</th>
                                <th>Role</th>
                                <th>Delete</th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                            <%
                                int count =0;
                                for(RegistrationDTO dto : result){
                                    String urlRewriting = "DispatchServlet" //dư khoảng trắng: có dấu cộng hoặc %20
                                            + "?btAction=delete"
                                            + "&pk=" + dto.getUsername()
                                            + "&lastSearchValue=" + searchValue;
                                    %>
                                <td>
                                    <%= ++count %>
                                    .</td>
                                <td>
                                    <%= dto.getUsername() %>
                                </td>
                                <td>
                                    <%= dto.getPassword() %>
                                </td>
                                <td>
                                    <%= dto.getFullName() %>
                                </td>
                                <td>
                                    <%= dto.isRole() %>
                                </td>
                                <td>
                                    <a href="<%= urlRewriting%>">Delete</a>
                                    
                                </td>
                             </tr>
                            <%
                                }
                            %>
                        </tbody>
                    </table>

        <%
                }else{// no result
                    %>
                    <h2>
                        <font color="red">
                        No record is matched!!!
                        </font>
                        
                    </h2>
        <%
                }
                        
            }// first request, do nothing 
        %>
%>--%>  
    </body>
</html>
