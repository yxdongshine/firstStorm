package com.yxd.com.weatherforcast.test;

import java.util.SortedMap;
import java.util.TreeMap;

public class SortedMapTest {
	
	public static void main(String[] args) {
		 SortedMap<Double,String> topcats=new TreeMap<Double,String>();
		 
		 topcats.put(23.5, "aaa");
		 topcats.put(25.5, "ara");
		 topcats.put(21.5, "aah");
		 topcats.put(29.5, "arre");
		 topcats.put(20.5, "aaw");
		 topcats.put(22.5, "waa");
		 
		 for (int i = 0; i < topcats.size(); i++) {
			if(topcats.size()>=3){
				topcats.remove(topcats.firstKey());
			}
		 }
		 
		 for (Double in : topcats.keySet()) {
			 //map.keySet()返回的是所有key的值
			 String str = topcats.get(in);//得到每个key多对用value的值
			 System.out.println(in + "     " + str);
		 }
	}

}
