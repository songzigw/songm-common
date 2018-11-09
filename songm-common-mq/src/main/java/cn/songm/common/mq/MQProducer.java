package cn.songm.common.mq;

public interface MQProducer {

	/**
     * 发送消息到指定队列
     * @param object
     */
    public void sendDataToQueue(Object object);
}
