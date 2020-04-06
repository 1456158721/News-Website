package Servlet;

import database.sql;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class addNewsSelect extends HttpServlet {
    @Override
    protected void doPost (HttpServletRequest request,HttpServletResponse response) throws IOException {
        sql sql = new sql();
        request.setCharacterEncoding( "UTF-8" );
        String strId = request.getParameter( "title" );
        String tips;
        if (sql.selectNews(strId) != null){
            tips = "标题已存在，请重新输入";
        } else {
            tips = "标题正确";
        }
        response.setContentType( "text/html;charset=utf-8" );
        response.getWriter().write( tips );
    }
}
