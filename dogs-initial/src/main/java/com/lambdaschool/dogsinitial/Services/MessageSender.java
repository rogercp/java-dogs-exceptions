package com.lambdaschool.dogsinitial.Services;


import com.lambdaschool.dogsinitial.DogsinitialApplication;
import com.lambdaschool.dogsinitial.model.Dog;
import com.lambdaschool.dogsinitial.model.MessageDetail;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.Queue;
import java.util.Random;

@Service
public class MessageSender
{

    private RabbitTemplate rt;
    public MessageSender(RabbitTemplate rt)
    {
        this.rt=rt;

    }
    public void SendMessge()
    {
        for(Dog d: DogsinitialApplication.ourDogList.dogList)
        {
            int rand=new Random().nextInt(10);
            boolean randBool = new Random().nextBoolean();
            MessageDetail message= new MessageDetail(d.toString(), rand, randBool);

            rt.convertAndSend(DogsinitialApplication.QUEUE_NAME_HIGH,message);
        }
    }
}
