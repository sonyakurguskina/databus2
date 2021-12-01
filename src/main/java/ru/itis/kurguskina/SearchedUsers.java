package ru.itis.kurguskina;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//, urlPatterns = "/searchedUsers"

@WebServlet(name="SearchedUsers")
public class SearchedUsers extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
            doPost(request,response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String json = new J().getJson();

        request.setAttribute("name", json);
        request.getRequestDispatcher("searchedUsers.jsp").forward(request, response);

    }
}