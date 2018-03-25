package VsyakieShtuki;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.CannotAcquireLockException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.print.attribute.standard.MediaSize;
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
        jdbcTemplate.update("UPDATE Counter SET num = 10");
        System.out.println("value was updated to 10");
        try {
            self.readFromAnotherTransactionCommited();
        } catch (CannotAcquireLockException e){
            System.out.println("READ_COMMITTED transaction fails");
        }
            self.readFromAnotherTransactionUncommited();
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRES_NEW)
    public void readFromAnotherTransactionCommited(){
        int num = jdbcTemplate.queryForObject("SELECT num FROM Counter", Integer.class);
        System.out.println("dirty read " + num);
    }

    @Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW)
    public void readFromAnotherTransactionUncommited(){
        int num = jdbcTemplate.queryForObject("SELECT num FROM Counter", Integer.class);
        System.out.println("dirty read " + num);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRES_NEW)
    public void changeFromAnotherTransaction(){

    }

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    CounterUtils self;
}
