/**
 * 
 */
package com.briandutremble.birds.dao;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import com.briandutremble.birds.entity.AddBirdRequest;
import com.briandutremble.birds.entity.AddColorationRequest;
import com.briandutremble.birds.entity.Bird;
import com.briandutremble.birds.entity.Coloration;
import com.briandutremble.birds.entity.Habitat;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

/**
 * @author briandutremble
 *
 */

@Component
@Slf4j
@SuppressWarnings({"java:S1192", "java:S125"})
public class BirdDao {


  private static final String HABITAT_TABLE = "habitat";
  private static final String WING_COLOR = "wing_color_id";
  private static final String TORSO_COLOR = "torso_color_id";
  private static final String HEAD_COLOR = "head_color_id";
  private static final String BEAK_COLOR = "beak_color_id";
  private static final String COLORATION_ID = "coloration_id";
  private static final String HABITAT_ID = "habitat_id";
  private static final String HABITAT_TYPE = "habitat_type";
  private static final String BIRD_HABITAT_TABLE = "bird_habitat";
  private static final String FOOD_TABLE = "food";
  private static final String NEST_TYPE_TABLE = "nest_type";
  private static final String BIRD_SIZE_TABLE = "bird_size";
  private static final String COLORATION_TABLE = "coloration";
  private static final String BIRD_COLORATION_TABLE = "bird_coloration";
  private static final String BIRD_TABLE = "bird";
  private static final String COMMON_NAME = "common_name";
  private static final String BIRD_SEX = "bird_sex_id";
  private static final String SCIENTIFIC_NAME = "scientific_name";
  private static final String BIRD_ID = "bird_id";
  private static final String DIET_TABLE = "diet";
  private static final String OBSERVATION_TABLE = "observation";
  
 
  @Autowired
  private NamedParameterJdbcTemplate jdbcTemplate;


  public List<Bird> getAllBirds() {
    log.info("Dao: List birds");

    String sql = """
        SELECT *
        FROM %s 
        ORDER BY %s
        """.formatted(BIRD_TABLE, COMMON_NAME);

    return jdbcTemplate.query(sql, (rs, rowNum) -> // @formatter:off
        Bird.builder()
            .birdId(rs.getInt(BIRD_ID))
            .commonName(rs.getString(COMMON_NAME))
            .scientificName(rs.getString(SCIENTIFIC_NAME))
            .birdSex(rs.getString(BIRD_SEX))
            .build()); // @formatter:on
  }

  public Optional<Bird> getBird(int birdId) {
    log.info("Dao: Get bird with ID={}", birdId);

    String sql = """
        SELECT b.*
        FROM %s b
        WHERE %s = :%s
        """.formatted(BIRD_TABLE, BIRD_ID, BIRD_ID);

    Map<String, Object> params = Map.of(BIRD_ID, birdId);

    Bird bird = jdbcTemplate.query(sql, params, (ResultSet rs) -> {
      if (rs.next()) {
        return Bird
            .builder() // @formatter:off
            .birdId(rs.getInt(BIRD_ID))
            .commonName(rs.getString(COMMON_NAME))
            .scientificName(rs.getString(SCIENTIFIC_NAME))
            .birdSex(rs.getString(BIRD_SEX))
            .build(); // @formatter:on
      }

      return null;
    });

    return Optional.ofNullable(bird);
  }
  
  private void updateBirdHabitat(Integer birdId, List<String> habitatNames) {
    deleteBirdHabitats(birdId);
    insertBirdHabitats(birdId, habitatNames);
  }


  private void deleteBirdHabitats(Integer birdId) {
  
    String sql = """
        DELETE FROM %s
        WHERE %s = :%s
        """.formatted(BIRD_HABITAT_TABLE, BIRD_ID, BIRD_ID);

    Map<String, Object> params = Map.of(BIRD_ID, birdId);
    jdbcTemplate.update(sql, params);
  }
  
  public boolean deleteBird(int birdId) {
    log.info("Dao: Delete bird with ID={}", birdId);

    String sql = """
        DELETE FROM %s
        WHERE %s = :%s
        """.formatted(BIRD_TABLE, BIRD_ID, BIRD_ID);

    Map<String, Object> params = Map.of(BIRD_ID, birdId);

    return jdbcTemplate.update(sql, params) == 1;
  }
  
