/**
 * 
 */
package com.briandutremble.birds.entity;

import java.util.Set;
import lombok.Builder;
import lombok.Data;

/**
 * @author briandutremble
 *
 */
@Data
@Builder
public class Food {
  private String foodName;
  private Set<String> foodType;
}
