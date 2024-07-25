package io.example.spring_security.user_details_example.member.fixtures;

import jakarta.persistence.EntityManager;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Profile({"mysql-test"})
@Component
public class MemberDataset {
  private final DataSource dataSource;
  private final EntityManager entityManager;
  public MemberDataset(DataSource dataSource, EntityManager entityManager){
    this.dataSource = dataSource;
    this.entityManager = entityManager;
  }

  @Transactional
  public void init(){
    fetchSchemaSql();
    fetchDataSql();
  }

  @Transactional
  public void truncate(){
    entityManager.clear();

    List<String> tables = entityManager
        .createNativeQuery("SHOW TABLES")
        .getResultList();

    tables.stream()
        .forEach(table -> entityManager
            .createNativeQuery(String.format("TRUNCATE TABLE %s", table))
            .executeUpdate()
        );
  }

  @Transactional
  public void fetchSchemaSql(){
    ClassPathResource resource = new ClassPathResource("schema/schema.sql");
    ResourceDatabasePopulator populator = new ResourceDatabasePopulator(resource);
    populator.execute(dataSource);
  }

  @Transactional
  public void fetchDataSql(){
    ClassPathResource resource = new ClassPathResource("dataset/member/data.sql");
    ResourceDatabasePopulator populator = new ResourceDatabasePopulator(resource);
    populator.execute(dataSource);
  }
}
