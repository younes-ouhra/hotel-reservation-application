package service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import model.Customer;

public class CustomerService
{
    private static CustomerService instance;
    private static final Set<Customer> customers = new HashSet<>();

    private CustomerService()
    {}

    public static CustomerService getInstance()
    {
        if(instance == null)
            {instance = new CustomerService();}
        return instance;
    }

    public Collection<Customer> getCustomers()
        {return customers;}

    public void addCustomer(String email, String firstName, String lastName)
    {
        customers.add(new Customer(firstName, lastName, email));
        System.out.print("and added ...\n");
    }

    public void addCustomer(Customer newCustomer)
    {
        customers.add(newCustomer);
        System.out.print("and added ...\n");
    }

    public Customer getCustomer(String customerEmail)
    {
        Customer myCustomer = null;
        for(Customer item : customers)
        {
            if((item.getEmail()).equalsIgnoreCase(customerEmail))
                {myCustomer = item;}
        }
        return myCustomer;
    }
}