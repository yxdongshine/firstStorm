package com.yxd.worldcount;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichBolt;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 20160905 on 2017/4/1.
 */
public class DeselfCountBolt implements IRichBolt {

    private HashMap<String,String> wordMap = new HashMap<String,String>();

    private  OutputCollector collector= null ;

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector ;
    }

    @Override
    public void execute(Tuple input) {
        //输入是Word列明
        String word = input.getStringByField("word");
        if (!wordMap.containsKey(word)) {
            wordMap.put(word, 0+"" );
        }

        int count = Integer.parseInt(wordMap.get(word));
        count++;
        wordMap.put(word, count+"");

        System.out.println(word+"******"+count);
        collector.emit(new Values(word, String.valueOf(count)));
    }

    @Override
    public void cleanup() {

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {

        declarer.declare(new Fields("word", "count"));

    }

    @Override
    public Map<String, Object> getComponentConfiguration() {
        return null;
    }
}
