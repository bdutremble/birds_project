package com.briandutremble.birds.entity;

import java.util.LinkedList;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import lombok.Builder;
import lombok.Data;

@Data
public class AddBirdRequest {
  @NotNull
  @Length(max = 30)
  @Pattern(regexp = "[\\w\\s]*")
  private String commonName;
  
  @Length(max = 80)
  @Pattern(regexp = "[\\w\\s]*")
  private String scientificName;
  
  private BirdSex birdSex;
 
  private LinkedList<String> coloration;
  private LinkedList<String> nestType;
  private LinkedList<String> habitatTypes;
  
  
  @Min(0)
  @Max(300)
  private int wingSpan;
  
  private BirdSize birdSize;

  @Builder
  public AddBirdRequest(String commonName, String scientificName) {
    this.commonName = commonName;
    this.scientificName = scientificName;
    this.coloration = new LinkedList<>();
    this.habitatTypes = new LinkedList<>();
  }

}