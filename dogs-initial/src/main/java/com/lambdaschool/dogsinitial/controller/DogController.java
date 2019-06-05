package com.lambdaschool.dogsinitial.controller;

import com.lambdaschool.dogsinitial.exception.ResourceNotFoundException;
import com.lambdaschool.dogsinitial.model.Dog;
import com.lambdaschool.dogsinitial.DogsinitialApplication;
import com.lambdaschool.dogsinitial.model.MessageDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;

@RestController
@RequestMapping("/dogs")
public class DogController
{
    private static final Logger logger= LoggerFactory.getLogger(DogController.class);

    @Autowired
    RabbitTemplate rt;


    // localhost:2020/dogs/dogs
    @GetMapping(value = "/dogs",
                produces = {"application/json"})
    public ResponseEntity<?> getAllDogs()
    {
        logger.info("/dogs/dogs accessed");
         MessageDetail message = new MessageDetail("/dogs/dogs accessed", 7, false);
        rt.convertAndSend(DogsinitialApplication.QUEUE_NAME_HIGH,message);
        return new ResponseEntity<>(DogsinitialApplication.ourDogList.dogList, HttpStatus.OK);
    }



    // localhost:2020/dogs/{id}
    @GetMapping(value = "/{id}",
            produces = {"application/json"})
    public ResponseEntity<?> getDogDetail(@PathVariable long id) throws ResourceNotFoundException
    {
        logger.info("/dogs "+id+ " accessed");
        MessageDetail message = new MessageDetail("/dogs \" +id+\" accessed ", 1, true);
        rt.convertAndSend(DogsinitialApplication.QUEUE_NAME_HIGH, message);
        Dog rtnDog;
        if(DogsinitialApplication.ourDogList.findDog(d -> (d.getId() == id))==null)
        {
            throw new ResourceNotFoundException("Dog with id "+id+" not found");
        }else
            {
                rtnDog = DogsinitialApplication.ourDogList.findDog(d -> (d.getId() == id));
            }

        return new ResponseEntity<>(rtnDog, HttpStatus.OK);
    }



    // localhost:2020/dogs/breeds/{breed}
    @GetMapping(value = "/breeds/{breed}",
            produces = {"application/json"})
    public ResponseEntity<?> getDogBreeds (@PathVariable String breed)
    {
        logger.info("/dogs/breeds "+breed+ " accessed");
        MessageDetail message = new MessageDetail("/dogs/breeds \" +breed+\" accessed ", 4, true);
        rt.convertAndSend(DogsinitialApplication.QUEUE_NAME_HIGH, message);
        ArrayList<Dog> rtnDogs = DogsinitialApplication.ourDogList.
                findDogs(d -> d.getBreed().toUpperCase().equals(breed.toUpperCase()));
        if(rtnDogs.size()==0)
        {
            throw new ResourceNotFoundException("No Dogs found that are the breed "+breed);
        }
        return new ResponseEntity<>(rtnDogs, HttpStatus.OK);
    }

    // localhost:2020/dogs/dogtable
    @GetMapping(value="/dogtable")
    public ModelAndView displayDogTable()
    {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("dogs");
        mav.addObject("dogList",DogsinitialApplication.ourDogList.dogList);

        return mav;
    }



}
