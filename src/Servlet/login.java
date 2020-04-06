package Servlet;

import database.sql;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class login extends HttpServlet {
    @Override
    protected void doPost (HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        String lastUrl = "/" + request.getHeader( "Referer" ).split( "/" )[3];
        sql sql = new sql();
        request.setCharacterEncoding( "UTF-8" );
        String id = request.getParameter( "uName" );
        String pwd = request.getParameter( "uPwd" );
        if (id == null || pwd == null) {
            //重定向（不会传数据）
            response.sendRedirect(  request.getContextPath()+lastUrl );
            sql.close();
            return;
        }
        if (sql.selectLogin( id,pwd )) {
            if (sql.selectUser( id,pwd ).getPermission() > 80){
                request.getSession().setAttribute( "userId",id );
                Cookie name = new Cookie( "userId",id );
                response.addCookie( name );
                //过期时间
                request.getSession().setMaxInactiveInterval( 600 );
                response.sendRedirect( "../newsPages/admin.jsp" );
            } else {
                request.setAttribute( "Error","权限不足，无法登录" );
                request.getRequestDispatcher( lastUrl ).forward( request,response );
            }
        } else {
            //转发方式2（会传数据）
            request.setAttribute( "Error","用户名或密码不正确" );
            request.getRequestDispatcher( lastUrl ).forward( request,response );
        }
        sql.close();
    }

    @Override
    protected void doGet (HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException {
        this.doPost( req, resp );
    }
}
