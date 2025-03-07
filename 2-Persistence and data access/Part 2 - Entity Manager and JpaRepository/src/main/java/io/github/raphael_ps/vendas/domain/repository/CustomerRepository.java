package io.github.raphael_ps.vendas.domain.repository;


import io.github.raphael_ps.vendas.domain.entity.Customer;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class CustomerRepository {

    @Autowired
    private EntityManager entityManager;

    @Transactional
    public Customer update(Customer customer){
        if (customer.getId() == null || entityManager.find(Customer.class, customer.getId()) == null){
            return null;
        }

        entityManager.merge(customer);
        return customer;
    }

    @Transactional
    public Customer delete(Integer id){
        Customer customer = entityManager.find(Customer.class, id);

        if (customer != null){
            entityManager.remove(customer);
        }

        return customer;
    }

    @Transactional
    public Customer save(Customer newCustomer){
        entityManager.persist(newCustomer);
        return newCustomer;
    }

    @Transactional(readOnly = true)
    public List<Customer> getAll(){
        return entityManager
                .createQuery("from Customer", Customer.class).getResultList();
    }

    @Transactional(readOnly = true)
    public List<Customer> findByName(String name){
        return entityManager
                .createQuery("Select c From Customer c WHERE c.name LIKE :name", Customer.class)
                .setParameter("name", "%" + name + "%")
                .getResultList();
    }
};