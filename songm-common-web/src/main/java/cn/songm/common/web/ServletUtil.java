package cn.songm.common.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Type;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.songm.common.utils.JsonUtils;

public class ServletUtil {

    public static String requestJson(HttpServletRequest request)
            throws IOException {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(request.getInputStream()));
        String line = null;
        StringBuffer buf = new StringBuffer();
        while ((line = reader.readLine()) != null) {
            buf.append(line);
        }
        reader.close();
        return buf.toString();
    }

    public static void responseJson(HttpServletResponse response, Object o,
            Type t) throws IOException {
        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print(JsonUtils.getInstance().toJson(o, t));
        out.flush();
        out.close();
    }
}
