package Servlet;

import database.sql;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class addTheme extends HttpServlet {
    @Override
    protected void doGet (HttpServletRequest request,HttpServletResponse response) throws IOException {
        response.sendRedirect( "newsPages/topic_add.jsp" );
    }

    @Override
    protected void doPost (HttpServletRequest request,HttpServletResponse response) throws IOException {
        sql sql = new sql();
        request.setCharacterEncoding( "UTF-8" );
        String newName = request.getParameter( "theme" );
        sql.addTheme( newName );
        sql.close();
        response.sendRedirect( "/setTheme" );
    }
}
