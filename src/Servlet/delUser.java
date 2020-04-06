package Servlet;

import database.sql;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class delUser extends HttpServlet {
    @Override
    protected void doPost (HttpServletRequest request,HttpServletResponse response) throws IOException {
        request.setCharacterEncoding( "UTF-8" );
        int id = Integer.parseInt( request.getParameter( "id" ) );
        sql sql = new sql();
        boolean flag =  sql.delUser( id );
        sql.close();
        response.setContentType( "text/html;charset=utf-8" );
        response.getWriter().write( String.valueOf( flag ) );
    }
}
