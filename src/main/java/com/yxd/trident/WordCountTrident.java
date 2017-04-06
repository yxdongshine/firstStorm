package com.yxd.trident;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import storm.trident.Stream;
import storm.trident.TridentTopology;
import storm.trident.operation.Filter;
import storm.trident.testing.FixedBatchSpout;

/**
 * 通过wordcount案例来理解Trident
 * @author Administrator
 *
 */
public class WordCountTrident {
	private static final String SPOUT_ID = "MySpout";
	public static void main(String[] args) {
		/*
		 * Trident是storm对topology的高层次的抽象
		 * 1.Trident中只保留了spout，不再有bolt组件，将之前的bolt组件所实现
		 * 的逻辑功能抽象成operation，比如分割，统计等；
		 * 2.Trident具有批次的概念，将固定的条数的tuple划分为一个批次，
		 * 并且按照时间顺序具有一个编号；
		 * 更新统计结果要严格按照批次顺序进行；
		 * 3.Trident具有事务控制的功能，同一批次内 可以具有事务处理能力，事务处理具有三种级别：
		 * 3.1. NON-TRANSACTION ：非事务控制
		 *  允许同一批次内部分tuple处理失败，失败的tuple会在其它多个批次内进行多次的重试；
		 * 3.2. TRANSACTION ： 严格的事务控制
		 *  要求同批次内失败的tuple只能在该批次内重试，如果一直不成功，任务程序挂起；
		 * 3.3. OPAQUE-TRANSACTION ：模糊的事务控制
		 *  允许同一批次内部分的tuple处理失败，失败的tuple在其它多个批次内进行一次重试；
		 */
		
		//框架内提供的测试spout
		//fields|向后发射的指定key, maxBatchSize|多少条封装成一个批次, outputs|测试数据，多条数据以逗号隔开
		FixedBatchSpout fbs = new FixedBatchSpout(new Fields("str","comm"), 5, 
				new Values("hello storm","h s"),new Values("hello shanghai","h s"),new Values("hadoop storm","h s"),
				new Values("google microsoft","g m"),new Values("nokia google","n g"),new Values("google storm","g s"));
		// 设置循环发射数据 默认只发射一次
		fbs.setCycle(true);
		
		
		// 构造topology
		TridentTopology tt = new TridentTopology();
		//构造stream (txId, spout)  返回的就是stream流
		Stream stream = tt.newStream(SPOUT_ID, fbs);
		
		
		
		// 初识Trident中最重要的方法 each()
		// (inputFields|指定后面方法能看见的key, filter|分割/过滤的方法)
		stream
		//.each(new Fields("str"), new PrintTestFilter())
		.each(new Fields("str"), new HasStormFilter())    //测试 抛弃不含有storm字符的tuple
		.each(new Fields("str"), new PrintTestFilter());   
		
		
		Config config = new Config();
		
		//本地测试代码编写
		LocalCluster lc = new LocalCluster();
		
			//(arg0|topology的名称, arg1|config, arg2|topology)
		lc.submitTopology("wordCountTrident", config, tt.build());
	}

}