  private void insertBirdHabitats(int birdId, List<String> habitatNames) {

    getOrCreateHabitats(habitatNames)
        .forEach(habitat -> insertBirdHabitat(birdId, habitat.getHabitatId()));

  }
  
  private void insertBirdHabitat(int birdId, int habitatId) {
 
    String sql = """
        INSERT INTO %s (%s, %s)
        VALUES
        (:%s, :%s)
        """.formatted(BIRD_HABITAT_TABLE, BIRD_ID, HABITAT_ID, BIRD_ID, HABITAT_ID);

    Map<String, Object> params = Map.of(BIRD_ID, birdId, HABITAT_ID, habitatId);
    jdbcTemplate.update(sql, params);
  }
  
  public List<String> getBirdHabitats(int birdId) {
 
    String sql = """
        SELECT h.%s
        FROM %s h
        JOIN %s bh USING (%s)
        WHERE bh.%s = :%s
        ORDER BY h.%s
        """.formatted(HABITAT_TYPE, HABITAT_TABLE, BIRD_HABITAT_TABLE, HABITAT_ID, BIRD_ID,
        BIRD_ID, HABITAT_TYPE);

    Map<String, Object> params = Map.of(BIRD_ID, birdId);
    return jdbcTemplate.query(sql, params, new RowMapper<>() {
      
    @Override
    public String mapRow(ResultSet rs, int rowNum) throws SQLException {
    return rs.getString(HABITAT_TYPE);
    }});
  }
  
  private List<Habitat> getOrCreateHabitats(List<String> habitatNames) {
    List<Habitat> habitats = new LinkedList<>();

    habitatNames.forEach(habitatName -> {
    
      Optional<Habitat> optionalHabitat = getHabitatByName(habitatName);

      if (optionalHabitat.isPresent()) {
        
        habitats.add(optionalHabitat.get());
      } else {
        
        habitats.add(insertHabitat(habitatName));
      }
    });

    return habitats;
  }
  
  private Habitat insertHabitat(String habitatName) {
 
    String sql = """
        INSERT INTO %s
        (%s)
        VALUES
        (:%s)
        """.formatted(HABITAT_TABLE, HABITAT_TYPE, HABITAT_TYPE);

    SqlParameterSource params = new MapSqlParameterSource(Map.of(HABITAT_TYPE, habitatName));
    KeyHolder keyHolder = new GeneratedKeyHolder();

    jdbcTemplate.update(sql, params, keyHolder);

    @SuppressWarnings("java:S2259")
    int habitatId = keyHolder.getKeyAs(BigInteger.class).intValue();

    return Habitat
        .builder() // @formatter:off
          .habitatId(habitatId)
          .habitatType(habitatName)
          .build(); // @formatter:on
  }
  
  private Optional<Habitat> getHabitatByName(String habitatName) {

    String sql = """
        SELECT *
        FROM %s
        WHERE %s = :%s
        """.formatted(HABITAT_TABLE, HABITAT_TYPE, HABITAT_TYPE);

    SqlParameterSource params = new MapSqlParameterSource(Map.of(HABITAT_TYPE, habitatName));

    return Optional.ofNullable(jdbcTemplate.query(sql, params, (ResultSet rs) -> {
      if (rs.next()) {
        return Habitat
            .builder() // @formatter:off
            .habitatId(rs.getInt(HABITAT_ID))
            .habitatType(rs.getString(HABITAT_TYPE))
            .build(); // @formatter:on
      }

      return null;
    }));
  }
  
  public boolean modifyBird(Bird birdRequest) {
  
    String sql = """
        UPDATE %s
        SET %s = :%s, %s = :%s, %s = :%s
        WHERE %s = :%s
        """.formatted(BIRD_TABLE, COMMON_NAME, COMMON_NAME, SCIENTIFIC_NAME, SCIENTIFIC_NAME, BIRD_SEX, BIRD_SEX, BIRD_ID,
        BIRD_ID);

    Map<String, Object> params = Map
        .of( // @formatter:off
        COMMON_NAME, birdRequest.getCommonName(), 
        SCIENTIFIC_NAME, birdRequest.getScientificName(), 
        BIRD_SEX, birdRequest.getBirdSex(),
        BIRD_ID, birdRequest.getBirdId()); // @formatter:on

    boolean updated = jdbcTemplate.update(sql, params) == 1;

    if (updated) {
      
      updateBirdHabitat(birdRequest.getBirdId(), birdRequest.getHabitatTypes());
      updateBirdColoration(birdRequest.getBirdId(), birdRequest.getColoration());
    }

    return updated;
  }
  
