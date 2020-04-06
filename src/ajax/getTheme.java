package ajax;

import com.alibaba.fastjson.JSONArray;
import database.sql;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class getTheme extends HttpServlet {
    @Override
    protected void doGet (HttpServletRequest request,HttpServletResponse response) throws IOException {
        sql sql = new sql();
        Map<Integer, String> themeList = sql.selectTheme();
        Map<String, String> newThemeList = new LinkedHashMap<>();
        for (Integer i : themeList.keySet()){
            newThemeList.put( "\"" + i + "\"",themeList.get( i ) );
        }
        sql.close();
        response.setContentType( "text/html;charset=utf-8" );
        response.getWriter().write( JSONArray.toJSONString( newThemeList ) );
    }
}
