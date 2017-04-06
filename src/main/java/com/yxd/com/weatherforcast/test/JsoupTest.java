package com.yxd.com.weatherforcast.test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Hello world!
 *
 */
public class JsoupTest {
    public static void main( String[] args ) throws IOException{
        
    	/*Connection connect=Jsoup.connect("http://www.toutiao.com/");
    	Document doc=connect.get();
    	//String html=doc.html();
    	//System.out.println(html);
    	Elements select=doc.select("[id=ieudp]");
    	System.out.println(select.get(0));*/
    	//String html = "<html><head><title>First parse</title></head>"
    	//		  + "<body><p>Parsed HTML into a doc.</p></body></html>";
    	//Document doc = Jsoup.parse(html);
    	//System.out.println(doc);
    	//Document doc=Jsoup.parseBodyFragment(html);
    	//Elements body=doc.getElementsByTag("body");
    	//Element body=doc.body();
    	//System.out.println(body);
	    /*Document doc = Jsoup.connect("http://example.com")
			  .data("query", "Java")
			  .userAgent("Mozilla")
			  .cookie("auth", "token")
			  .timeout(3000)
			  .post();
    	System.out.println(doc);*/
    	//File input = new File("/tmp/input.html");
    	//解析文档中的文件
    	//Document doc = Jsoup.parse(input, "UTF-8", "http://example.com/");
    	/*String html = "<p>An <a href='http://example.com/'><b>example</b></a> link.</p>";
    	Document doc = Jsoup.parse(html);//解析HTML字符串返回一个Document实现
    	Element link = doc.select("a").first();//查找第一个a元素
    	System.out.println(link);

    	String text = doc.body().text(); // "An example link"//取得字符串中的文本
    	System.out.println("text: "+text);
    	String linkHref = link.attr("href"); // "http://example.com/"//取得链接地址
    	System.out.println("linkHref: "+linkHref);
    	String linkText = link.text(); // "example""//取得链接地址中的文本
    	System.out.println("linkText: "+linkText);

    	String linkOuterH = link.outerHtml(); 
    	System.out.println("linkOuterH: "+linkOuterH);
    	    // "<a href="http://example.com"><b>example</b></a>"
    	String linkInnerH = link.html(); // "<b>example</b>"//取得链接内的html内容
    	System.out.println("linkInnerH: "+linkInnerH);*/
    	/*Validate.isTrue(args.length == 1, "usage: supply url to fetch");
        String url = args[0];
        print("Fetching %s...", url);

        Document doc = Jsoup.connect(url).get();
        Elements links = doc.select("a[href]");
        Elements media = doc.select("[src]");
        Elements imports = doc.select("link[href]");

        print("\nMedia: (%d)", media.size());
        for (Element src : media) {
            if (src.tagName().equals("img"))
                print(" * %s: <%s> %sx%s (%s)",
                        src.tagName(), src.attr("abs:src"), src.attr("width"), src.attr("height"),
                        trim(src.attr("alt"), 20));
            else
                print(" * %s: <%s>", src.tagName(), src.attr("abs:src"));
        }

        print("\nImports: (%d)", imports.size());
        for (Element link : imports) {
            print(" * %s <%s> (%s)", link.tagName(),link.attr("abs:href"), link.attr("rel"));
        }

        print("\nLinks: (%d)", links.size());
        for (Element link : links) {
            print(" * a: <%s>  (%s)", link.attr("abs:href"), trim(link.text(), 35));
        }
    }

    private static void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    }

    private static String trim(String s, int width) {
        if (s.length() > width)
            return s.substring(0, width-1) + ".";
        else
            return s;
    }*/
    	
    	/*List<String> city2list=new ArrayList<String>();
    	Pattern pattern = Pattern.compile("href=.*\\d{5}");
    	Connection connect=Jsoup.connect("http://tianqi.2345.com/beijing_dz/12.htm");
    	Document doc=connect.get();
    	
    	Elements select=doc.select("[class=citychk]");
    	String html=select.toString();
    	//System.out.println(html);
    	System.out.println("======================");
    	String[] arr=html.split(" ");
    	int i=0;
    	for (String string : arr) {
    		Matcher matcher = pattern.matcher(string);
    		if (matcher.find()){
    			++i;
    			if(i>1){
    				String hrefcity2="http://tianqi.2345.com/wea_history/"+string.split("\"")[1].split("/")[2];
    				System.out.println(hrefcity2);
    				
    			}
			}
		}*/
//List<String> CityWeathHrefList=new ArrayList<String>();
		
    	String html = "<p>An <a href='http://example.com/'><b>example</b></a> link.</p>";
    	Document doc = Jsoup.parse(html);//解析HTML字符串返回一个Document实现
    	Element link = doc.select("a").first();
    	String linkText =  link.attr("href");
    	System.out.println(linkText);
    	
    	/*for (int i = (citylist.length-35); i < citylist.length; i++) {
			System.out.println(citylist[i]);
		}*/
    	
    	/*for (String string : citylist) {
    		String[] hreflist=string.split("\"");
    		String cityhref="http://www.nmc.cn"+hreflist[3];
    		System.out.println(cityhref);
		}*/
    }
 }

