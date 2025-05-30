package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/bookList")
public class BookListServlet extends HttpServlet {
    private static final String query = "SELECT ID, BOOKNAME, BOOKEDITION, BOOKPRICE FROM BOOK";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter pw = resp.getWriter();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException cnf) {
            cnf.printStackTrace();
        }

        try (Connection con = DriverManager.getConnection("jdbc:mysql:///books", "root", "password");
             PreparedStatement ps = con.prepareStatement(query)) {

            ResultSet rs = ps.executeQuery();

            pw.println("<html><head>");
            pw.println("<style>");
            pw.println("@import url('https://fonts.googleapis.com/css2?family=Pirata+One&display=swap');");
            pw.println("body {");
            pw.println("  font-family: 'Pirata One', cursive !important;");
            pw.println("  background-image: url('https://images.pexels.com/photos/235985/pexels-photo-235985.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2');");
            pw.println("  background-size: cover;");
            pw.println("  color: #2b1d0e;");
            pw.println("  text-align: center;");
            pw.println("  padding-top: 40px;");
            pw.println("  margin: 0;");
            pw.println("}");
            pw.println("* { font-family: 'Pirata One', cursive !important; }");
            pw.println("h2 { font-size: 36px; color: #FFFFFF; text-shadow: 2px 2px 4px #000; margin-bottom: 30px; }");
            pw.println("table { margin: 0 auto; border-collapse: collapse; width: 85%; background: rgba(255, 248, 220, 0.9); box-shadow: 0 0 30px rgba(0,0,0,0.6); border: 4px solid #c17e1f; }");
            pw.println("th, td { border: 2px solid #7b3f00; padding: 14px; font-size: 18px; }");
            pw.println("th { background-color: #ffcc00; color: #000; font-size: 20px; }");
            pw.println("tr:nth-child(even) { background-color: #fff4e6; }");
            pw.println("tr:hover { background-color: #ffe082; transition: 0.3s; }");
            pw.println("a { text-decoration: none; color: #fefefe; font-weight: bold; font-size: 20px; background-color: #7b3f00; padding: 5px 12px; border-radius: 8px; }");
            pw.println("a:hover { color:#7b3f00; background-color: #fefefe; text-shadow: none; transition: 0.3s; }");
            pw.println(".home-link { display: inline-block; margin-top: 30px; font-weight: bold; color: #fefefe; font-size: 20px; background-color: #7b3f00; padding: 8px 20px; border-radius: 12px; }");
            pw.println(".home-link:hover { color:#7b3f00; background-color: #fefefe; }");
            pw.println("</style>");

            pw.println("</head><body>");

            pw.println("<h2>Book Collection</h2>");

            pw.println("<table>");
            pw.println("<tr>");
            pw.println("<th>Book Id</th>");
            pw.println("<th>Book Name</th>");
            pw.println("<th>Book Edition</th>");
            pw.println("<th>Book Price</th>");
            pw.println("<th>Edit</th>");
            pw.println("<th>Delete</th>");
            pw.println("</tr>");

            while (rs.next()) {
                pw.println("<tr>");
                pw.println("<td>" + rs.getInt(1) + "</td>");
                pw.println("<td>" + rs.getString(2) + "</td>");
                pw.println("<td>" + rs.getString(3) + "</td>");
                pw.println("<td>" + rs.getFloat(4) + "</td>");
                pw.println("<td><a href='editScreen?id=" + rs.getInt(1) + "'>✏️ Edit</a></td>");
                pw.println("<td><a href='deleteUrl?id=" + rs.getInt(1) + "'>🗑️ Delete</a></td>");
                pw.println("</tr>");
            }

            pw.println("</table>");
            pw.println("<a class='home-link' href='home.html'>🏠 Home</a>");

            pw.println("</body></html>");

        } catch (SQLException se) {
            se.printStackTrace();
            pw.println("<h2 style='color: #fefefe; text-align:center;'>" + se.getMessage() + "</h2>");
        } catch (Exception e) {
            e.printStackTrace();
            pw.println("<h2 style='color: #fefefe; text-align:center;'>" + e.getMessage() + "</h2>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