  public Bird insertBird(AddBirdRequest birdRequest) {
    log.info("Dao: Adding bird {}", birdRequest);

    String sql = """
        INSERT INTO %s
        (%s, %s)
        VALUES
        (:%s, :%s, :%s)
        """.formatted(BIRD_TABLE, COMMON_NAME, SCIENTIFIC_NAME, COMMON_NAME, SCIENTIFIC_NAME, BIRD_SEX);

    Map<String, Object> paramMap =
        Map.of(COMMON_NAME, birdRequest.getCommonName(), SCIENTIFIC_NAME, birdRequest.getScientificName(),
            BIRD_SEX, birdRequest.getBirdSex());

    SqlParameterSource params = new MapSqlParameterSource(paramMap);
    KeyHolder keyHolder = new GeneratedKeyHolder();

    jdbcTemplate.update(sql, params, keyHolder);

    @SuppressWarnings("java:S2259")
    int birdId = keyHolder.getKeyAs(BigInteger.class).intValue();

    insertBirdHabitats(birdId, birdRequest.getHabitatTypes());

    Bird bird = Bird
        .builder() // @formatter:off
          .birdId(birdId)
          .commonName(birdRequest.getCommonName())
          .scientificName(birdRequest.getScientificName())
          .birdSex(birdRequest.getBirdSex())
          .build();// @formatter:on

    bird.getHabitatTypes().addAll(birdRequest.getHabitatTypes());
    bird.getColoration().addAll(birdRequest.getColoration());

    return bird;
  }
  
  public List<Coloration> getBirdColoration(int birdId) {
    
    String sql = """
        SELECT *
        FROM %s c
        JOIN %s bc USING (%s)
        WHERE bc.%s = :%s
        ORDER BY c.%s
        """.formatted(COLORATION_TABLE, BIRD_COLORATION_TABLE, COLORATION_ID, BIRD_ID,
        BIRD_ID, COLORATION_ID);

    Map<String, Object> params = Map.of(BIRD_ID, birdId);
    return jdbcTemplate.query(sql, params, new RowMapper<>() {
      
    @Override
    public Coloration mapRow(ResultSet rs, int rowNum) throws SQLException {
    Coloration coloration = Coloration
        .builder() //formatter:on
        .birdId(rs.getInt(BIRD_ID))
        .colorationId(rs.getInt(COLORATION_ID))
        .beakColor(rs.getString(BEAK_COLOR))
        .headColor(rs.getString(HEAD_COLOR))
        .torsoColor(rs.getString(TORSO_COLOR))
        .wingColor(rs.getString(WING_COLOR))
        .build();
        //formatter:off
    return coloration;
    }});
  }
  private void updateBirdColoration(Integer birdId, List<Coloration> coloration) {
    deleteBirdColoration(birdId);
    insertBirdColoration(birdId, coloration);
  }


private void insertBirdColoration(Integer birdId, List<Coloration> coloration) {
  
  String sql = """
      INSERT INTO %s (%s, %s)
      VALUES
      (:%s, :%s)
      """.formatted(BIRD_COLORATION_TABLE, BIRD_ID, COLORATION_ID, BIRD_ID, COLORATION_ID);

  Map<String, Object> params = Map.of(BIRD_ID, birdId, COLORATION_ID, coloration.getColorationId());
  jdbcTemplate.update(sql, params);
}

private void deleteBirdColoration(Integer birdId) {
  
  String sql = """
      DELETE FROM %s
      WHERE %s = :%s
      """.formatted(BIRD_COLORATION_TABLE, BIRD_ID, BIRD_ID);

  Map<String, Object> params = Map.of(BIRD_ID, birdId);
  jdbcTemplate.update(sql, params);
}

}
