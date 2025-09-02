package api;

import java.util.Collection;
import java.util.ArrayList;
import java.util.Date;
import model.*;
import service.CustomerService;
import model.Customer;
import service.ReservationService;

public class HotelResource
{
    private static final CustomerService myCustomerService = CustomerService.getInstance();
    private static final ReservationService myReservationService = ReservationService.getInstance();

    public static Customer getCustomer(String email)
        {return myCustomerService.getCustomer(email);}

    public static void createACustomer(Indentation indentationType)
    {
        String inputEmail = InputReviewer.getValidData(Data.EMAIL, indentationType);
        if(HotelResource.getCustomer(inputEmail) == null)
        {
            String inputFirstName = InputReviewer.getValidData(Data.FIRST_NAME, indentationType);
            String inputLastName = InputReviewer.getValidData(Data.LAST_NAME, indentationType);
            String preComment = "";
            if(indentationType == Indentation.ONE_TAB)                  {preComment = "\t";}
            else if(indentationType == Indentation.TWO_TABS)            {preComment = "\t\t";}
            else if(indentationType == Indentation.THREE_TABS)          {preComment = "\t\t\t";}
            else if(indentationType == Indentation.FOUR_TABS)           {preComment = "\t\t\t\t";}
            System.out.print(preComment + "\tA new Customer is successfully ... ");
            myCustomerService.addCustomer(inputEmail, inputFirstName, inputLastName);
        }

        else if(HotelResource.getCustomer(inputEmail) != null)
        {
            System.out.print(InputReviewer.getPreComment(indentationType) + "\tThis Email exists already .. Please try again\n");
        }
    }

    public static Customer createACustomer()
    {
        Customer newCustomer = null;
        String inputEmail = InputReviewer.getValidData(Data.EMAIL, Indentation.THREE_TABS);
        if(HotelResource.getCustomer(inputEmail) == null)
        {
            String inputFirstName = InputReviewer.getValidData(Data.FIRST_NAME, Indentation.THREE_TABS);
            String inputLastName = InputReviewer.getValidData(Data.LAST_NAME, Indentation.THREE_TABS);
            String preComment = "\t\t\t";
            System.out.print(preComment + "\tA new Customer is successfully ... ");
            newCustomer = new Customer(inputFirstName, inputLastName, inputEmail);
            myCustomerService.addCustomer(newCustomer);
        }

        else if(HotelResource.getCustomer(inputEmail) != null)
        {
            System.out.print(InputReviewer.getPreComment(Indentation.THREE_TABS) + "\tThis Email exists already .. Please try again\n");
        }

        return newCustomer;
    }

    public static IRoom getRoom(String roomNumber)
        {return myReservationService.getARoom(roomNumber);}

    public static Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate)
        {return myReservationService.reserveARoom(getCustomer(customerEmail), room, checkInDate, checkOutDate);}

    public static Collection<Reservation> getCustomersReservations(String customerEmail)
    {
        if(HotelResource.getCustomer(customerEmail) != null)
            {return myReservationService.getCustomersReservation(HotelResource.getCustomer(customerEmail));}

        return null;
    }

    public static Collection<IRoom> findARoom(Date checkIn, Date checkOut)
        {return myReservationService.findRooms(checkIn, checkOut);}

    /*
    public static void addNewCustomer(String email, String firstName, String lastName)          //Test-Function
        {myCustomerService.addCustomer(email, firstName, lastName);}
    */
}