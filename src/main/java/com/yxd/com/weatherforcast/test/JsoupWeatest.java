package com.yxd.com.weatherforcast.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class JsoupWeatest {

	
	public static void main(String[] args) throws IOException {
		
		/*System.out.println("城市名称"+"	"+"日期"+"		"+"星期"+"	"+"上午天气"+"	"+"下午天气"+"	"+"上午温度"+"	"+"下午温度"+"	"+"上午风向"+"	"+"下午风向"+"	"+"上午风力"+"	"+"下午风力");
    	//Pattern pattern = Pattern.compile("href=.*\\d{5}");
    	Connection connect=Jsoup.connect("http://www.nmc.cn/publish/forecast/ASH/baoshan.html");
    	
    	Document doc=connect.get();
    	
    	String cityname=doc.select("[class=cityname]").text().toString().split(" ")[1];
    	
    	Elements select=doc.select("[class=today]");
    	for (Element element : select) {
    		String[] wealist=element.text().toString().split(" ");
    		
    		if(wealist.length==11){
				System.out.print(cityname+"	"+wealist[0]+"("+wealist[2]+")"+"	"+wealist[1]
						+"	"+wealist[3]
						+"	"+wealist[4]
						+"	"+wealist[5]
						+"	"+wealist[6]
						+"	"+wealist[7]
						+"	"+wealist[8]
						+"	"+wealist[9]
						+"	"+wealist[10]);
    				
    		}
    		
    		if(wealist.length==12){
				System.out.print(cityname+"	"+wealist[0]+wealist[1]+"("+wealist[3]+")"+"	"+wealist[2]
						+"	"+wealist[4]
						+"	"+wealist[5]
						+"	"+wealist[6]
						+"	"+wealist[7]
						+"	"+wealist[8]
						+"	"+wealist[9]
						+"	"+wealist[10]
						+"	"+wealist[11]);
    		}
    		
    		if(wealist.length==10){
    			System.out.print(cityname+"	"+wealist[0]
    					+"		"+wealist[1]
    					+"	"+wealist[2]
    					+"	"+wealist[3]
						+"	"+wealist[4]
						+"	"+wealist[5]
						+"	"+wealist[6]
						+"	"+wealist[7]
						+"	"+wealist[8]
						+"	"+wealist[9]);
    		}
    		
    		System.out.println();
		}*/
    	//String[] str=select.split(" ");
    	//for (String string : str) {
			//System.out.println(select);
		//}
    	//System.out.println(select);
    	//String[] arr=html.split(" ");
    	/*int i=0;
    	for (String string : arr) {
    		Matcher matcher = pattern.matcher(string);
    		if (matcher.find()){
    			++i;
    			if(i>1){
    				String hrefcity2="http://tianqi.2345.com/wea_history/"+string.split("\"")[1].split("/")[2];
    				System.out.println(hrefcity2);
    				
    			}
			}
		}
    */
		/*String url = "http://xinjinqiao.tprtc.com/admin/main/flrpro.do";  
		try {  
		    WebClient webClient = new WebClient(BrowserVersion.FIREFOX_10);  
		    //设置webClient的相关参数  
		    webClient.getOptions().setJavaScriptEnabled(true);  
		    webClient.getOptions().setCssEnabled(false);  
		    webClient.setAjaxController(new NicelyResynchronizingAjaxController());  
		    //webClient.getOptions().setTimeout(50000);  
		    webClient.getOptions().setThrowExceptionOnScriptError(false);  
		    //模拟浏览器打开一个目标网址  
		    HtmlPage rootPage = webClient.getPage(url);  
		    System.out.println("为了获取js执行的数据 线程开始沉睡等待");  
		    Thread.sleep(3000);//主要是这个线程的等待 因为js加载也是需要时间的  
		    System.out.println("线程结束沉睡");  
		    String html = rootPage.asText();  
		    System.out.println(html);  
		} catch (Exception e) {  
		}  */
		
      Connection connect=Jsoup.connect("http://www.nmc.cn/publish/forecast/ATJ/xiqing.html");
    	
    	Document doc=connect.get();
    	
    	System.out.println(doc.html().toString());
	}
}
