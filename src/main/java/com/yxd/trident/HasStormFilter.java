package com.yxd.trident;

import java.util.Map;

import storm.trident.operation.Filter;
import storm.trident.operation.TridentOperationContext;
import storm.trident.tuple.TridentTuple;

public class HasStormFilter implements Filter {

	@Override
	public void prepare(Map conf, TridentOperationContext context) {
		// TODO Auto-generated method stub

	}

	@Override
	public void cleanup() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isKeep(TridentTuple tuple) {
		String str = tuple.getStringByField("str");
		if(str.contains("storm")){
			System.out.println("-----HasStormFilter say: "+str+".-----");
			return true;  //保留该tuple
		}
		return false;  //丢弃该tuple
	}

}
