import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/ViewAllStudents")
public class ViewAllStudent extends HttpServlet {

    Connection con;
    Statement stmt;

   public void init(ServletConfig config){
    try {
        // loading a driver and establishing a connection
        Class.forName("oracle.jdbc.driver.OracleDriver");
        con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","System","122718");
   
    } catch (Exception e) {
        // TODO: handle exception
    }
   }

   public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException{
        List<Student> students = new ArrayList<>();

        
        students = getAllStudents();

        PrintWriter pw = res.getWriter();

        res.setContentType("text/html");
        pw.println("<html><head><title>View All Students</title>");
        pw.println("<link rel=\"stylesheet\" href=\"css/styles.css\">");
        pw.println("</head>");
        pw.println("<body>");
        pw.println("<div class='container'>");
        pw.println("<h1>Student Data</h1>");
        pw.println("<table>");
        pw.println("<thead>");
        pw.println("<tr><th>RollNo</th><th>Name</th><th>Email</th></tr>");
        pw.println("</thead>");
        
        for(Student st : students){
            pw.println("<tr>");
            pw.println("<td>"+st.getRollNo()+"</td>");
            pw.println("<td>"+st.getName()+"</td>");
            pw.println("<td>"+st.getEmail()+"</td>");
            pw.println("</tr>");
        }
        pw.println("</table>");
        pw.println("</div>");
        
        pw.println("<div class='footer'>");
        pw.println("<p>&copy; 2024 Student Management Application. All rights reserved.</p>");
        pw.println("</div>");
        pw.println("</body>");
        pw.println("</html>");
   }


   // this method will fetch all data from the DB and returns the students
public List<Student> getAllStudents() {

    List<Student> students = new ArrayList<>();

    try {
        String sql = "SELECT * FROM student_db ORDER BY rollno";

        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery(sql);

        String rollno,name,email;
        while (rs.next()) {
            rollno = rs.getString("rollno");
            name = rs.getString("name");
            email = rs.getString("email");
            students.add(new Student(rollno,name,email));
        }


     
       
        
    } catch (Exception e) {
        System.out.println(e.getMessage());
        // TODO: handle exception
    }

    return students;

}

}

// POJOClass (For fetching multiple data from database)
 class Student {

    String rollno,name,email;
    
    public Student(String rollno,String name, String email){
        this.rollno = rollno;
        this.name = name;
        this.email = email;
    }

    public void setRollno(String rollno){
        this.rollno = rollno;
    }
    public String getRollNo(){
        return rollno;
    }

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }

    public void setEmail(String email){
        this.email = email;
    }
    public String getEmail(){
        return email;
    }

    
}
