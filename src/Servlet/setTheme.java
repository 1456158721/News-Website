package Servlet;

import database.sql;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public class setTheme extends HttpServlet {
    @Override
    protected void doGet (HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        sql sql = new sql();
        Map<Integer, String> themeList = sql.selectTheme();
        sql.close();
        request.setAttribute( "themeList",themeList );
        request.getRequestDispatcher( "newsPages/themeList.jsp" ).forward( request,response );
    }

    @Override
    protected void doPost (HttpServletRequest request,HttpServletResponse response) throws IOException {
        request.setCharacterEncoding( "UTF-8" );
        int id = Integer.parseInt( request.getParameter( "themeId" ) );
        String newName = request.getParameter( "newThemeName" );
        sql sql = new sql();
        sql.setTheme( id,newName );
        sql.close();
        response.sendRedirect( "/setTheme" );
    }
}
