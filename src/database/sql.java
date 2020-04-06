package database;

import object.commentArea;
import object.news;
import object.user;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class sql {
    Connection conn;

    //加载连接数据库的驱动
    static {
        try {
            Class.forName( "com.mysql.jdbc.Driver" );
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new Error();
        }
    }

    public sql () {
        try {
            conn = DriverManager.getConnection( "jdbc:mysql://localhost:3306/news",
                    "root","20000321" );
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException( "数据库连接失败" );
        }
    }

    //处理输入字符串
    public static String setStr (String str) {
        if (str == null) {
            return null;
        } else if (! str.contains( "<" ) && ! str.contains( ">" ) && ! str.contains( "\"" )) {
            return str;
        }
        String[] invalidSymbol = new String[]{"<",">","\"","/","«","'","u","p","s"};
        String[] setInvalidSymbol = new String[]{"&lt;","&gt;","&quot;","&frasl;","《","&acute;","&upsilon;","&rho;","&int;"};
        String[] thisStr = str.split( "" );
        StringBuilder re = new StringBuilder();
        for (String c : thisStr) {
            for (int j = 0;j < invalidSymbol.length;j++) {
                if (c.equals( invalidSymbol[j] )) {
                    c = setInvalidSymbol[j];
                }
            }
            re.append( c );
        }
        return re.toString();
    }

    private String addQuotationMarks (String str) {
        if (str == null) {
            return null;
        }
        return "\"" + str + "\"";
    }

    //添加用户（不带权限）
    public boolean addUser (String userName,String password) {
        if (userName == null || password == null) {
            return false;
        }
        String addUser = "insert into t_user (userName,password) value(?,?)";
        try (PreparedStatement st = conn.prepareStatement( addUser )) {
            st.setString( 1,setStr( userName ) );
            st.setString( 2,setStr( password ) );
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException( "数据库操作异常" );
        }
    }

    //添加用户（带权限）
    public boolean addUser(String userName,String password,int permission){
        String addUser = "insert into t_user (userName,password,permission) value(?,?,?)";
        try (PreparedStatement st = conn.prepareStatement( addUser )) {
            st.setString( 1,userName );
            st.setString( 2,password );
            st.setInt( 3,permission );
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException( "数据库操作异常" );
        }
    }

    //添加主题
    public void addTheme (String name) {
        if (name == null) {
            return;
        }
        String addTheme = "insert into t_theme (name) value(?)";
        try (PreparedStatement st = conn.prepareStatement( addTheme )) {
            st.setString( 1,setStr( name ) );
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException( "数据库操作异常" );
        }
    }

    //添加新闻
    public void addNews (int id,int subject,String title,String nowDate,String author,
                         String newAbstract,String content,String imgPath) {
        if (title == null || nowDate == null || author == null || newAbstract == null || content == null) {
            return;
        }
        String addNews;
        if (selectNews(id)!=null){
            addNews = "update t_news set subjectId=" + subject + ",title=" + addQuotationMarks( setStr( title ) ) +
                    ",date=" + addQuotationMarks( setStr( nowDate ) ) + ",author=" + addQuotationMarks( setStr( author ) ) +
                    ",abstract=" + addQuotationMarks( setStr( newAbstract ) ) + ",content=" + addQuotationMarks( setStr( content ) ) +
                    ",image=" + addQuotationMarks( imgPath ) + " WHERE id=" + id;
        } else {
            addNews = "insert into t_news (id,subjectId,title,date,author,abstract,content,image) value(" +
                    id + "," + subject + "," + addQuotationMarks( setStr( title ) ) +
                    "," + addQuotationMarks( setStr( nowDate ) ) + "," + addQuotationMarks( setStr( author ) ) +
                    "," + addQuotationMarks( setStr( newAbstract ) ) + "," + addQuotationMarks( setStr( content ) ) +
                    "," + addQuotationMarks( imgPath ) + ")";
        }
        try (PreparedStatement st = conn.prepareStatement( addNews )) {
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException( "数据库操作异常" );
        }
    }

    //添加/修改新闻到草稿箱
    public void addNewsDraft (int id,int subject,String title,String nowDate,String author,
                              String newAbstract,String content,String imgSrc) {
        String addNewsDraft;
        if (selectNewsDraft( id )) {
            addNewsDraft = "update t_draft set subjectId=" + subject + ",title=" + addQuotationMarks( setStr( title ) ) +
                    ",date=" + addQuotationMarks( setStr( nowDate ) ) + ",author=" + addQuotationMarks( setStr( author ) ) +
                    ",abstract=" + addQuotationMarks( setStr( newAbstract ) ) + ",content=" + addQuotationMarks( setStr( content ) ) +
                    ",image=" + addQuotationMarks( imgSrc ) + " WHERE id=" + id;
        } else {
            addNewsDraft = "insert into t_draft (id,subjectId,title,date,author,abstract,content,image) value(" +
                    id + "," + subject + "," + addQuotationMarks( setStr( title ) ) +
                    "," + addQuotationMarks( setStr( nowDate ) ) + "," + addQuotationMarks( setStr( author ) ) +
                    "," + addQuotationMarks( setStr( newAbstract ) ) + "," + addQuotationMarks( setStr( content ) ) +
                    "," + addQuotationMarks( imgSrc ) + ")";
        }
        try (PreparedStatement st = conn.prepareStatement( addNewsDraft )) {
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException( "数据库操作异常" );
        }
    }

    //添加评论
    public void addCommentArea (int newsId,String userName,String ip,String comment,String date) {
        if (userName == null || ip == null || comment == null || date == null) {
            return;
        }
        String addCommentArea = "insert into t_commentarea (newsId,ip,userName,comment,date) " +
                "value(?,?,?,?,?)";
        try (PreparedStatement st = conn.prepareStatement( addCommentArea )) {
            st.setInt( 1,newsId );
            st.setString( 2,setStr( ip ) );
            st.setString( 3,setStr( userName ) );
            st.setString( 4,setStr( comment ) );
            st.setString( 5,setStr( date ) );
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException( "数据库操作异常" );
        }
    }

    //删除用户
    public boolean delUser (int id) {
        String removeUser = "delete from t_user where userId = ?";
        try (PreparedStatement st = conn.prepareStatement( removeUser )) {
            st.setInt( 1,id );
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException( "数据库操作异常" );
        }
    }

    //删除主题
    public void removeTheme (int id) {
        String removeUser = "delete from t_theme where id = ?";
        try (PreparedStatement st = conn.prepareStatement( removeUser )) {
            st.setInt( 1,id );
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException( "数据库操作异常" );
        }
    }

    //删除新闻
    public void removeNews (int id) {
        String removeNews = "delete from t_news where id = ?";
        if (selectCommentArea(id) != null){
            removeComment(id);
        }
        try (PreparedStatement st = conn.prepareStatement( removeNews )) {
            st.setInt( 1,id );
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException( "数据库操作异常" );
        }
    }

    //删除评论
    public void removeComment (int id) {
        String removeUser = "delete from t_commentarea where newsId = ?";
        try (PreparedStatement st = conn.prepareStatement( removeUser )) {
            st.setInt( 1,id );
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException( "数据库操作异常" );
        }
    }

    //从草稿箱中删除指定新闻
    public void removeNewsForDraft (int id) {
        String removeNews = "delete from t_draft where id=?";
        try (PreparedStatement st = conn.prepareStatement( removeNews )) {
            st.setInt( 1,id );
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException( "数据库操作异常" );
        }
    }

    //修改用户名和密码
    public boolean setUser (int userId,String userName,String userPwd) {
        if (userName == null || userPwd == null || !selectUserIsEmpty(userId)) {
            return false;
        }
        String setUser = "update t_user set userName = ?,password = ? where userId = ?";
        try (PreparedStatement st = conn.prepareStatement( setUser )) {
            st.setInt( 3,userId );
            st.setString( 2,setStr( userPwd ) );
            st.setString( 1,setStr( userName ) );
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException( "数据库操作异常" );
        }
    }

    //修改权限等级
    public boolean setPermission(int id,int permission){
        String setPermission = "update t_user set permission = ? where userId = ?";
        try (PreparedStatement st = conn.prepareStatement( setPermission )) {
            st.setInt( 2,id );
            st.setInt( 1,permission );
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException( "数据库操作异常" );
        }
    }

    //修改主题
    public void setTheme (int id,String newName) {
        if (newName == null) {
            return;
        }
        String setThemeName = "update t_theme set name = ? where id = ?";
        try (PreparedStatement st = conn.prepareStatement( setThemeName )) {
            st.setInt( 2,id );
            st.setString( 1,setStr( newName ) );
            st.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException( "数据库操作异常" );
        }
    }

    //登录验证
    public boolean selectLogin (String userName,String pwd) {
        if (userName == null || pwd == null) {
            return false;
        }
        String selectLogin = "select count(*) from t_user where userName = ? and password = ? ";
        try (PreparedStatement st = conn.prepareStatement( selectLogin )) {
            st.setString( 1,userName );
            st.setString( 2,pwd );
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt( 1 ) > 0;
            }
            throw new SQLException();
        } catch (SQLException e) {
            throw new RuntimeException( "数据库操作异常" );
        }
    }

    //查询所有用户
    public ArrayList<user> selectAllUser (){
        ArrayList<user> allUserName = new ArrayList<>();
        String selectAllUserName = "select * from t_user";
        try (PreparedStatement st = conn.prepareStatement( selectAllUserName );
             ResultSet rs = st.executeQuery()) {
            while (rs.next()){
                allUserName.add( new user(rs.getInt( "userId" ),
                        rs.getString( "userName" ),
                        rs.getString( "password" ),
                        rs.getInt( "permission" )) );
            }
            return allUserName;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException( "数据库操作异常" );
        }
    }

    //查询指定用户是否存在
    public boolean selectUserIsEmpty (int id){
        String selectUser = "select count(userId) from t_user where userId=" + id;
        try (PreparedStatement st = conn.prepareStatement( selectUser );
             ResultSet rs = st.executeQuery()) {
            if (rs.next()){
                return rs.getInt( 1 ) > 0;
            }
            throw new SQLException();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException( "数据库操作异常" );
        }
    }

    //查询指定用户详细信息
    public user selectUser(String userName,String password){
        user user = new user();
        String selectAllUserName = "select * from t_user where userName = \"" + userName + "\" and password = \"" + password + "\"";
        try (PreparedStatement st = conn.prepareStatement( selectAllUserName );
             ResultSet rs = st.executeQuery()) {
            if (rs.next()){
                user.setUserId( rs.getInt( "userId" ) );
                user.setUserName( rs.getString( "userName" ) );
                user.setPassword( rs.getString( "password" ) );
                user.setPermission( rs.getInt( "permission" ) );
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException( "数据库操作异常" );
        }
    }

    //查询主题列表
    public Map<Integer, String> selectTheme () {
        //待完成：按名称首字母排序
        Map<Integer, String> themeList = new LinkedHashMap<>();
        String selectTheme = "select * from t_theme";
        try (PreparedStatement st = conn.prepareStatement( selectTheme )) {
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                themeList.put( rs.getInt( "id" ),rs.getString( "name" ) );
            }
            return themeList;
        } catch (SQLException e) {
            throw new RuntimeException( "数据库操作异常" );
        }
    }

    //按ID查询某个新闻
    public news selectNews (int id) {
        String selectNews = "select * from t_news where id = " + id;
        return getNews( selectNews );
    }

    //按名称查询某个新闻
    public news selectNews (String name) {
        String selectNews = "select * from t_news where title = " + addQuotationMarks(setStr( name ));
        return getNews( selectNews );
    }

    //查询草稿箱中的新闻
    public news selectNewsForDraft (int id) {
        String selectNews = "select * from t_draft where id = " + id;
        return getNews( selectNews );
    }

    //获取新闻
    private news getNews (String selectNews) {
        try (PreparedStatement st = conn.prepareStatement( selectNews )) {
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new news( rs.getInt( "id" ),
                        rs.getInt( "subjectId" ),
                        rs.getString( "title" ),
                        rs.getString( "date" ),
                        rs.getString( "author" ),
                        rs.getString( "abstract" ),
                        rs.getString( "content" ),
                        rs.getString( "image" ) );
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException( "数据库操作异常" );
        }
    }

    //查询指定长度的新闻列表
    public ArrayList<news> selectNews (int start,int length) {
        ArrayList<news> allNews = new ArrayList<>();
        String selectNews = "select * from t_news ORDER BY date DESC limit " + start + "," + length;
        return getNews( allNews,selectNews );
    }

    //查询所有新闻的列表长度
    public int selectNewsLength () {
        String selectNewsLength = "select count(*) from t_news";
        return getLength( selectNewsLength );
    }

    //查询指定主题的新闻列表长度
    public int selectNewsLength (int themeId) {
        String selectNews = "select count(*) from t_news where subjectId=" + themeId;
        return getLength( selectNews );
    }

    //查询长度方法
    private int getLength (String selectNewsLength) {
        try (PreparedStatement st = conn.prepareStatement( selectNewsLength )) {
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt( 1 );
            }
            throw new SQLException();
        } catch (SQLException e) {
            throw new RuntimeException( "数据库操作异常" );
        }
    }

    //查询指定主题和指定长度的新闻列表
    public ArrayList<news> selectNews (int id,int start,int length) {
        ArrayList<news> partialNews = new ArrayList<>();
        String selectNews = "select * from t_news where subjectId=" + id + " ORDER BY date DESC limit " + start + "," + length;
        return getNews( partialNews,selectNews );
    }

    //新闻列表赋值
    private ArrayList<news> getNews (ArrayList<news> partialNews,String selectNews) {
        try (PreparedStatement st = conn.prepareStatement( selectNews )) {
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                partialNews.add( new news( rs.getInt( "id" ),
                        rs.getInt( "subjectId" ),
                        rs.getString( "title" ),
                        rs.getString( "date" ),
                        rs.getString( "author" ),
                        rs.getString( "abstract" ),
                        rs.getString( "content" ),
                        rs.getString( "image" ) ) );
            }
            return partialNews;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException( "数据库操作异常" );
        }
    }

    //查询评论列表
    public ArrayList<commentArea> selectCommentArea (int id) {
        ArrayList<commentArea> commentAreaArrayList = new ArrayList<>();
        String selectTheme = "select * from t_commentarea where newsId=" + id + " ORDER BY date DESC";
        try (PreparedStatement st = conn.prepareStatement( selectTheme )) {
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                commentAreaArrayList.add( new commentArea(
                        rs.getInt( "id" ),
                        rs.getInt( "newsId" ),
                        rs.getString( "ip" ),
                        rs.getString( "userName" ),
                        rs.getString( "comment" ),
                        rs.getString( "date" ) ) );
            }
            return commentAreaArrayList;
        } catch (SQLException e) {
            throw new RuntimeException( "数据库操作异常" );
        }
    }

    //查询草稿箱中是否有某个新闻
    public boolean selectNewsDraft (int id) {
        String addNewsDraft = "select count(id) from t_draft where id = " + id;
        try (PreparedStatement st = conn.prepareStatement( addNewsDraft )) {
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt( 1 ) > 0;
            }
            throw new SQLException();
        } catch (SQLException e) {
            throw new RuntimeException( "数据库操作异常" );
        }
    }

    //关闭数据库
    public void close () {
        try {
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException( "数据库拒绝访问，可能已经关闭或处于异常状态" );
        }
    }
}
