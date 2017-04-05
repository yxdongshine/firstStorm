package com.yxd.WordCount;

import java.util.UUID;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.spout.SchemeAsMultiScheme;
import backtype.storm.topology.TopologyBuilder;
import storm.kafka.BrokerHosts;
import storm.kafka.KafkaSpout;
import storm.kafka.SpoutConfig;
import storm.kafka.StringScheme;
import storm.kafka.ZkHosts;
/**
 * wordcount驱动类
 * @author naixi
 *
 */
public class WordCountTopology {
	private static final String SPOUT_ID = "spoutID";
	private static final String SPLIT_BOLT = "splitID";
	public static void main(String[] args) {
		// 1.构造topology builder
		TopologyBuilder tb = new TopologyBuilder();
		// 2.指定spout组件

		// 使用storm提供的kafkaSpout组件去完成
		// (BrokerHosts hosts|生产者的主机地址, String topic|topic名称, String zkRoot|topic路径, String id|唯一的ID)
		BrokerHosts hosts = new ZkHosts("hadoop1:2181/kafka");
		//public ZkHosts(String brokerZkStr, String brokerZkPath"/kafka/broker") 上面那个万一报错
		String topic = "yxdkafka0";
		String zkRoot = "/kafka";
		String id = UUID.randomUUID().toString();
		SpoutConfig spoutConf = new SpoutConfig(hosts,topic,zkRoot,id);
		// 上面完成后 还有2个参数需要设置
		spoutConf.forceFromStart = false;  //设置每次重启都从上次的地方继续“消费”
		spoutConf.scheme = new SchemeAsMultiScheme(new StringScheme());  //这个是设置以什么数据类型来解析从kafka上读取的数据
		// 思考:回去之后将这行注释掉 测试默认是什么数据格式来解析kafka的数据
		KafkaSpout kafkaSpout = new KafkaSpout(spoutConf);
		tb.setSpout(SPOUT_ID, kafkaSpout);

		// 指定splitBolt组件  字符串分割逻辑功能的bolt
		tb.setBolt(SPLIT_BOLT, new SplitBolt()).shuffleGrouping(SPOUT_ID);
		// 数据流分组的3种方式
		/*
		 * .shuffleGrouping(SPOUT_ID);  随机分组
		 * .fieldsGrouping(componentId, fields);  按（字段）key分组
		 * .globalGrouping(componentId);  全局分组（只分到一组中）
		 *
		 */





		/*
		 * 本地测试模式
		 */
		LocalCluster localCluster = new LocalCluster();
		Config config = new Config();  //向后面的bolt发送key-val对
		config.put("myK", "myV");
		// arg0|topology名字, arg1|是向后发送的参数, arg2|topology实例
		localCluster.submitTopology("wordCountTopo", config, tb.createTopology());






	}

}
