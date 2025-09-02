package menu;

import api.AdminResource;
import api.HotelResource;
import model.*;
import java.util.Collection;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Date;
import java.util.Calendar;

public class MainMenu
{
    public static final String[] mainMenuItems = {
            "\nWelcome to the Hotel Reservation Application",
            "----------------------------------------------",
            "1. Find and reserve a room",
            "2. See my reservations",
            "3. Create an account",
            "4. Admin",
            "5. Exit",
            "----------------------------------------------"};

    public static void showMainMenu()
    {
        for(String item : mainMenuItems)
            {System.out.println(item);}
        System.out.print("Please select a number for the menu option: ");
    }

    public static Date getSevenDaysAfterThisDate(Date myDate)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(myDate);
        cal.add(Calendar.DAY_OF_MONTH, 7);
        return cal.getTime();
    }

    public static Date getCheckOutDate(Date checkInDate)
    {
        Date result = InputReviewer.convertStringToDate(InputReviewer.getValidData(Data.CHECKOUT_DATE, Indentation.ONE_TAB));

        if(result.before(checkInDate) || result.equals(checkInDate))
        {
            InputReviewer.writeAComment("This CheckOUT Date is before the CheckIN Date !!", Indentation.TWO_TABS);
            boolean askingForCheckOutDate = true;
            while(askingForCheckOutDate)
            {
                result = InputReviewer.convertStringToDate(InputReviewer.getValidData(Data.CHECKOUT_DATE, Indentation.ONE_TAB));
                if(result.after(checkInDate))           {askingForCheckOutDate = false;}
                else if(result.before(checkInDate))     {InputReviewer.writeAComment("This CheckOUT Date is before the CheckIN Date !!", Indentation.TWO_TABS);}
            }
        }
        return result;
    }

    public static IRoom getSavedRoom(Data dataType)
    {
        IRoom tempRoom = null;
        if(dataType == Data.ROOM_NUMBER)
        {
            String inputData = InputReviewer.getValidData(dataType, Indentation.ONE_TAB);
            tempRoom = HotelResource.getRoom(inputData);

            if(tempRoom == null)
            {
                boolean askingForData = true;
                while(askingForData)
                {
                    InputReviewer.writeAComment("There is no Room with this Number", Indentation.TWO_TABS);
                    tempRoom = HotelResource.getRoom(InputReviewer.getValidData(Data.ROOM_NUMBER, Indentation.ONE_TAB));

                    if(tempRoom != null)            {askingForData = false;}
                }
            }
        }
        return tempRoom;
    }

    public static Customer getRegistratedCustomer(Data dataType)
    {
        Customer tempCustomer = null;
        if(dataType == Data.EMAIL)
        {
            String inputData = InputReviewer.getValidData(dataType, Indentation.TWO_TABS);
            tempCustomer = HotelResource.getCustomer(inputData);

            if(tempCustomer == null)
            {
                boolean askingForData = true;
                while (askingForData)
                {
                    InputReviewer.writeAComment("There is no Customer with this E-Mail", Indentation.THREE_TABS);
                    boolean havingAnAccount = InputReviewer.convertStringToBool(InputReviewer.getValidData(Data.ACCOUNT_QUESTION, Indentation.TWO_TABS));
                    if (havingAnAccount)
                    {
                        inputData = InputReviewer.getValidData(dataType, Indentation.TWO_TABS);
                        tempCustomer = HotelResource.getCustomer(inputData);

                        if (tempCustomer != null)           {askingForData = false;}
                    }
                    else if(!havingAnAccount)           {askingForData = false;}
                }
            }
        }
        return tempCustomer;
    }

    public static Reservation findAndReserveARoom(Date checkInDate, Date checkOutDate)
    {
        Reservation myReservation = null;

        System.out.println("\nThere is " + (AdminResource.getAllRooms()).size() + " Room(s):");
        for (IRoom item : AdminResource.getAllRooms())
            {System.out.println(item);}

        System.out.print("\n");

        if (InputReviewer.convertStringToBool(InputReviewer.getValidData(Data.BOOKING_A_ROOM, Indentation.ONE_TAB)))
        {
            Customer myCustomer = null;
            IRoom myRoom = null;
            boolean havingAnAccount = InputReviewer.convertStringToBool(InputReviewer.getValidData(Data.ACCOUNT_QUESTION, Indentation.ONE_TAB));
            if(havingAnAccount)
            {
                myCustomer = getRegistratedCustomer(Data.EMAIL);
                if(myCustomer == null)
                    {myCustomer = HotelResource.createACustomer();}

                else if(myCustomer != null)
                    {InputReviewer.writeAComment("Customer found", Indentation.THREE_TABS);}
            }

            else if(!havingAnAccount)
                {myCustomer = HotelResource.createACustomer();}

            String inputEmail = myCustomer.getEmail();
            myRoom = getSavedRoom(Data.ROOM_NUMBER);

            if(myRoom != null)
            {
                InputReviewer.writeAComment("Room found\n", Indentation.THREE_TABS);
                myReservation = (HotelResource.bookARoom(inputEmail, myRoom, checkInDate, checkOutDate));
                if(myReservation != null)
                {
                    InputReviewer.writeAComment("This Reservation was successful", Indentation.ONE_TAB);
                    myReservation.printReservation(Indentation.ONE_TAB);
                }
            }
        }
        return myReservation;
    }

    public static void getSuggestions(Date checkInDate, Date checkOutDate)
    {
        Date searchedCheckInDate = getSevenDaysAfterThisDate(checkInDate);
        Date searchedCheckOutDate = getSevenDaysAfterThisDate(checkOutDate);

        Collection <IRoom> suggestedRooms = HotelResource.findARoom(checkInDate, checkOutDate);
        if(!suggestedRooms.isEmpty())
        {
            for(IRoom suggestion : suggestedRooms)
                {suggestion.printFreeRoom(Indentation.THREE_TABS);}
        }

        suggestedRooms.clear();

        suggestedRooms = HotelResource.findARoom(searchedCheckInDate, searchedCheckOutDate);
        if(!suggestedRooms.isEmpty())
        {
            for(IRoom suggestion : suggestedRooms)
                {suggestion.printFreeRoom(Indentation.THREE_TABS);}
            System.out.print("\n");
        }
    }

    public static int getValidAnswerMainMenu()
    {
        boolean inputValid = false;
        int mainMenuAnswer = 0;

        try
        {
            MainMenu.showMainMenu();
            Scanner mainMenuScanner = new Scanner(System.in);
            mainMenuAnswer = mainMenuScanner.nextInt();

            switch(mainMenuAnswer)
            {
                case 1:         //Find and reserve a room
                    System.out.println("Creating and reserving a room ...");

                    if(AdminResource.getAllRooms().isEmpty())
                    {System.out.println("\nThere is no Rooms to book :(");}

                    else if(!(AdminResource.getAllRooms()).isEmpty())
                    {
                        Date inputCheckInDate = InputReviewer.convertStringToDate(InputReviewer.getValidData(Data.CHECKIN_DATE, Indentation.ONE_TAB));
                        Date inputCheckOutDate = getCheckOutDate(inputCheckInDate);

                        Reservation myReservation = MainMenu.findAndReserveARoom(inputCheckInDate, inputCheckOutDate);

                        if(myReservation != null)
                            {}

                        if(myReservation == null)
                        {
                            getSuggestions(inputCheckInDate, inputCheckOutDate);
                            boolean bookSuggestion = InputReviewer.convertStringToBool(InputReviewer.getValidData(Data.BOOKING_A_ROOM, Indentation.ONE_TAB));
                            if(bookSuggestion)
                            {
                                inputCheckInDate = InputReviewer.convertStringToDate(InputReviewer.getValidData(Data.CHECKIN_DATE, Indentation.ONE_TAB));
                                inputCheckOutDate = getCheckOutDate(inputCheckInDate);
                                myReservation = MainMenu.findAndReserveARoom(inputCheckInDate, inputCheckOutDate);
                                while(myReservation == null && bookSuggestion)
                                {
                                    getSuggestions(inputCheckInDate, inputCheckOutDate);
                                    bookSuggestion = InputReviewer.convertStringToBool(InputReviewer.getValidData(Data.BOOKING_A_ROOM, Indentation.ONE_TAB));
                                    if(bookSuggestion)
                                    {
                                        inputCheckInDate = InputReviewer.convertStringToDate(InputReviewer.getValidData(Data.CHECKIN_DATE, Indentation.ONE_TAB));
                                        inputCheckOutDate = getCheckOutDate(inputCheckInDate);
                                        myReservation = MainMenu.findAndReserveARoom(inputCheckInDate, inputCheckOutDate);
                                    }
                                }
                            }
                        }
                    }
                    break;

                case 2:             //See my reservations
                    System.out.println("Seeing Customer's Reservations ...");
                    String inputEmail = InputReviewer.getValidData(Data.EMAIL, Indentation.ONE_TAB);
                    if(HotelResource.getCustomer(inputEmail) != null)
                    {
                        Collection<Reservation> myReservations = HotelResource.getCustomersReservations(inputEmail);

                        if(myReservations.isEmpty())
                            {InputReviewer.writeAComment("This Customer has no Reservations", Indentation.TWO_TABS);}

                        else if(!myReservations.isEmpty())
                        {
                            InputReviewer.writeAComment("This Customer has " + myReservations.size() + " Reservations", Indentation.TWO_TABS);
                            for(Reservation item : myReservations)
                            {item.printReservation(Indentation.ONE_TAB);}
                        }
                    }
                    else if(HotelResource.getCustomer(inputEmail) == null)
                        {InputReviewer.writeAComment("There is no Customer with this E-Mail", Indentation.TWO_TABS);}

                    break;

                case 3:             //Create an Account
                    System.out.println("Creating a Customer's Account ...");
                    HotelResource.createACustomer(Indentation.ONE_TAB);
                    break;

                case 4:             //Admin Menu
                    break;

                case 5:
                    System.out.println("The Application is closed!!");
                    break;

                default:
                    throw new InputMismatchException();
            }
        }

        catch (InputMismatchException ex)
            {System.out.println("Input is invalid");}

        return mainMenuAnswer;
    }
}