package io.github.raphael_ps.vendas.domain.repository;

import io.github.raphael_ps.vendas.domain.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CustomerRepository {

    private static String INSERT = "insert into customer (name) values (?)";
    private static String SELECT_ALL = "SELECT * FROM CUSTOMER";
    private static String UPDATE_LINE = "UPDATE CUSTOMER SET NAME=(?) WHERE ID=(?)";
    private static String DELETE = "DELETE FROM CUSTOMER WHERE ID = (?)";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Customer save(Customer customer){
        jdbcTemplate.update(INSERT, new Object[]{customer.getName()});
        return customer;
    }
    public int delete(Integer id){
        return jdbcTemplate.update(DELETE, id);
    }

    public int updateCustomer(Customer customer){
        return jdbcTemplate.update(UPDATE_LINE, new Object[]{customer.getName(), customer.getId()});
    }

    public List<Customer> getAllByName(String nameToFind){
        String QUERY_NAME = SELECT_ALL + " WHERE NAME LIKE '%" + nameToFind + "%';";

        return jdbcTemplate.query(QUERY_NAME, (rs, rowNum) ->
                new Customer(rs.getInt("id"), rs.getString("name"))
        );
    }

    public List<Customer> getAll(){
        return jdbcTemplate.query(SELECT_ALL, new RowMapper<Customer>() {
            @Override
            public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
                Integer id = rs.getInt("id");
                String name = rs.getString("name");
                return new Customer(id, name);
            };
        }
        );
    }
}
