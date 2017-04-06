package com.yxd.com.weatherforcast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CityUtil {
	public static void main(String[] args) throws IOException {
		getCentCity();
	}
	
	public static void getCentCity() throws IOException{
		/*List<String> cities=new ArrayList<String>();
		return cities;*/
	    StringBuffer buffer=new StringBuffer();;
        BufferedReader br = new BufferedReader(new FileReader("url.txt"));//构造一个BufferedReader类来读取文件
        String s = null;
        while((s = br.readLine())!=null){//使用readLine方法，一次读一行
        	String str="http://www.nmc.cn"+s;
        	buffer.append(str+"\n");   
        }
        FileWriter fw=new FileWriter("urldetail.txt");
    	BufferedWriter bw=new BufferedWriter(fw);
    	bw.write(buffer.toString());
    	bw.close();
        br.close();    
    
	}

}
