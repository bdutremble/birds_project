/**
 * 
 */
package com.briandutremble.birds.entity;

import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

/**
 * @author briandutremble
 *
 */
@Data
public class AddObservationRequest {
  
  private Integer observationId;
  @NotNull
  private Integer bird_id;
  @NotNull
  private String observationLocation;
  private String observationTime;
  
  
  @Builder
  public AddObservationRequest(Integer observationId, Integer birdId, String observationLocation, String observationTime) {
    this.observationId = observationId;
    this.bird_id = birdId;
    this.observationLocation = observationLocation;
    this.observationTime = observationTime;
  }
}
