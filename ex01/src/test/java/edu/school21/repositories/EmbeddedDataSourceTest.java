package edu.school21.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

public class EmbeddedDataSourceTest {

    @Test
    @BeforeEach
    public void testDBConnection() throws Exception {
        EmbeddedDatabaseBuilder dataSource = new EmbeddedDatabaseBuilder();
        DataSource ds = dataSource
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("schema.sql")
                .addScript("data.sql")
                .build();
        Assertions.assertNotNull(ds.getConnection());
    }
}
