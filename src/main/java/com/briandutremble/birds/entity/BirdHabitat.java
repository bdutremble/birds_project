/**
 * 
 */
package com.briandutremble.birds.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BirdHabitat {
  private Bird bird;
  private HabitatType habitat;
}
