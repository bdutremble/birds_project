/**
 * 
 */
package com.briandutremble.birds.entity;

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
public class Coloration {

  private int colorationId;
  private int birdId;
  private String beakColor;
  private String headColor;
  private String torsoColor;
  private String wingColor;
  
  
  @Builder
  public Coloration(Integer colorationId, Integer birdId, String beakColor, String headColor, String torsoColor, String wingColor) {
    this.colorationId = colorationId;
    this.birdId = birdId;
    this.beakColor = beakColor;
    this.headColor = headColor;
    this.torsoColor = torsoColor;
    this.wingColor = wingColor;
  }
}
