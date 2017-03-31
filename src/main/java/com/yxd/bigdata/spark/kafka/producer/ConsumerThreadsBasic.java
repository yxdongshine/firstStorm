package com.yxd.bigdata.spark.kafka.producer;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by 20160905 on 2017/2/27.
 */
public class ConsumerThreadsBasic {


    public static  AtomicBoolean isRunning = new AtomicBoolean(true);

    public static void main(String[] args) {

        String togicName = "yxdkafka0";
        Integer threadNum = 1;

        //调用生产�?
        ConsumerByThreads cbt = new ConsumerByThreads(togicName,threadNum);

        cbt.run();
        //时间长点 30秒钟Long.MAX_VALUE
        try {
            Thread.sleep(1000*60*3);
        } catch (InterruptedException e) {

        }

        isRunning.set(false);
        // 关闭连接
        cbt.closeProducer();
    }
}
