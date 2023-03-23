/**
 * 
 */
package com.briandutremble.birds.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Diet {
  private Bird bird;
  private Food foodNames;
}
