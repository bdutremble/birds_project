/**
 * 
 */
package com.briandutremble.birds.entity;

import java.util.LinkedList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author briandutremble
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddColorationRequest {

  private int colorationId;
  private int birdId;
  private String beakColor;
  private String headColor;
  private String torsoColor;
  private String wingColor;
  
  
  @Builder
  public AddColorationRequest(Integer colorationId, Integer birdId, String beakColor, String headColor, String torsoColor, String wingColor) {
    this.colorationId = colorationId;
    this.beakColor = beakColor;
    this.headColor = headColor;
    this.torsoColor = torsoColor;
    this.wingColor = wingColor;
  }
}
