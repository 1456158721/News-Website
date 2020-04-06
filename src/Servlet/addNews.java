package Servlet;

import database.sql;
import object.news;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class addNews extends HttpServlet {
    @Override
    protected void doGet (HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding( "UTF-8" );
        String strId = request.getParameter( "id" );
        String imgPath = request.getParameter( "imgPath" );
        String submit = request.getParameter( "submit" );
        Integer getSubject = null;
        String getTitle = null;
        String getAuthor = null;
        String getAbstract = null;
        String getContent = null;
        sql sql = new sql();
        Map<Integer, String> themeList = sql.selectTheme();
        if (strId != null) {
            int getId = Integer.parseInt( strId );
            news thisNews = sql.selectNewsForDraft( getId );
            if (imgPath == null && thisNews != null) {
                imgPath = thisNews.getImage();
            }
            if (thisNews == null) {
                thisNews = sql.selectNews( getId );
                if (thisNews == null) {
                    response.sendRedirect( "../news.jsp" );
                    sql.close();
                    return;
                }
            }
            getSubject = thisNews.getSubjectId();
            getTitle = thisNews.getTitle();
            getAuthor = thisNews.getAuthor();
            getAbstract = thisNews.getThisAbstract();
            getContent = thisNews.getContent();
        } else {
            strId = String.valueOf( System.currentTimeMillis() );
        }
        sql.close();
        request.setAttribute( "strId",strId );
        request.setAttribute( "imgPath",imgPath );
        request.setAttribute( "getSubject",getSubject );
        request.setAttribute( "getTitle",getTitle );
        request.setAttribute( "getAuthor",getAuthor );
        request.setAttribute( "getAbstract",getAbstract );
        request.setAttribute( "getContent",getContent );
        request.setAttribute( "themeList",themeList );
        request.setAttribute( "submit",submit );
        request.getRequestDispatcher( "util/comments_control.jsp" ).forward( request,response );
    }

    @Override
    protected void doPost (HttpServletRequest request,HttpServletResponse response) throws IOException {
        //得到当前时间
        SimpleDateFormat getDate = new SimpleDateFormat( "yyyy-MM-dd" );
        String nowDate = getDate.format( Calendar.getInstance().getTime() );
        //得到上传文件的目录
        String upload = request.getServletContext().getRealPath( "/img/upload" );
        File thisPath = new File( upload );
        if (! thisPath.exists()) {
            if (thisPath.mkdirs()){
                System.out.println();
            }
        }
        FileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload fileupload = new ServletFileUpload( factory );
        List<FileItem> items = null;
        try {
            items = fileupload.parseRequest( request );
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        Map<String, String> submitSetNews = new LinkedHashMap<>();
        int id = 0;
        String imgPath = null;
        String submitPath = null;
        assert items != null;
        for (FileItem item : items) {
            if (item.isFormField()) {
                String fieldName = item.getFieldName();
                String fieldValue = item.getString( "UTF-8" );
                if (fieldName.equals( "id" )) {
                    id = Integer.parseInt( fieldValue );
                    continue;
                } else if (fieldName.equals( "submitPath" )) {
                    submitPath = fieldValue;
                    continue;
                }
                submitSetNews.put( fieldName,fieldValue );
            } else {
                //得到文件名
                String fileName = item.getName();
                //得到文件的大小
                long size = item.getSize();
                //截取后缀名
                String ext = fileName.substring( fileName.lastIndexOf( "." ) + 1 );
                if (! ext.equals( "jpg" ) && ! ext.equals( "jepg" ) && ! ext.equals( "gif" )) {
                    //response.sendRedirect( "comments_control.jsp?id=" + id + "&submit=1" );
                    response.sendRedirect( "/addUser?id=" + id + "&submit=1" );
                    return;
                } else if (size > 5242880) {
                    //response.sendRedirect( "comments_control.jsp?id=" + id + "&submit=2" );
                    response.sendRedirect( "/addUser?id=" + id + "&submit=1" );
                    return;
                }
                //临时的文件名
                String tempFileName = genRandomFileName( fileName );
                //获取文件数据
                InputStream input = item.getInputStream();
                //定义输出流
                OutputStream output = new FileOutputStream( new File( upload + "/" + tempFileName ) );
                byte[] buff = new byte[1024];
                int len;
                while (( len = input.read( buff ) ) != - 1) {
                    output.write( buff,0,len );
                }
                output.close();
                input.close();
                imgPath = "../img/upload/" + tempFileName;
            }
        }
        sql sql = new sql();
        try {
            assert submitPath != null;
            if (submitPath.equals( "draft" )) {
                int done = 0;
                if (imgPath == null) {
                    done = 3;
                }
                sql.addNewsDraft( id,Integer.parseInt( submitSetNews.get( "subject" ) ),
                        submitSetNews.get( "title" ),nowDate,submitSetNews.get( "author" ),
                        submitSetNews.get( "abstract" ),submitSetNews.get( "content" ),imgPath );
                response.sendRedirect( "/addUser?id=" + id + "&submit=" + done );
            } else if (submitPath.equals( "formal" )) {
                String getImgPathFormDraft = sql.selectNewsForDraft( id ).getImage();
                sql.addNews( id,Integer.parseInt( submitSetNews.get( "subject" ) ),
                        submitSetNews.get( "title" ),nowDate,submitSetNews.get( "author" ),
                        submitSetNews.get( "abstract" ),submitSetNews.get( "content" ),getImgPathFormDraft );
                sql.removeNewsForDraft( id );
                response.sendRedirect( "/readNews?newsId=" + id );
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException( "空指针异常" );
        } finally {
            sql.close();
        }
    }

    public static String genRandomFileName (String formerName) {
        formerName = sql.setStr( formerName );
        long t = System.currentTimeMillis();
        return formerName + "_" + t;
    }
}
