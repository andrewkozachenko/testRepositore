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
