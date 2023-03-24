/**
 * 
 */
package com.briandutremble.birds.controller;

import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.briandutremble.birds.entity.AddBirdRequest;
import com.briandutremble.birds.entity.Bird;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Validated
@RequestMapping("/birds") // Maps to http://localhost:8080/birds
@OpenAPIDefinition(info = @Info(title = "Bird Operations"),
    servers = {@Server(url = "http://localhost:8080", description = "Local server.")})
public interface BirdController {

  @Operation( // @formatter:off
      summary = "List all birds",
      description = "List all the birds with scientific names if they exist",
      responses = {
          @ApiResponse(responseCode = "200", description = "Returns a list of all birds", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Bird.class))),
          @ApiResponse(responseCode = "500", description = "An unplanned error occurred", content = @Content(mediaType = "application/json"))
      }
  ) // @formatter:on
  @GetMapping
  @ResponseStatus(code = HttpStatus.OK)
  List<Bird> retrieveBirds();

  @Operation( // @formatter:off
      summary = "Return a specified bird",
      description = "Return a specified bird with all attributes",
      responses = {
          @ApiResponse(responseCode = "200", description = "Returns a bird", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Bird.class))),
          @ApiResponse(responseCode = "400", description = "Invalid bird ID", content = @Content(mediaType = "application/json")),
          @ApiResponse(responseCode = "404", description = "Bird not found", content = @Content(mediaType = "application/json")),
          @ApiResponse(responseCode = "500", description = "An unplanned error occurred", content = @Content(mediaType = "application/json"))
      },
      parameters = {
          @Parameter(
              name = "birdId", 
              allowEmptyValue = false, 
              required = true, 
              description = "The bird ID of the bird to return.", 
              in = ParameterIn.PATH
          )
      }  
  ) // @formatter:on
  @GetMapping("/{birdId}")
  @ResponseStatus(code = HttpStatus.OK)
  Bird getBird(@PathVariable int birdId);


  @Operation( // @formatter:off
      summary = "Add a new bird",
      description = "Add a new bird and return the bird with bird ID",
      responses = {
          @ApiResponse(responseCode = "201", description = "Add a bird", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Bird.class))),
          @ApiResponse(responseCode = "400", description = "Invalid bird data", content = @Content(mediaType = "application/json")),
          @ApiResponse(responseCode = "409", description = "Duplicate bird", content = @Content(mediaType = "application/json")),
          @ApiResponse(responseCode = "500", description = "An unplanned error occurred", content = @Content(mediaType = "application/json"))
      }
  ) // @formatter:on
  @PostMapping
  @ResponseStatus(code = HttpStatus.CREATED)
  Bird addBird(@Valid @RequestBody AddBirdRequest birdRequest);

  
  @Operation( // @formatter:off
      summary = "Modify an existing bird",
      description = "Modify an existing bird and return the bird with bird ID",
      responses = {
          @ApiResponse(responseCode = "200", description = "Modify a bird", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Bird.class))),
          @ApiResponse(responseCode = "400", description = "Invalid bird data", content = @Content(mediaType = "application/json")),
          @ApiResponse(responseCode = "404", description = "Bird not found", content = @Content(mediaType = "application/json")),
          @ApiResponse(responseCode = "500", description = "An unplanned error occurred", content = @Content(mediaType = "application/json"))
      }
  ) // @formatter:on
  @PutMapping
  @ResponseStatus(code = HttpStatus.OK)
  Bird modifyBird(@Valid @RequestBody Bird birdRequest);

 
  @Operation( // @formatter:off
      summary = "Delete an existing bird",
      description = "Delete an existing bird",
      responses = {
          @ApiResponse(responseCode = "200", description = "Delete a bird", content = @Content(mediaType = "application/json")),
          @ApiResponse(responseCode = "400", description = "Invalid bird ID", content = @Content(mediaType = "application/json")),
          @ApiResponse(responseCode = "404", description = "Bird not found", content = @Content(mediaType = "application/json")),
          @ApiResponse(responseCode = "500", description = "An unplanned error occurred", content = @Content(mediaType = "application/json"))
      },
      parameters = {
          @Parameter(
              name = "birdId", 
              allowEmptyValue = false, 
              required = true, 
              description = "The bird ID of the bird to delete.", 
              in = ParameterIn.PATH
          )
      }
  ) // @formatter:on
  @DeleteMapping("/{birdId}")
  @ResponseStatus(code = HttpStatus.OK)
  void deleteBird(@PathVariable int birdId);
}