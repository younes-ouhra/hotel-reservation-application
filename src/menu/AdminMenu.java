package menu;

import api.AdminResource;
import model.Customer;
import model.IRoom;
import model.Indentation;
import service.CustomerService;

import java.util.Collection;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AdminMenu
{
    private static CustomerService myCustomerService = CustomerService.getInstance();

    public static final String[] adminMenuItems = {
            "\nMenu Admin",
            "----------------------------------------------",
            "1. See all Customers",
            "2. See all Rooms",
            "3. See all Reservations",
            "4. Add a Room",
            "5. Back to Main Menu",
            "----------------------------------------------"};

    public static void showAdminMenu()
    {
        for(String item : adminMenuItems)
            {System.out.println(item);}
        System.out.print("Please select a number for the menu option: ");
    }

    public static int getValidAnswerAdminMenu()
    {
        boolean inputValid = false;
        int adminMenuAnswer = 0;
        try
        {
            AdminMenu.showAdminMenu();
            Scanner adminMenuScanner = new Scanner(System.in);
            adminMenuAnswer = adminMenuScanner.nextInt();

            switch(adminMenuAnswer)
            {
                case 1:             //See all Customers
                    Collection<Customer> myCustomers = AdminResource.getAllCustomers();
                    System.out.println("There is " + myCustomers.size() + " Customer(s):");
                    for(Customer item : myCustomers)
                        {System.out.println(item);}
                    break;

                case 2:             //See all Rooms
                    System.out.println("There is " + (AdminResource.getAllRooms()).size() + " Room(s):");
                    for(IRoom item : AdminResource.getAllRooms())
                        {System.out.println(item);}
                    break;

                case 3:             //See all Reservations
                    AdminResource.displayAllReservations();
                    break;

                case 4:             //Add a Room
                    System.out.println("Adding a Room ...");
                    AdminResource.addRoom(Indentation.ONE_TAB);
                    break;

                case 5:             //Back to Main Menu
                    break;

                default:
                    throw new InputMismatchException();
            }
        }

        catch (InputMismatchException ex)
            {System.out.println("Input is invalid");}
        return adminMenuAnswer;
    }
}