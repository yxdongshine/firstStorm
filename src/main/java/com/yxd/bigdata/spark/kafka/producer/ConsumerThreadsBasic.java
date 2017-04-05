package com.yxd.bigdata.spark.kafka.producer;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by 20160905 on 2017/2/27.
 */
public class ConsumerThreadsBasic {


    public static  AtomicBoolean isRunning = new AtomicBoolean(true);

    public static void main(String[] args) {

        String togicName = "yxdkafka0";
        Integer threadNum = 3;

        ConsumerByThreads cbt = new ConsumerByThreads(togicName,threadNum);

        cbt.run();
        try {
            Thread.sleep(1000*60*60*24*1);
        } catch (InterruptedException e) {

        }

        isRunning.set(false);
        cbt.closeProducer();
    }
}
