<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
    <%@ include file="/jsp/header.jsp" %>
    <body bgcolor="#FFFFFF" text="#000000">
        <p>
            <%@ page import="model.*" %>
            <%@ page import="java.util.*"%>
            <%@ page import="java.text.*"%>
        </p>
        <table width="38%" border="0" cellspacing="1" cellpadding="0" height="53" align="left">
            <tr> 
                <td bgcolor="#E2E2E2"> 
                    <div align="left"><font face="Arial, Helvetica, sans-serif"><font face="Times New Roman, Times, serif" size="3"><b><font face="Arial, Helvetica, sans-serif" size="2">Items 
                            in your Shopping Cart</font></b></font></font></div>
                </td>
            </tr>
            <c:choose>
                <c:when test="${not empty sessionScope.cart}">
                    <c:set var="totalCostOfOrder" value="0.00" />
                    <c:forEach var="entry" items="${sessionScope.cart}">
                        <c:set var="item" value="${entry.value}" />
                        <c:set var="totalCostOfOrder" value="${totalCostOfOrder + item.orderCost}" />
                        <tr> 
                            <td height="13" bgcolor="#E8FFE8">${item}</td>
                        </tr>
                    </c:forEach>
                    <tr> 
                        <td height="13" bgcolor="#CCCCCC"> 
                            <div align="right">
                                <font face="Times New Roman, Times, serif">
                                    <b>
                                        <font size="2" face="Arial, Helvetica, sans-serif">
                                            Order Total: <fmt:formatNumber value="${totalCostOfOrder}" type="number" pattern="0.00"/>
                                        </font>
                                    </b>
                                </font>
                            </div>
                        </td>
                    </tr>
                </c:when>
                <c:otherwise>
                    <tr> 
                        <td height="13">No Items in Cart</td>
                    </tr>
                </c:otherwise>
            </c:choose>
        </table>
        <p>&nbsp;</p><p>&nbsp;</p>
        <p>&nbsp;</p><hr>
        <h2 align="center">WELCOME TO THE ONLINE BOOKSHOP</h2>
        <form name="form1" method="post" action="./books">
            <input type="hidden" name="action" value="add_to_cart">
            <table width="93%" border="2" cellspacing="0" cellpadding="1" bordercolor="#FFFFFF">
                <tr bgcolor="#CCCCCC"> 
                    <td width="10%"><b>ISBN</b></td>
                    <td width="37%"><b>Title</b></td>
                    <td width="24%"><b>Author</b></td>
                    <td width="13%"><b>Price</b></td>
                    <td width="10%"><b>Quantity</b></td>
                    <td width="6%"> 
                        <div align="left"><b>Add</b></div>
                    </td>
                </tr>
                <c:forEach var="book" items="${sessionScope.Books}">
                    <tr bgcolor="#F4F4F4"> 
                        <td width="10%">${book.isbn}</td>
                        <td width="37%">${book.title}</td>
                        <td width="24%">${book.author}</td>
                        <td width="13%">${book.price}</td>
                        <td width="10%"> 
                            <select name="${book.isbn}" size="1">
                                <option value="1">1</option>
                                <option value="2">2</option>
                                <option value="3">3</option>
                            </select>
                        </td>
                        <td width="6%"> 
                            <div align="center"> 
                                <input type="checkbox" name="add" value="${book.isbn}">
                            </div>
                        </td>
                    </tr>
                </c:forEach>
              
                <tr> 
                    <td width="10%"> 
                        <input type="submit" name="Details" value="add_to_cart">
                    </td>
                    <td width="37%">&nbsp;</td>
                    <td width="24%">&nbsp;</td>
                    <td width="13%">&nbsp;</td>
                    <td width="10%">&nbsp;</td>
                    <td width="6%">&nbsp;</td>
                </tr>
            </table>
        </form>
        <p><a href="./books?action=view_cart">View Shopping Cart</a></p>
        <%@ include file="/jsp/footer.jsp" %>
    </body>
</html>
