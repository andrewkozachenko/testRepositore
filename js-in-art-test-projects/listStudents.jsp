<%@ page 
[ language="java" ]
[ extends="package.class" ]
[ import="{package.class | package.*}, ..." ]
[ session="true | false" ]
[ buffer="none | 8kb | sizekb" ] 
[ autoFlush="true | false" ]
[ isThreadSafe="true | false" ] 
[ info="text" ]
[ errorPage="relativeURL" ] 
[ contentType="mimeType [ ;charset=characterSet ]" | "text/html ; charset=ISO-8859-1"] 
[pageEncoding=“charset” ]
[ isErrorPage="true | false" ] 
%>










<%@page contentType="text/html" pageEncoding="UTF-8"%><%@page import = 'students.model.Student' %>
<%@page import = 'java.util.List' %>
<%@page import = 'students.model.StudentDAO' %> <html>
<head>
<meta http-equiv="Content-Type"content="text/html;charset=UTF-8"><title>Preprod Students</title>
</head>
<body>
  <h1>Preprod Students</h1> <h1>List Of Students</h1> <ul>
  <% List<Student> students = StudentDAO.getStudents(); 
  for (Student s: students) {
  %>
    <li><%=s.getSurname()%> <%=s.getName()%> </li>
  <%}%>
  </ul>
</body>
</html>








<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<html><head>
<meta http-equiv="Content-Type"content="text/html;charset=UTF-8"> 
</head>
  <body>
    <h1>Студенти КПІ</h1>
    <h1>Список студентів</h1>
    <jsp:useBean id="model" class="students.model.StudentModel" />
    <ul>
      <c:forEach var="item" items="${model.students}">
        <li>${item.surname} ${item.name}</li>
      </c:forEach>
    </ul>
  </body>
</html>




<table>
  <c:forEach var="item" items="${model.students}" varStatus="studentsCount">
    <tr>
      <td> Numver: ${studentsCount.count} </td
    </tr>
    <tr>
      <td>${item.surname} ${item.name}</td>
    </tr>        
  </c:forEach>
</table>


  <c:set var="userLevel" scope="session" value="Cowboy"/>

  <c:set var="userLevel" scope="request">
    Sheriff, Bartnder, Cowgirl
  </c:set>
  
  <c:get var="userLevel" scope="page"/>
  


<% -- firstPage.jsp --%>

<%@ page contentType="text/html" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags/myCustomTags" %>
<html>
	<body bgcolor="white">
		Random Number : <my:generateRandomNum />
	</body>
</html>
  
<% -- generateRandomNum.tag --%>
<%@ tag body-content="empty" %>
<%
	out.println(java.lang.Math.random());
%>
  
  
<% -- secondPage.jsp--%>

<%@ page contentType="text/html" %>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags/myCustomTags" %>
<html>
	<body bgcolor="white">
		Sum of two numbers : <my:addNumbers firstNum="10" secondNum="20" />
	</body>
</html>
  
  
<% --addNumbers.tag--%>

<%@ tag body-content="empty" %>
<%@ attribute name="firstNum" required="true"%>
<%@ attribute name="secondNum" required="true"%>

${firstNum + secondNum}  
  
 
