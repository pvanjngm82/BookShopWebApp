package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/editurl")
public class EditFinalServlet extends HttpServlet {
    private static final String query = "UPDATE BOOK SET BOOKNAME=?, BOOKEDITION=?, BOOKPRICE=? WHERE ID=?";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter pw = resp.getWriter();

        int id = Integer.parseInt(req.getParameter("id"));
        String bookName = req.getParameter("bookName");
        String bookEdition = req.getParameter("bookEdition");
        float bookPrice = Float.parseFloat(req.getParameter("bookPrice"));

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException cnf) {
            cnf.printStackTrace();
        }

        try (Connection con = DriverManager.getConnection("jdbc:mysql:///books", "root", "password");
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, bookName);
            ps.setString(2, bookEdition);
            ps.setFloat(3, bookPrice);
            ps.setInt(4, id);

            int count = ps.executeUpdate();

            pw.println("<html><head>");
            pw.println("<link href='https://fonts.googleapis.com/css2?family=Pirata+One&display=swap' rel='stylesheet'>");
            pw.println("<style>");
            pw.println("body {");
            pw.println("  font-family: 'Pirata One', cursive;");
            pw.println("  background-image: url('https://images.pexels.com/photos/235985/pexels-photo-235985.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2');");
            pw.println("  background-size: cover;");
            pw.println("  color: #fff8dc;");
            pw.println("  text-align: center;");
            pw.println("  padding-top: 60px;");
            pw.println("  margin: 0;");
            pw.println("}");
            pw.println("h2 {");
            pw.println("  font-size: 48px;");
            pw.println("  text-shadow: 3px 3px 8px #000;");
            pw.println("  margin-bottom: 40px;");
            pw.println("}");
            pw.println(".success { color: #f0e68c; }");
            pw.println(".error { color: #ff6347; }");
            pw.println("a {");
            pw.println("  font-family: 'Pirata One', cursive;");
            pw.println("  display: inline-block;");
            pw.println("  margin: 0 20px;");
            pw.println("  font-weight: bold;");
            pw.println("  color: #ffe135;");
            pw.println("  font-size: 24px;");
            pw.println("  text-decoration: none;");
            pw.println("  text-shadow: 1px 1px 3px #000;");
            pw.println("  transition: color 0.3s ease;");
            pw.println("}");
            pw.println("a:hover {");
            pw.println("  color: #ffcc00;");
            pw.println("  text-shadow: 2px 2px 5px #000;");
            pw.println("}");
            pw.println("</style>");
            pw.println("</head><body>");

            if (count == 1) {
                pw.println("<h2 class='success'>✔️ Record Edited Successfully, Matey!</h2>");
            } else {
                pw.println("<h2 class='error'>❌ Record Not Edited, Arrr!</h2>");
            }

            pw.println("<a href='home.html'>🏠 Back to Home</a>");
            pw.println("<a href='bookList'>📚 See Yer Book List</a>");

            pw.println("</body></html>");

        } catch (SQLException se) {
            se.printStackTrace();
            pw.println("<h2 class='error'>❌ " + se.getMessage() + "</h2>");
        } catch (Exception e) {
            e.printStackTrace();
            pw.println("<h2 class='error'>❌ " + e.getMessage() + "</h2>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
