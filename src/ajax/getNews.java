package ajax;

import com.alibaba.fastjson.JSONArray;
import database.sql;
import object.news;
import object.pages;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class getNews extends HttpServlet {
    @Override
    protected void doGet (HttpServletRequest request,HttpServletResponse response) throws IOException {
        //要显示的新闻主题
        int sub;
        sql sql = new sql();
        String themeId = request.getParameter( "themeId" );
        String getPages = request.getParameter( "pages" );
        //页数
        int pages = getPages == null ? 0 : Integer.parseInt( getPages );
        if (themeId == null){
            response.setContentType( "text/html;charset=utf-8" );
            response.getWriter().write( "error" );
            return;
        }
        sub = Integer.parseInt( themeId );
        int totalPage = sql.selectNewsLength( sub );
        pages = Math.min( pages,totalPage-1 );
        pages = Math.max( pages,0 );
        ArrayList<news> newsList = sql.selectNews( sub,pages * 34,34 );
        object.pages getP = new pages(totalPage,pages,newsList );
        response.setContentType( "text/html;charset=utf-8" );
        response.getWriter().write( JSONArray.toJSONString( getP ) );
    }
}
