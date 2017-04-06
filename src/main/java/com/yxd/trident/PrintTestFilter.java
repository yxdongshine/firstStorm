package com.yxd.trident;

import java.util.List;
import java.util.Map;

import storm.trident.operation.Filter;
import storm.trident.operation.TridentOperationContext;
import storm.trident.tuple.TridentTuple;

public class PrintTestFilter implements Filter {

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
		// TODO Auto-generated method stub
		List<String> list = tuple.getFields().toList();
		int size = list.size();
		for(int i=0;i<size;i++){
			String key = list.get(i);
//			tuple.getStringByField(field)  注意 不能使用getString 无法判断里面的数据类型
			Object val = tuple.getValueByField(key);
			System.out.println("-----PrintTestFilter say: The key is ["+key+"]; The value is ["+val+"].-----");
		}
		return true;  //返回真 则保留这个tuple 返回假则丢弃这个tuple
	}

}
