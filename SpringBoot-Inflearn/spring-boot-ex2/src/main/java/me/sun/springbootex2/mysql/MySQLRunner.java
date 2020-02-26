package me.sun.springbootex2.mysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;

/**
 * @author Dongmyeong Lee
 * @since 2020/02/27
 */
@Component
@Profile("mysql")
public class MySQLRunner implements ApplicationRunner {
    @Autowired
    DataSource dataSource;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // DataSourceProperties에서 임베디드 메모리 설정이 되어 있다.
        try (final Connection connection = dataSource.getConnection()) {
            final Class<? extends DataSource> aClass = dataSource.getClass();
            final String driverName = connection.getMetaData().getDriverName();
            final String url = connection.getMetaData().getURL();
            final String userName = connection.getMetaData().getUserName();
            System.out.println("aClass = " + aClass);
            System.out.println("url = " + url);
            System.out.println("userName = " + userName);
            System.out.println("driverName = " + driverName);

            final Statement statement = connection.createStatement();
            final String sql = "CREATE TABLE USER(" +
                                "ID INTEGER NOT NULL," +
                                " name VARCHAR(10)" +
                                ")";
            statement.executeUpdate(sql);
        }

        jdbcTemplate.execute("INSERT INTO USER VALUES (1, 'dongmyeng')");
    }
}
