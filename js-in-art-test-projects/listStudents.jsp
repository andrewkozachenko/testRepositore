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
[pageEncoding=“charset” ][ isErrorPage="true | false" ] 
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
