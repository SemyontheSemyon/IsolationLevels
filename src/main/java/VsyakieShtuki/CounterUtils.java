package VsyakieShtuki;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.SQLException;

@Component
public class CounterUtils {

    @Transactional
    public void init(){
        try {
            Connection connection = jdbcTemplate.getDataSource().getConnection();
            System.out.println("Isol lvl:"+connection.getTransactionIsolation());
            jdbcTemplate.execute("CREATE TABLE Counter(num INTEGER)");
            jdbcTemplate.update("INSERT INTO Counter VALUES(5)");
        } catch (Exception e) {
            e.printStackTrace();
        }}

    @Transactional
    public void dirtyReads(){

    }

    @Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Pro)
    public void readFromAnotherTransaction(){

    }

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    CounterUtils self;
}
