<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page  isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="Base" value="${pageContext.request.contextPath}"/>
<html>
<head>
    <title>客户管理</title>
</head>
<body>
    <h1>客户列表</h1>
    <table>
        <th>客户名称</th>
        <th>联系人</th>
        <th>电话</th>
        <th>邮箱</th>
        <th>操作</th>
        <c:forEach var="customer" items="${customerList}">
            <tr>
                <td>${customer.name}</td>
                <td>${customer.contact}</td>
                <td>${customer.telephone}</td>
                <td>${customer.email}</td>
                <td>
                    <a href="${Base}/customer_edit?id=${customer.id}">编辑</a>
                    <a href="${Base}/customer_delete?id=${customer.id}">删除</a>
                </td>
            </tr>
        </c:forEach>

    </table>
</body>
</html>
