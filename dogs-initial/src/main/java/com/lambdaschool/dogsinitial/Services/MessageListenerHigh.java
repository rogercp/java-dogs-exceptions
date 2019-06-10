package com.lambdaschool.dogsinitial.Services;

import com.lambdaschool.dogsinitial.DogsinitialApplication;
import com.lambdaschool.dogsinitial.model.MessageDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class MessageListenerHigh
{
    private static final Logger logger =LoggerFactory.getLogger(MessageListenerHigh.class);

    @RabbitListener(queues = DogsinitialApplication.QUEUE_NAME_HIGH)
    public void receiveHighMessage(MessageDetail msg)
    {
        //process the message
        logger.info("Received message {}",msg.toString());
    }
}
