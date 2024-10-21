import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;

public class Login extends HttpServlet {
    Connection con;
    PreparedStatement ps;
    public void init(ServletConfig sc) throws ServletException {
        // Initialize servlet parameters
        System.out.println("init() is running...");
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","System","122718");
        } catch (Exception e) {
        
            System.out.println(e.getMessage());
        }
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException{
       String username = req.getParameter("username");
       String password = req.getParameter("password");
       PrintWriter pw = res.getWriter();

       try{
        ps = con.prepareStatement("SELECT * FROM student_db WHERE email = ?");
       ps.setString(1,username);

       ResultSet rs = ps.executeQuery();
       boolean isStudentExist = false;
       if (rs.next()) {
        isStudentExist = true;
        if (rs.getString("password").equals(password)) {
           // pw.println("Login Successfull!");
           // res.sendRedirect("index.html"); // it is used to redirect from one window to another

           res.setContentType("text/html");
           pw.println("<html><body>");
           pw.println("<h3>Login Successful! Redirecting to homepage in 5 seconds...</h3>");
           pw.println("<script>setTimeout(function() { window.location.href = 'http://localhost:8090/StudentMgmt'; }, 5000);</script>");
           pw.println("</body></html>");
           return;
        }else{
            pw.println("Wrong password entered!");
        }
    }

        if (!isStudentExist) {
            pw.println("No such student exists, please Register.");
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
