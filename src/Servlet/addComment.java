package Servlet;

import database.sql;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class addComment extends HttpServlet {
    @Override
    protected void doPost (HttpServletRequest request,HttpServletResponse response) throws IOException {
        sql sql = new sql();
        request.setCharacterEncoding( "UTF-8" );
        int newsId = Integer.parseInt( request.getParameter( "newsId" ) );
        String userName = request.getParameter( "userName" );
        String ip = request.getRemoteAddr();
        String comment = request.getParameter( "comment" );
        //获取当前日期
        SimpleDateFormat getDate = new SimpleDateFormat( "yyyy-MM-dd" );
        String date = getDate.format( Calendar.getInstance().getTime() );
        sql.addCommentArea( newsId,userName,ip,comment,date );
        response.sendRedirect( "../readNews.jsp?newsId="+newsId );
        sql.close();
        response.sendRedirect( "/" + request.getHeader( "Referer" ).split( "/" )[3] );
    }
}
