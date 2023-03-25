/**
 * 
 */
package com.briandutremble.birds.controller;

import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.briandutremble.birds.entity.AddBirdRequest;
import com.briandutremble.birds.entity.AddObservationRequest;
import com.briandutremble.birds.entity.Bird;
import com.briandutremble.birds.entity.Observation;
import com.briandutremble.birds.service.BirdService;
import lombok.extern.slf4j.Slf4j;


@RestController
@Slf4j
public class BasicBirdController implements BirdController {
  
  @Autowired
  private BirdService birdService;
  
  @Override
  public List<Bird> retrieveBirds() {
    log.info("Controller: List birds");
    return birdService.retrieveBirds();
  }
  
  @Override
  public Bird getBird(int birdId) {
    log.info("Controller: Get bird with ID={}", birdId);
    return birdService.getBird(birdId);
  }

  @Override
  public Bird addBird(AddBirdRequest birdRequest) {
    log.info("Controller: Adding bird {}", birdRequest);
    return birdService.addBird(birdRequest);
  }


  @Override
  public Bird modifyBird(Bird birdRequest) {
    log.info("Controller: Modify bird {}", birdRequest);
    return birdService.modifyBird(birdRequest);
  }

 
  @Override
  public void deleteBird(int birdId) {
    log.info("Controller: Delete bird with ID={}", birdId);
    birdService.deleteBird(birdId);
  }
  

}
