package ca.jrvs.apps.jdbc;

import com.sun.org.apache.xpath.internal.operations.Or;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCExecutor {
    public static void main(String[] args) {
        DatabaseConnectionManager dcm = new DatabaseConnectionManager("localhost", "hplussport", "postgres", "password");

        try {
            Connection connection = dcm.getConnection();
            //manipulate the customer table
            CustomerDAO customerDAO = new CustomerDAO(connection);
            /* Create Data
            Customer customer = new Customer();
            customer.setFirstName("George");
            customer.setLastName("Washington");
            customer.setEmail("george.washington@wh.gov");
            customer.setPhone("(555) 555-6543");
            customer.setAddress("1234 Main St");
            customer.setCity("Mount Vernon");
            customer.setState("VA");
            customer.setZipCode("22121");

            customerDAO.create(customer);
            */

            /* Read Data
            Customer customer = customerDAO.findById(1000);
            System.out.println(customer.getFirstName() + " " + customer.getLastName());
            */

            /* Update Data
            Customer customer = customerDAO.findById(10000);
            System.out.println(customer.getFirstName() + " " + customer.getLastName() + " " +
                    customer.getEmail());
            customer.setEmail("gwashington@wh.gov");
            customer = customerDAO.update(customer);
            System.out.println(customer.getFirstName() + " " + customer.getLastName() + " " +
                    customer.getEmail());

             */

            //whole CRUD process
            Customer customer = new Customer();
            customer.setFirstName("John");
            customer.setLastName("Adams");
            customer.setEmail("jadams.wh.gov");
            customer.setAddress("1234 Main St");
            customer.setCity("Arlington");
            customer.setState("VA");
            customer.setPhone("(555) 555-9845");
            customer.setZipCode("01234");
            //create a record
            Customer dbCustomer = customerDAO.create(customer);
            System.out.println(dbCustomer);
            //read a record
            dbCustomer = customerDAO.findById(dbCustomer.getId());
            System.out.println(dbCustomer);
            //update
            dbCustomer.setEmail("john.adams@wh.gov");
            dbCustomer = customerDAO.update(dbCustomer);
            System.out.println(dbCustomer);
            //delete
            customerDAO.delete(dbCustomer.getId());

            //manipulate the order table
            OrderDAO orderDAO = new OrderDAO(connection);
            Order order = orderDAO.findById(1000);
            System.out.println(order);

        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
