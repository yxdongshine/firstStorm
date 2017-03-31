package com.yxd.WordCount;

import java.util.Map;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichSpout;
import backtype.storm.topology.OutputFieldsDeclarer;

public class MySpout implements IRichSpout {

	@Override
	public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
		// 是spout组件的初始化方法，首先被调用，且被调用一次；
		// 一般用来进行初始化变量等
		

	}

	@Override
	public void close() {
		// 关闭的时候调用

	}

	@Override
	public void activate() {
		// TODO Auto-generated method stub

	}

	@Override
	public void deactivate() {
		// TODO Auto-generated method stub

	}

	@Override
	public void nextTuple() {
		// 下一个个bolt发射的数据 循环调用 不停发射
		// 也是核心方法 实现从数据源上获取数据的逻辑以及向后面组件（bolt）发射数据

	}

	@Override
	public void ack(Object msgId) {
		// tuple发射成功调用的方法

	}

	@Override
	public void fail(Object msgId) {
		// tuple发射失败调用的方法
		// 这个和ack方法一般是配合使用 用来进行一个安全上的设置

	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		// 这个方法 是 申明 向后面组件发射tuple的key是什么

	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		// 这个方法是设置该spout 一些专用的参数 使用的方法
		return null;
	}

}
