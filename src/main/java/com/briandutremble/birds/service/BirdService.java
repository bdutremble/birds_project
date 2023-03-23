/**
 * 
 */
package com.briandutremble.birds.service;

import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.briandutremble.birds.dao.BirdDao;
import com.briandutremble.birds.entity.AddBirdRequest;
import com.briandutremble.birds.entity.Bird;
import com.briandutremble.birds.errorhandler.DeleteBirdException;
import lombok.extern.slf4j.Slf4j;


/**
 * @author briandutremble
 *
 */
@Service
@Slf4j
public class BirdService {
  @Autowired
  private BirdDao birdDao;

  @Transactional(readOnly = true)
  public List<Bird> retrieveBirds() {
    log.info("Service: List birds");

    List<Bird> birds = birdDao.getAllBirds();

    birds.forEach(bird -> {
      //bird.getColoration().addAll(birdDao.fetchColoration(bird.getBirdId()));
      bird.getHabitatTypes().addAll(birdDao.getBirdHabitats(bird.getBirdId()));
    });

    return birds;
  }

  @Transactional(readOnly = true)
  public Bird getBird(int birdId) {
    log.info("Service: Get bird with ID={}", birdId);

    Bird bird = birdDao.getBird(birdId)
        .orElseThrow(() -> new NoSuchElementException("Unknown bird with bird ID=" + birdId));

    //bird.getColoration().addAll(birdDao.getColoration(birdId));
    bird.getHabitatTypes().addAll(birdDao.getBirdHabitats(birdId));

    return bird;
  }


//  @Transactional(readOnly = false)
//  public Bird addBird(AddBirdRequest birdRequest) {
//    log.info("Service: Adding bird {}", birdRequest);
//    return birdDao.insertBird(birdRequest);
//  }
//

  @Transactional(readOnly = false)
  public Bird modifyBird(Bird bird) {
    if (!birdDao.modifyBird(bird)) {
      throw new NoSuchElementException("Unknown bird with bird ID=" + bird.getBirdId());
    }

    return bird;
  }

 
  @Transactional(readOnly = false)
  public void deleteBird(int birdId) {
    log.info("Service: Delete bird with ID={}", birdId);

    getBird(birdId);

    if (!birdDao.deleteBird(birdId)) {
      throw new DeleteBirdException("Unable to delete bird with ID=" + birdId);
    }
  }
  @Transactional(readOnly = false)
  public Bird addBird(AddBirdRequest birdRequest) {
    log.info("Service: Adding bird {}", birdRequest);
    return birdDao.insertBird(birdRequest);
  }
}