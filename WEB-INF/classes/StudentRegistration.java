import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;
public class StudentRegistration extends HttpServlet {

    Connection con;
    PreparedStatement ps;
    public void init(ServletConfig sc) throws ServletException {
        // Initialize servlet parameters
        System.out.println("init() is running...");

        System.out.println("init() is running...");
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","System","122718");
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {

        String name,email,rollno,password;
        name = req.getParameter("name");
        email = req.getParameter("email");
        rollno = req.getParameter("rollno");
        password = req.getParameter("password");

        res.setContentType("text/html");
        PrintWriter pw = res.getWriter();


        try{
            
           ps = con.prepareStatement("INSERT INTO student_db VALUES (?,?,?,?)");
           ps.setString(1,name);
           ps.setString(2,email);
           ps.setString(3,rollno);
           ps.setString(4,password);
    
           int rows = ps.executeUpdate();

           if (rows > 0) {

            pw.println("<html><body>");
            pw.println("<h3>Registration Successful! Redirecting to homepage in 5 seconds...</h3>");
            pw.println("<script>setTimeout(function() { window.location.href = 'http://localhost:8090/StudentMgmt'; }, 5000);</script>");
            pw.println("</body></html>");
            return;
           }else{
            pw.println("Registration failed!! please try again...");
           }
          
         pw.close(); 
        }catch(Exception e){
            System.out.println(e.getMessage());
        }


    }

    public void destroy() {
        System.out.println("destroy() is running...");
    }
}
