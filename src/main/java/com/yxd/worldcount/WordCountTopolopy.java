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

        //����topology
        TopologyBuilder tb = new TopologyBuilder();

        //����kafka
        //����BrokerHosts·��
        BrokerHosts bh = new ZkHosts("hadoop1:2181/kafka");
        //主题
        String togicName = "yxdkafka0";
        String zkRoot = "/kafka";
        //����Ψһ���
        String id = UUID.randomUUID().toString();
        //����SpoutConfig
        SpoutConfig sc = new SpoutConfig(bh,togicName,zkRoot,id);

        //ÿ�δ��������λ�ÿ�ʼ����
        sc.forceFromStart = false;
        //����ģʽ
        sc.scheme = new SchemeAsMultiScheme(new StringScheme());


        //����fkaspout
        KafkaSpout ks = new KafkaSpout(sc);

        //topology����KafkaSpout
        tb.setSpout("wcspout",ks);

        //������ȡbolt
        tb.setBolt("wcSpiltbolt",new DeselfSplitBolt()).shuffleGrouping("wcspout");

        // �����������3�ַ�ʽ
		/*
		 * .shuffleGrouping(SPOUT_ID);  �������
		 * .fieldsGrouping(componentId, fields);  �����ֶΣ�key����
		 * .globalGrouping(componentId);  ȫ�ַ��飨ֻ�ֵ�һ���У�
		 *
		 */

        //����countBolt
        tb.setBolt("countBolt", new DeselfCountBolt()).fieldsGrouping("wcSpiltbolt", new Fields("word"));



        //����hbase
        hbc.initNameSpace(nameSpace,create);
        System.out.println("========nameSpace created=======");
        hbc.initTable(first_storm_content, cf_storm);
        System.out.println("========initTable created=======");


        //дhbase
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



        // ��Ⱥģʽ
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
        else {
            LocalCluster localCluster = new LocalCluster();
            // arg0|topology����, arg1|������͵Ĳ���, arg2|topologyʵ��
            localCluster.submitTopology("wcTopology",conf,tb.createTopology());
        }


    }
}
