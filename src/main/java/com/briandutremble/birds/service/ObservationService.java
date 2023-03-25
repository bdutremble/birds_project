/**
 * 
 */
package com.briandutremble.birds.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.briandutremble.birds.dao.ObservationDao;
import com.briandutremble.birds.entity.AddObservationRequest;
import com.briandutremble.birds.entity.Observation;
import lombok.extern.slf4j.Slf4j;

/**
 * @author briandutremble
 *
 */
@Service
@Slf4j
public class ObservationService {
  
  @Autowired
  private ObservationDao observationDao;
  
  @Transactional(readOnly = false)
  public Observation addObservation(AddObservationRequest observationRequest) {
    log.info("Service: Adding observation {}", observationRequest);
    return observationDao.insertObservation(observationRequest);
  }
}
