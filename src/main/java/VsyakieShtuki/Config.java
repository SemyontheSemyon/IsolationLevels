package VsyakieShtuki;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@ComponentScan
@EnableTransactionManagement
public class Config {
    public static void main(String[] args) {
        System.getProperties().put("derby.locks.waitTimeout", "1");
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        Config config = context.getBean(Config.class);
        config.run();
    }

    private void run() {
        counterUtils.init();
        counterUtils.dirtyReads();

    }

    @Bean
    public DataSource dataSource(){
        return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.DERBY).build();
    }

    @Bean
    JdbcTemplate getJdbcTemplate(DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }

    @Bean
    PlatformTransactionManager getPlatformTransactionManager(DataSource dataSource){
        return new DataSourceTransactionManager(dataSource);
    }

    @Autowired
    CounterUtils counterUtils;

}
