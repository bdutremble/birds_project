/**
 * 
 */
package com.briandutremble.birds.entity;


import lombok.Builder;
import lombok.Data;

/**
 * @author briandutremble
 *
 */

@Data

public class Observation {
  
  private Integer observationId;
  private Integer bird_id;
  private String observationLocation;
  private String observationTime;
  
  
  @Builder
  public Observation(Integer observationId, Integer birdId, String observationLocation, String observationTime) {
    this.bird_id = birdId;
    this.observationLocation = observationLocation;
    this.observationTime = observationTime;
  }
}
