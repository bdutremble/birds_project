/**
 * 
 */
package com.briandutremble.birds.controller;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.briandutremble.birds.entity.AddObservationRequest;
import com.briandutremble.birds.entity.Observation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.servers.Server;

/**
 * @author briandutremble
 *
 */

@Validated
@RequestMapping("/observations") // Maps to http://localhost:8080/birds
@OpenAPIDefinition(info = @Info(title = "Bird Observations"),
    servers = {@Server(url = "http://localhost:8080", description = "Local server.")})
public interface ObservationController {

  @Operation( // @formatter:off
      summary = "Add a new observation",
      description = "Add a new observation and return the observation with observation ID",
      responses = {
          @ApiResponse(responseCode = "201", description = "Add an observation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Observation.class))),
          @ApiResponse(responseCode = "400", description = "Invalid observation data", content = @Content(mediaType = "application/json")),
          @ApiResponse(responseCode = "409", description = "Duplicate observation", content = @Content(mediaType = "application/json")),
          @ApiResponse(responseCode = "500", description = "An unplanned error occurred", content = @Content(mediaType = "application/json"))
      }
  ) // @formatter:on
  @PostMapping
  @ResponseStatus(code = HttpStatus.OK)
  Observation addObservation(AddObservationRequest observationRequest);
 
}
