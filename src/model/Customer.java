package model;

public class Customer
{
    private final String firstName;
    private final String lastName;
    private final String email;


    public Customer(String firstName, String lastName, String email)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        System.out.print("created ");
    }

    public String getEmail()
        {return email;}

    public String getFirstName()
        {return firstName;}

    public String getLastName()
        {return lastName;}

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            {return true;}
        if (o == null || getClass() != o.getClass())
            {return false;}
        Customer myCustomer = (Customer) o;
        return email.equalsIgnoreCase(myCustomer.email);
    }

    @Override
    public int hashCode()
        {return email.hashCode();}

    @Override
    public String toString()
    {
        return "\nThe Customer has the following data:\n" +
                "- Fullname: " + firstName + " " + lastName + "\n" +
                "- Email: " + email;
    }
}