package api;

import model.*;
import java.util.Collection;
import service.CustomerService;
import service.ReservationService;

public class AdminResource
{
    private static final CustomerService myCustomerService = CustomerService.getInstance();
    private static final ReservationService myReservationService = ReservationService.getInstance();

    public static Customer getCustomer(String email)
        {return myCustomerService.getCustomer(email);}

    public static void addRoom(Indentation indentationType)
    {
        boolean addingAktiv = true;
        while(addingAktiv)
        {
            String inputRoomNr = InputReviewer.getValidData(Data.ROOM_NUMBER, indentationType);
            if(myReservationService.getARoom(inputRoomNr) == null)
            {
                String inputRoomPrice = InputReviewer.getValidData(Data.ROOM_PRICE, indentationType);
                String inputRoomType = InputReviewer.getValidData(Data.ROOM_TYP, indentationType);
                System.out.print(InputReviewer.getPreComment(indentationType) + "\tA new Room is successfully ");

                if(inputRoomType.equals("1"))
                    {myReservationService.addRoom(new Room(inputRoomNr, Double.parseDouble(inputRoomPrice), RoomType.SINGLE));}

                else if(inputRoomType.equals("2"))
                    {myReservationService.addRoom(new Room(inputRoomNr, Double.parseDouble(inputRoomPrice), RoomType.DOUBLE));}

                addingAktiv = InputReviewer.convertStringToBool(InputReviewer.getValidData(Data.ADDING_ANOTHER_ROOM, indentationType));
            }

            else if(myReservationService.getARoom(inputRoomNr) != null)
            {
                System.out.print(InputReviewer.getPreComment(indentationType) + "\tThis Room Number exists already .. Please try again\n");
            }
        }
    }

    public static Collection<IRoom> getAllRooms()
        {return myReservationService.getRooms();}

    public static Collection<Customer> getAllCustomers()
        {return myCustomerService.getCustomers();}

    public static void displayAllReservations()
        {myReservationService.printAllReservations();}

    /*
    public static void addNewRoom(String roomNumber, Double price, RoomType enumeration)
    {
        myReservationService.addRoom(new Room(roomNumber, price, enumeration));
    }
    */
}