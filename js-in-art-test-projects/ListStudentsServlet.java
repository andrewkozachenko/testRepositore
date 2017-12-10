public class ListStudentsServlet extends HttpServlet {
@Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
  List<Student> students = StudentDAO.getStudents();
  response.setContentType("text/html;charset=UTF-8");PrintWriter out = response.getWriter();
  try { out.println("<html>"); out.println("<head>");
    out.println("<title>Preprod Students</title>"); 
    out.println("</head>"); 
    out.println("<body>"); 
    out.println("<h1>Preprod Students</h1>"); 
    out.println("<h3>List of students</h3>"); out.println("<ul>");
    for (Student s: students)
      out.println(" <li>" + s.getSurname() + " " + s.getName() + "</li>"); out.println("</ul>");
    out.println("</body>");
    out.println("</html>");
  }
  finally {
    out.close();
  }

}
