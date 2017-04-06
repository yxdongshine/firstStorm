package com.yxd.com.weatherforcast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CityForcast {
	
	public static void main(String[] args) throws IOException {
		StringBuffer buffer=new StringBuffer();
		List<String> city=getAllCityHref();
		List<String> cityweadet=new ArrayList<String>();
		int i=0;
		for (String string : city) {
			if(i==0){
				buffer.append("城市名称"+"	"+"日期"+"		"+"星期"+"	"+"上午天气"+"	"+"下午天气"+"	"+"上午温度"+"	"+"下午温度"+"	"+"上午风向"+"	"+"下午风向"+"	"+"上午风力"+"	"+"下午风力");
			}
			cityweadet=getDetilWea(string);
			for (String cityweastr : cityweadet) {
				System.out.println(cityweastr);
				buffer.append(cityweastr+"\n");
			}
			i++;
		}
		FileWriter fw=new FileWriter("detailweainfo.txt");
    	BufferedWriter bw=new BufferedWriter(fw);
    	bw.write(buffer.toString());
    	bw.close();
	}
	
	public static List<String> getCityWeathHref() throws IOException{
		
		List<String> CityWeathHrefList=new ArrayList<String>();
		
		Connection connect=Jsoup.connect("http://www.nmc.cn/publish/forecast/china.html?x=0&y=0#");
        Document doc=connect.get();
    	
    	Elements select=doc.select("[class=cname]").select("a");
    	String[] citylist=select.toString().split("\n");
    	
    	for (int i = (citylist.length-35); i < citylist.length; i++) {
    		String[] hreflist=citylist[i].split("\"");
    		String cityhref="http://www.nmc.cn"+hreflist[3];
    		CityWeathHrefList.add(cityhref);
		}
    	
    	return CityWeathHrefList;
    	
	}
	
    public static List<String> getAllCityHref() throws IOException{
		
		List<String> CityWeathHrefList=new ArrayList<String>();
		
        BufferedReader br = new BufferedReader(new FileReader("urldetail.txt"));//构造一个BufferedReader类来读取文件
        String s = null;
        while((s = br.readLine())!=null){//使用readLine方法，一次读一行
        	CityWeathHrefList.add(s); 
        }
        br.close();
    	return CityWeathHrefList;
    	
	}

	public static List<String> getDetilWea(String url) throws IOException{
		System.out.println("城市名称"+"	"+"日期"+"		"+"星期"+"	"+"上午天气"+"	"+"下午天气"+"	"+"上午温度"+"	"+"下午温度"+"	"+"上午风向"+"	"+"下午风向"+"	"+"上午风力"+"	"+"下午风力");
		List<String> detilWea=new ArrayList<String>();
    	Connection connect=Jsoup.connect(url);
    	
    	Document doc=connect.get();
    	
    	String cityname=doc.select("[class=cityname]").text().toString().split(" ")[1];
    	
    	Elements select=doc.select("[class=today]");
    	for (Element element : select) {
    		String[] wealist=element.text().toString().split(" ");
    		
    		if(wealist.length==11){
    			detilWea.add(cityname+"	"+wealist[0]+"("+wealist[2]+")"+"	"+wealist[1]
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
    			detilWea.add(cityname+"	"+wealist[0]+wealist[1]+"("+wealist[3]+")"+"	"+wealist[2]
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
    			detilWea.add(cityname+"	"+wealist[0]
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
    		
		}
    	
    	return detilWea;
	}
	
	
}
