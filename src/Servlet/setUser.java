package Servlet;

import com.alibaba.fastjson.JSONArray;
import database.sql;
import object.user;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class setUser extends HttpServlet {
    @Override
    protected void doGet (HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {
        sql sql = new sql();
        ArrayList<user> allUser = sql.selectAllUser();
        sql.close();
        request.setAttribute( "allUser",allUser );
        request.getRequestDispatcher( request.getContextPath() + "/newsPages/userList.jsp" ).forward( request,response );
    }

    @Override
    protected void doPost (HttpServletRequest request,HttpServletResponse response) throws IOException {
        request.setCharacterEncoding( "UTF-8" );
        int id = Integer.parseInt( request.getParameter( "id" ) );
        String setName = request.getParameter( "setName" );
        String setPwd = request.getParameter( "setPwd" );
        sql sql = new sql();
        ArrayList<Object> result = new ArrayList<>();
        result.add( sql.setUser( id,setName,setPwd ) );
        result.add( id );
        result.add( setName );
        result.add( setPwd );
        sql.close();
        String done = JSONArray.toJSONString( result );
        response.setContentType( "text/html;charset=utf-8" );
        response.getWriter().write( done );
    }
}
