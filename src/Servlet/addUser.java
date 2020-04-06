package Servlet;

import com.alibaba.fastjson.JSONArray;
import database.sql;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class addUser extends HttpServlet {
    @Override
    protected void doPost (HttpServletRequest request,HttpServletResponse response) throws IOException {
        request.setCharacterEncoding( "UTF-8" );
        String newName = request.getParameter( "newName" );
        String newPwd = request.getParameter( "newPwd" );
        String newPermission = request.getParameter( "newPermission" );
        sql sql = new sql();
        ArrayList<Object> result = new ArrayList<>();
        if (newPermission == null){
            result.add( sql.addUser( newName,newPwd ) );
        } else {
            result.add( sql.addUser( newName,newPwd,Integer.parseInt( newPermission ) ) );
        }
        result.add( sql.selectUser(newName,newPwd) );
        sql.close();
        String done = JSONArray.toJSONString( result );
        response.setContentType( "text/html;charset=utf-8" );
        response.getWriter().write( done );
    }
}
