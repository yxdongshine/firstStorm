package com.yxd.worldcount;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.spout.SchemeAsMultiScheme;
import backtype.storm.topology.TopologyBuilder;
import storm.kafka.*;

import java.util.UUID;

/**
 * Created by Administrator on 2017/3/30 0030.
 */
public class WordCountTopolopy {

    public static void main(String[] args) {

        //创建topology
        TopologyBuilder tb = new TopologyBuilder();

        //集成kafka
        //设置参数
        BrokerHosts bh = new ZkHosts("hadoop1:2181/kafka");
        //主题
        String togicName = "yxdkafka0";
        String zkRoot = "/kafka";
        //唯一编号
        String id = UUID.randomUUID().toString();
        //设置SpoutConfig
        SpoutConfig sc = new SpoutConfig(bh,togicName,zkRoot,id);

        //设置�?大偏移量消费模式
        sc.forceFromStart = false;
        //设置解析类型
        sc.scheme = new SchemeAsMultiScheme(new StringScheme());


        //初始化kafkaspout
        KafkaSpout ks = new KafkaSpout(sc);

        //topology设置spout
        tb.setSpout("wcspout",ks);

        //设置spout分割组件
        tb.setBolt("wcbolt",new DeselfSplitBolt()).shuffleGrouping("wcspout");

        // 数据流分组的3种方�?
		/*
		 * .shuffleGrouping(SPOUT_ID);  随机分组
		 * .fieldsGrouping(componentId, fields);  按（字段）key分组
		 * .globalGrouping(componentId);  全局分组（只分到�?组中�?
		 *
		 */


        //本地模式测试
        LocalCluster localCluster = new LocalCluster();
        //全局参数设置
        Config config = new Config();
        config.put("yxdK", "yxdV");
        //运行
        localCluster.submitTopology("wcTopology",config,tb.createTopology());

    }
}
