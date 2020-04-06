package Servlet;

import database.sql;
import object.news;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class setNews extends HttpServlet {
    @Override
    protected void doGet (HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        database.sql sql = new sql();
        String getPages = request.getParameter( "pages" );
        //页数
        int pages = getPages == null ? 0 : Integer.parseInt( getPages );
        //总页数
        int totalPage;
        //总条数
        int totalCount = sql.selectNewsLength();
        //每页条数
        int everyPage = 34;
        //获取总页数
        if (totalCount != 0 && totalCount % everyPage == 0) {
            totalPage = totalCount / everyPage;
        } else {
            totalPage = totalCount / everyPage + 1;
        }
        pages = Math.min( pages,totalPage-1 );
        pages = Math.max( pages,0 );
        ArrayList<news> newsList = sql.selectNews( pages * 34,34 );
        sql.close();
        request.setAttribute( "pages",pages );
        request.setAttribute( "totalPage",totalPage );
        request.setAttribute( "newsList",newsList );
        request.getRequestDispatcher( "util/news_control.jsp" ).forward( request,response );
    }
}
