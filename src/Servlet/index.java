package Servlet;

import database.sql;
import object.news;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class index extends HttpServlet {
    @Override
    protected void doPost (HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        //要显示的新闻主题
        //int sub = 0;
        //页数
        //int pages;
        /*sql sql = new sql();
        String themeId = request.getParameter( "themeId" );
        String getPages = request.getParameter( "pages" );
        Map<Integer, String> themeList = sql.selectTheme();
        pages = getPages == null ? 0 : Integer.parseInt( getPages );
        if (themeId != null) {
            sub = Integer.parseInt( themeId );
        } else {
            for (Integer i : themeList.keySet()) {
                sub = i;
                break;
            }
        }*/
        //总页数
        /*int totalPage;
        //总条数
        int totalCount = sql.selectNewsLength( sub );
        //每页条数
        int everyPage = 34;
        //获取总页数
        if (totalCount != 0 && totalCount % everyPage == 0) {
            totalPage = totalCount / everyPage;
        } else {
            totalPage = totalCount / everyPage + 1;
        }*/
        //控制页数的范围
        /*pages = Math.min( pages,totalPage-1 );
        pages = Math.max( pages,0 );
        ArrayList<news> newsList = sql.selectNews( sub,pages * 34,34 );
        ArrayList<news> domesticNews = sql.selectNews( 21,0,3 );
        ArrayList<news> internationalNews = sql.selectNews( 20,0,3 );
        ArrayList<news> entertainmentNews = sql.selectNews( 38,0,3 );
        sql.close();
        request.setAttribute( "sub",sub );
        request.setAttribute( "pages",pages );
        request.setAttribute( "totalPage",totalPage );
        request.setAttribute( "newsList",newsList );
        request.setAttribute( "themeList",themeList );
        request.setAttribute( "domesticNews",domesticNews );
        request.setAttribute( "internationalNews",internationalNews );
        request.setAttribute( "entertainmentNews",entertainmentNews );*/
        request.getRequestDispatcher( "news.jsp" ).forward( request,response );
    }

    @Override
    protected void doGet (HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {
        this.doPost( request,response );
    }
}
