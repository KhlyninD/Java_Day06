package edu.school21.repositories;

import edu.school21.models.Product;
import edu.school21.numbers.NumberWorker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.sql.Array;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProductsRepositoryJdbcImplTest {
    private ProductsRepositoryJdbcImpl repo;

    final Product EXPECTED_PRODUCT_SAVE = new Product(null, "n5", 500L);
    final Product EXPECTED_PRODUCT_UPDATE = new Product(2L, "n2", 2000L);

    @BeforeEach
    private void resetDBAndGetConnection() throws SQLException {
        Connection dbConnection = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("schema.sql")
                .addScript("data.sql")
                .build().getConnection();
        this.repo = new ProductsRepositoryJdbcImpl(dbConnection);
    }

    @Test
    public void testSave() throws Exception {
        repo.save(EXPECTED_PRODUCT_SAVE);
        Assertions.assertNotNull(EXPECTED_PRODUCT_SAVE.getId());
        Assertions.assertEquals(EXPECTED_PRODUCT_SAVE, repo.findById(EXPECTED_PRODUCT_SAVE.getId()).orElse(null));
    }

    @Test
    public void testUpdate() throws Exception {
        long id = EXPECTED_PRODUCT_UPDATE.getId();
        repo.update(EXPECTED_PRODUCT_UPDATE);
        Product result = repo.findById(id).orElse(null);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(EXPECTED_PRODUCT_UPDATE.getPrice(), result.getPrice());
    }

    @ParameterizedTest
    @ValueSource(longs = {0, 1, 2, 3})
    public void testDelete(long id) throws Exception {
        repo.delete(id);
        Assertions.assertFalse(repo.findById(id).isPresent());
    }

    @ParameterizedTest
    @ValueSource(longs = {0, 1, 2, 3})
    public void testFindByIdTrue(long id) throws Exception {
        Assertions.assertTrue(repo.findById(id).isPresent());
    }

    @ParameterizedTest
    @ValueSource(longs = {99, 99, 15874, 41})
    public void testFindByIdFalse(long id) throws Exception {
        Assertions.assertFalse(repo.findById(id).isPresent());
    }

    @Test
    public void testFindAll() throws Exception {
        long EXPECTED_SIZE = 4;
        Assertions.assertEquals(EXPECTED_SIZE, repo.findAll().size());
    }
}
