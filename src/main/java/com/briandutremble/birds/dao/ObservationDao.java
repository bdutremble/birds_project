/**
 * 
 */
package com.briandutremble.birds.dao;

import java.math.BigInteger;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import com.briandutremble.birds.entity.AddObservationRequest;
import com.briandutremble.birds.entity.Observation;
import lombok.extern.slf4j.Slf4j;

/**
 * @author briandutremble
 *
 */
@Component
@Slf4j
@SuppressWarnings({"java:S1192", "java:S125"})
public class ObservationDao {
  
  private static final String OBSERVATION_TABLE = "observation";
  private static final String BIRD_OBSERVATION_TABLE = "bird_observation";
  private static final String OBSERVATION_ID = "observation_id";
  private static final String OBSERVATION_LOCATION = "observation_location";
  private static final String OBSERVATION_TIME = "observation_time";
  private static final String BIRD_ID = "bird_id";
  
  @Autowired
  private NamedParameterJdbcTemplate jdbcTemplate;
  
  public Observation insertObservation(AddObservationRequest observationRequest) {
    log.info("Dao: Adding observation {}", observationRequest);

    String sql = """
        INSERT INTO %s
        (%s, %s)
        VALUES
        (:%s, :%s)
        """.formatted(OBSERVATION_TABLE, OBSERVATION_LOCATION, OBSERVATION_TIME, OBSERVATION_LOCATION, OBSERVATION_TIME);

    Map<String, Object> paramMap =
        Map.of(OBSERVATION_LOCATION, observationRequest.getObservationLocation(),
            OBSERVATION_TIME, observationRequest.getObservationTime());

    SqlParameterSource params = new MapSqlParameterSource(paramMap);
    KeyHolder keyHolder = new GeneratedKeyHolder();

    jdbcTemplate.update(sql, params, keyHolder);

    @SuppressWarnings("java:S2259")
    int observationId = keyHolder.getKeyAs(BigInteger.class).intValue();

    insertBirdObservations(observationId, observationRequest.getBird_id());

    Observation observation = Observation
        .builder() // @formatter:off
          .observationId(observationId)
          .observationLocation(observationRequest.getObservationLocation())
          .observationTime(observationRequest.getObservationTime())
          .build();// @formatter:on


    return observation;

  }

  private void insertBirdObservations(int birdId, int observationId) {

    String sql = """
        INSERT INTO %s (%s, %s)
        VALUES
        (:%s, :%s)
        """.formatted(BIRD_OBSERVATION_TABLE, BIRD_ID, OBSERVATION_ID, BIRD_ID, OBSERVATION_ID);

    Map<String, Object> params = Map.of(BIRD_ID, birdId, OBSERVATION_ID, observationId);
    jdbcTemplate.update(sql, params);
  }
}
