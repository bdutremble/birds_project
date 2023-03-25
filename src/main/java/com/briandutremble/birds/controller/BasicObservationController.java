/**
 * 
 */
package com.briandutremble.birds.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.briandutremble.birds.entity.AddObservationRequest;
import com.briandutremble.birds.entity.Observation;
import com.briandutremble.birds.service.ObservationService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author briandutremble
 *
 */
@RestController
@Slf4j
public class BasicObservationController implements ObservationController {
  
  @Autowired
  private ObservationService observationService;
  @Override
  public Observation addObservation(AddObservationRequest observationRequest) {
    log.info("Service: Adding observation {}", observationRequest);
    return observationService.addObservation(observationRequest);
  } 
}
