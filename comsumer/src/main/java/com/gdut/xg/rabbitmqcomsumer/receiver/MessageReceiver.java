package com.gdut.xg.rabbitmqcomsumer.receiver;

import com.gdut.xg.common.output.SelfMessageOutPut;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @author lulu
 * @Date 2019/6/16 23:04
 */
@Component
public class MessageReceiver {

    @RabbitListener(bindings =@QueueBinding(
            value=@Queue(value="m-queue",durable="true"),
            exchange = @Exchange(name="m-exchange",type = "topic"),
            key="m.*"
    )
    )
    /**
     * 传输的对象
     * 消息头
     * 处理的管道
     */
    @RabbitHandler
    public void onMessageReceiver(@Payload SelfMessageOutPut m, @Headers Map<String,Object> headers, Channel channel){
        System.out.println("收到消息:"+m.getId());
        System.out.println("消息如下:"+m.getContent());
        //处理
        try {

//批量接受false
            channel.basicAck((Long)headers.get(AmqpHeaders.DELIVERY_TAG),false);


        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
