/**
 * 
 */
package com.briandutremble.birds.entity;

import lombok.Builder;
import lombok.Value;

/**
 * @author briandutremble
 *
 */
@Value
@Builder
public class Habitat {
  private int habitatId;
  private String habitatType;
  private String habitatDescription;
}
