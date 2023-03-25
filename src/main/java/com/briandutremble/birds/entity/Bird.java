/**
 * 
 */
package com.briandutremble.birds.entity;
import java.util.LinkedList;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import com.briandutremble.birds.entity.Bird;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bird {
  
  @NotNull
  @Positive
  private Integer birdId;
  
  @NotBlank
  private String commonName;
  
  @NotBlank
  private String scientificName;
  
  @NotBlank
  private String birdSex;
  private List<Coloration> coloration;
  
  @NotNull
  private List<String> habitatTypes;
  
  @Builder
  public Bird(Integer birdId, String commonName, String scientificName, String birdSex) {
    this.birdId = birdId;
    this.commonName = commonName;
    this.scientificName = scientificName;
    this.birdSex = birdSex;
    this.coloration = new LinkedList<>();
    this.habitatTypes = new LinkedList<>();
  }
}
