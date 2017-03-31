package com.yxd.worldcount;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Tuple;

import java.util.Map;

/**
 * Created by Administrator on 2017/3/30 0030.
 */
public class DeselfSplitBolt implements IRichBolt {
    @Override
    public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {

    }

    @Override
    public void execute(Tuple tuple) {
        {
            // 真正实现逻辑功能的方法
            // Tuple input就是我们发射进来的key-val对
            // public static final String STRING_SCHEME_KEY = "str";
            String str = tuple.getStringByField("str");
//		     System.out.println("-----SplitBolt say: str is "+str+ ".-----");
            // 完成字符串分割后  向后发射tuple
            String[] strs = str.split(" ");
            for(String word : strs){
                System.out.println("-----SplitBolt say: word is ["+word+ "].-----");
                // countBolt没写 暂时测试使用
                //collector.emit(new Values(word));  //发射数据需要使用storm的指定类
            }
        }
    }

    @Override
    public void cleanup() {

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }
}
