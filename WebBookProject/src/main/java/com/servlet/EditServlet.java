package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/editScreen")
public class EditServlet extends HttpServlet {
    private static final String query = "SELECT BOOKNAME, BOOKEDITION, BOOKPRICE FROM BOOK WHERE id=?";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter pw = resp.getWriter();
        resp.setContentType("text/html");

        int id = Integer.parseInt(req.getParameter("id"));

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException cnf) {
            cnf.printStackTrace();
        }

        try (Connection con = DriverManager.getConnection("jdbc:mysql:///books", "root", "password");
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                pw.println("<html><head>");
                pw.println("<link href='https://fonts.googleapis.com/css2?family=Pirata+One&display=swap' rel='stylesheet'>");
                pw.println("<style>");
                pw.println("body, h1, label, input, a { font-family: 'Pirata One', cursive; }");
                pw.println("body { background-image: url('https://images.pexels.com/photos/235985/pexels-photo-235985.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2'); background-size: cover; color: #2b1d0e; margin: 0; padding: 0; }");
                pw.println(".form-container { background: rgba(255, 248, 220, 0.95); max-width: 600px; margin: 80px auto; padding: 40px; border: 5px double #8B4513; border-radius: 20px; box-shadow: 0 0 30px rgba(0,0,0,0.6); }");
                pw.println("h1.title { font-size: 60px; text-align: center; margin-bottom: 30px; color: #fff; text-shadow: 3px 3px 8px #000; }");
                pw.println("label { font-size: 22px; display: block; margin-bottom: 10px; color: #000; }");
                pw.println("input[type='text'] { width: 100%; padding: 12px; font-size: 18px; margin-bottom: 20px; border: 2px solid #7b3f00; border-radius: 10px; background-color: #fffaf0; color: #000; }");
                pw.println("input[type='submit'], input[type='reset'] { padding: 12px 30px; font-size: 18px; background-color: #ffcc00; border: none; border-radius: 10px; cursor: pointer; margin: 10px 5px; font-weight: bold; color: #000; }");
                pw.println("input[type='submit']:hover, input[type='reset']:hover { background-color: #ffa500; transform: scale(1.05); }");
                pw.println(".buttons { text-align: center; }");
                pw.println("a.home-link { display: block; text-align: center; font-size: 22px; margin-top: 30px; color: #000; text-decoration: none; font-weight: bold; }");
                pw.println("a.home-link:hover { color: #ff6f00; }");
                pw.println("</style>");
                pw.println("</head><body>");

                pw.println("<h1 class='title'>☠️ Edit The Book!</h1>");
                pw.println("<div class='form-container'>");
                pw.println("<form action='editurl?id=" + id + "' method='post'>");

                pw.println("<label for='bookName'>📘 Book Name</label>");
                pw.println("<input type='text' id='bookName' name='bookName' value='" + rs.getString(1) + "' required>");

                pw.println("<label for='bookEdition'>📗 Book Edition</label>");
                pw.println("<input type='text' id='bookEdition' name='bookEdition' value='" + rs.getString(2) + "' required>");

                pw.println("<label for='bookPrice'>💰 Book Price</label>");
                pw.println("<input type='text' id='bookPrice' name='bookPrice' value='" + rs.getFloat(3) + "' required>");

                pw.println("<div class='buttons'>");
                pw.println("<input type='submit' value='Update'>");
                pw.println("<input type='reset' value='Cancel'>");
                pw.println("</div>");

                pw.println("</form>");
                pw.println("<a class='home-link' href='home.html'>Return to Home</a>");
                pw.println("</div></body></html>");
            } else {
                pw.println("<h1>☠️ No Record Found, Landlubber!</h1>");
            }

        } catch (SQLException se) {
            se.printStackTrace();
            pw.println("<h1 style='color:red'>" + se.getMessage() + "</h1>");
        } catch (Exception e) {
            e.printStackTrace();
            pw.println("<h1 style='color:red'>" + e.getMessage() + "</h1>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
