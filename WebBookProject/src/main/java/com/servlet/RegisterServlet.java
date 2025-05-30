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

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private static final String query = "INSERT INTO BOOK(BOOKNAME,BOOKEDITION,BOOKPRICE) VALUES(?,?,?)";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter pw = resp.getWriter();
        resp.setContentType("text/html");

        String bookName = req.getParameter("bookName");
        String bookEdition = req.getParameter("bookEdition");
        float bookPrice = Float.parseFloat(req.getParameter("bookPrice"));

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException cnf) {
            cnf.printStackTrace();
        }

        pw.println("<!DOCTYPE html>");
        pw.println("<html>");
        pw.println("<head>");
        pw.println("<title>Book Registration</title>");
        // Google Fonts Pirata One for One Piece style font
        pw.println("<link href='https://fonts.googleapis.com/css2?family=Pirata+One&display=swap' rel='stylesheet'>");
        pw.println("<link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css'>");
        pw.println("<style>");
        pw.println("body {"
                + "background: url('https://images.pexels.com/photos/235985/pexels-photo-235985.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2') no-repeat center center fixed;"
                + "background-size: cover;"
                + "font-family: 'Pirata One', cursive;"
                + "color: #fff;"  // lighter white for better contrast
                + "margin: 0;"
                + "padding: 0;"
                + "display: flex;"
                + "justify-content: center;"
                + "align-items: center;"
                + "height: 100vh;"
                + "flex-direction: column;"
                + "}");
        pw.println(".container {"
                + "background-color: rgba(0, 0, 0, 0.7);"
                + "padding: 40px;"
                + "border-radius: 15px;"
                + "box-shadow: 0 0 20px rgba(255, 255, 255, 0.3);"
                + "text-align: center;"
                + "color: #fff;"  
                + "}");
        pw.println("h2 { margin-bottom: 20px; }");
        pw.println("a {"
                + "color: #f0f0f0;"
                + "text-decoration: none;"
                + "padding: 10px 20px;"
                + "margin: 10px;"
                + "border: 2px solid #f0f0f0;"
                + "border-radius: 8px;"
                + "transition: 0.3s;"
                + "display: inline-block;"
                + "}");
        pw.println("a:hover { background-color: #f0f0f0; color: #000; }");
        pw.println("</style>");

        pw.println("</head>");
        pw.println("<body>");
        pw.println("<div class='container'>");

        try (Connection con = DriverManager.getConnection("jdbc:mysql:///books", "root", "password");
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, bookName);
            ps.setString(2, bookEdition);
            ps.setFloat(3, bookPrice);

            int count = ps.executeUpdate();

            if (count == 1) {
                pw.println("<h2 style='color: white;'><i class='fas fa-check-circle'></i> Record registered successfully!</h2>");
            } else {
                pw.println("<h2 style='color: red;'><i class='fas fa-times-circle'></i> Record not registered. Something went wrong!</h2>");
            }

        } catch (SQLException se) {
            se.printStackTrace();
            pw.println("<h2 style='color: red;'><i class='fas fa-bug'></i> " + se.getMessage() + "</h2>");
        } catch (Exception e) {
            e.printStackTrace();
            pw.println("<h2 style='color: red;'><i class='fas fa-bug'></i> " + e.getMessage() + "</h2>");
        }

        pw.println("<br>");
        pw.println("<a href='home.html'><i class='fas fa-home'></i> Home</a>");
        pw.println("<a href='bookList'><i class='fas fa-book'></i> Book List</a>");
        pw.println("</div>");
        pw.println("</body>");
        pw.println("</html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
