package Servlet;

import database.sql;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class delNews extends HttpServlet {
    @Override
    protected void doGet (HttpServletRequest request,HttpServletResponse response) throws IOException {
        sql sql = new sql();
        request.setCharacterEncoding( "UTF-8" );
        int id = Integer.parseInt( request.getParameter( "id" ) );
        sql.removeNews( id );
        sql.close();
        response.sendRedirect(  request.getContextPath() + "/" + request.getHeader( "Referer" ).split( "/" )[3] );
    }
}
