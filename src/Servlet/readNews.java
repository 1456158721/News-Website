package Servlet;

import object.commentArea;
import database.sql;
import object.news;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class readNews extends HttpServlet {
    @Override
    protected void doGet (HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding( "UTF-8" );
        String strId = request.getParameter( "newsId" );
        String ip = request.getRemoteAddr();
        if (strId == null) {
            //获取过来的路径
            response.sendRedirect( "/" + request.getHeader( "Referer" ).split( "/" )[3] );
            return;
        }
        sql sql = new sql();
        news thisNews = sql.selectNews( Integer.parseInt( strId ) );
        ArrayList<commentArea> commentList = sql.selectCommentArea( Integer.parseInt( strId ) );
        sql.close();
        request.setAttribute( "ip",ip );
        request.setAttribute( "strId",strId );
        request.setAttribute( "thisNews",thisNews );
        request.setAttribute( "commentList",commentList );
        request.getRequestDispatcher( request.getContextPath() + "/news_read.jsp" ).forward( request,response );
    }
}
