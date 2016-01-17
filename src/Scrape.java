

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Sora on 2016/1/16.
 */
public class Scrape {

    //爬取网页最外层
    private static String MAIN_URL = "http://www.basketball-reference.com/";
    //爬取内容保存路径
    private static String PATH = "D:/data";

    public static void main(String args[]){
        getTeamList();
    }

    private static  void getTeamList() {
        StringBuffer result = new StringBuffer();
        try {
            URL url = new URL(MAIN_URL + "/teams");
            URLConnection conn = url.openConnection();
            conn.connect();
            InputStream in = conn.getInputStream();
            //Reader
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line = null;
            while ((line = reader.readLine()) != null){
                result.append(new String(line.getBytes(),"utf-8"));
            }
            reader.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
//        System.out.println(result.toString());
     // 取出<div class="table_container p402_hide " id="div_defunct"> 和 </div> 之间的部分
        Pattern pattern = Pattern.compile("(.*)(<div class=\"table_container p402_hide \" id=\"div_active\">)(.*?)(</div>)(.*)");
        Matcher matcher = pattern.matcher(result);
//        System.out.println("A");
        if (matcher.matches()){
//        	System.out.println("B");
            String str1 = matcher.group(3);
//            System.out.println(str1.toString());
            pattern = Pattern.compile("(<td align=\"left\" ><a href=\"/teams/)(.*?)(/\">)(.*?)(</a></td>   <td align=\"left\" >)(.*?)(</td>   <td align=\"right\" >)(.*?)(</td>)");
            matcher = pattern.matcher(str1);
            while (matcher.find()){
            	System.out.println(matcher.group(8)+"	"+matcher.group(2)+"	"+matcher.group(4));
            }
        }
        
    }
 
}