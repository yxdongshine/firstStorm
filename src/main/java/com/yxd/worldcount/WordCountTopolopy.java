package com.yxd.worldcount;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.spout.SchemeAsMultiScheme;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;
import high.aruq.HbaseClient;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.storm.guava.collect.Maps;
import org.apache.storm.hbase.bolt.HBaseBolt;
import org.apache.storm.hbase.bolt.mapper.SimpleHBaseMapper;
import storm.kafka.*;

import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * Created by Administrator on 2017/3/30 0030.
 */
public class WordCountTopolopy {

    static HbaseClient hbc = new HbaseClient();
    static String nameSpace = "storm";
    static String create = "yxd";
    static String cf_storm = "stormcf";
    static String table_name = "storm:first_storm_content" ;
    private static final byte[] first_storm_content = Bytes.toBytes(table_name);

    public static void main(String[] args) {

        //创建topology
        TopologyBuilder tb = new TopologyBuilder();

        //连接kafka
        //创建BrokerHosts路径
        BrokerHosts bh = new ZkHosts("hadoop1:2181/kafka");
        //涓婚
        String togicName = "yxdkafka0";
        String zkRoot = "/kafka";
        //生成唯一编号
        String id = UUID.randomUUID().toString();
        //创建SpoutConfig
        SpoutConfig sc = new SpoutConfig(bh,togicName,zkRoot,id);

        //每次从最大消费位置开始消费
        sc.forceFromStart = false;
        //解析模式
        sc.scheme = new SchemeAsMultiScheme(new StringScheme());


        //连接fkaspout
        KafkaSpout ks = new KafkaSpout(sc);

        //topology设置KafkaSpout
        tb.setSpout("wcspout",ks);

        //设置拉取bolt
        tb.setBolt("wcSpiltbolt",new DeselfSplitBolt()).shuffleGrouping("wcspout");

        // 数据流分组的3种方式
		/*
		 * .shuffleGrouping(SPOUT_ID);  随机分组
		 * .fieldsGrouping(componentId, fields);  按（字段）key分组
		 * .globalGrouping(componentId);  全局分组（只分到一组中）
		 *
		 */

        //设置countBolt
        tb.setBolt("countBolt", new DeselfCountBolt()).fieldsGrouping("wcSpiltbolt", new Fields("word"));



        //创建hbase
        hbc.initNameSpace(nameSpace,create);
        System.out.println("========nameSpace created=======");
        hbc.initTable(first_storm_content, cf_storm);
        System.out.println("========initTable created=======");


        //写hbase
        SimpleHBaseMapper mapper = new SimpleHBaseMapper();
        mapper.withColumnFamily(cf_storm);
        mapper.withColumnFields(new Fields("count"));
        mapper.withRowKeyField("word");

        //Random random = new Random(100000000000L);
        //String rowkey = random.nextInt()+"_"+System.currentTimeMillis();
        // mapper.withRowKeyField(rowkey);

        Map<String, Object> map = Maps.newTreeMap();
        map.put("hbase.rootdir", "hdfs://hadoop1:9000/hbase");
        map.put("hbase.zookeeper.quorum", "hadoop1");

        // hbase-bolt
        HBaseBolt hBaseBolt = new HBaseBolt(table_name, mapper).withConfigKey("hbase.conf");

        tb.setBolt("hbaseId", hBaseBolt).shuffleGrouping("countBolt");

        Config conf = new Config();
        conf.setDebug(true);
        conf.put("hbase.conf", map);



        // 集群模式
        if (args != null && args.length > 0) {
            conf.setNumWorkers(3);

            try {
                StormSubmitter.submitTopologyWithProgressBar(args[0], conf, tb.createTopology());
            } catch (AlreadyAliveException e) {
                e.printStackTrace();
            } catch (InvalidTopologyException e) {
                e.printStackTrace();
            }
        }
        else {      // 本地模式
           /*
		 * 本地测试模式
		 */
            LocalCluster localCluster = new LocalCluster();
            // arg0|topology名字, arg1|是向后发送的参数, arg2|topology实例
            localCluster.submitTopology("wcTopology",conf,tb.createTopology());
        }


    }
}
