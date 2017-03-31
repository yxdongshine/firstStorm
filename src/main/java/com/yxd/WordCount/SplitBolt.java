package com.yxd.WordCount;

import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class SplitBolt implements IRichBolt {
	private OutputCollector collector;
	@Override
	public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
		// 初始化方法 和spout中的open方法类似
		// OutputCollector collector 数据发射器 提升作用域
		this.collector = collector;   //初始化的时候赋值
		//得到config传递来的参数u
		Object myV = stormConf.get("myK");
		System.out.println("-----myK->"+myV.toString()+"-----");
	}

	@Override
	public void execute(Tuple input) {
		// 真正实现逻辑功能的方法
		// Tuple input就是我们发射进来的key-val对
		// public static final String STRING_SCHEME_KEY = "str";
		String str = input.getStringByField("str");
//		System.out.println("-----SplitBolt say: str is "+str+ ".-----");
		// 完成字符串分割后  向后发射tuple
		String[] strs = str.split(" ");
		for(String word : strs){
			System.out.println("-----SplitBolt say: word is ["+word+ "].-----");
			// countBolt没写 暂时测试使用
			//collector.emit(new Values(word));  //发射数据需要使用storm的指定类
		}
	}

	@Override
	public void cleanup() {
		// TODO Auto-generated method stub

	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// 指定向后发射的key
		//declarer.declare(new Fields("word"));
		

	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}

}
