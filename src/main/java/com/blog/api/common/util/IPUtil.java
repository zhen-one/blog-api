package com.blog.api.common.util;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import org.lionsoul.ip2region.DataBlock;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbSearcher;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

public class IPUtil {


    public static String parseIP(String ip,String spliter) {
        String result = "";
        // 关联下载的id2region.db 离线库
        String dbFile=System.getProperty("user.dir")+"\\src\\main\\resources\\ip2region.db";
        try {
            DbSearcher search = new DbSearcher(new DbConfig(), dbFile);
            // 传入ip进行解析
            DataBlock dataBlock = search.btreeSearch(ip);
            // 获取解析后的数据  格式：国家|大区|省|市|运营商
            String region = dataBlock.getRegion();
            String replace = region.replace("|", spliter);
//            String[] splits = replace.split(",");
//            if (splits.length == 5) {
//                String country = splits[0];
//                String province = splits[2];
//                String city = splits[3];
//                String operator = splits[4];
//                // 拼接数据
//                result = country + spliter + province +spliter + city + spliter + operator;
//            }
            return replace;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String parseIP(String ip) {
       return parseIP(ip,"|");
    }


    /**
     * 解析 用户代理(User-Agent)
     * @param userAgent 用户代理User-Agent ,UA
     * @return "设备类型:%s,操作系统:%s,浏览器:%s,浏览器版本:%s,浏览器引擎:%s,用户代理(User-Agent):[%s]"
     */
    public static Map<String,String> getDevice(String userAgent) {
        //解析agent字符串
        UserAgent ua = UserAgent.parseUserAgentString(userAgent);
        //获取浏览器对象
        Browser browser = ua.getBrowser();
        //获取操作系统对象
        OperatingSystem os = ua.getOperatingSystem();

        Map<String,String> result=new HashMap<>();
        result.put("deviceType", os.getDeviceType().getName());
        result.put("systemName", os.getName());
        result.put("browser", browser.getName());
        result.put("browser-version", browser.getVersion(userAgent).getVersion());
        return result;
//        return String.format("设备类型:%s,操作系统:%s,浏览器:%s,浏览器版本:%s,浏览器引擎:%s,用户代理(User-Agent):[%s]",
//                os.getDeviceType(),
//                os.getName(),
//                browser.getName(),
//                browser.getVersion(userAgent),
//                browser.getRenderingEngine(),
//                userAgent
//        );
    }


    public static UserAgent parseUserAgent(String userAgent) {
        //解析agent字符串
        UserAgent ua = UserAgent.parseUserAgentString(userAgent);

        return ua;

    }
}
