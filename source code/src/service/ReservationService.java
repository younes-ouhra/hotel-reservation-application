package service;

import java.util.*;
import model.*;

public class ReservationService
{
    private final static Set<Reservation> reservations = new HashSet<>();
    private final static Set<IRoom> rooms = new HashSet<>();
    private static ReservationService instance;

    private ReservationService()
    {}

    public static ReservationService getInstance()
    {
        if(instance == null)
            {instance = new ReservationService();}
        return instance;
    }

    public Collection<Reservation> getReservations()
        {return reservations;}

    public Collection<IRoom> getRooms()
        {return rooms;}

    public void addRoom(IRoom room)
    {
        rooms.add(room);
        System.out.print("and added ...\n");
    }

    public IRoom getARoom(String roomID)
    {
        for(IRoom item : rooms)
        {
            if(roomID.equals(item.getRoomNumber()))
                {return item;}
        }
        return null;
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate)
    {
        boolean impossibleReservation = false;
        Reservation temp = new Reservation(customer, room, checkInDate, checkOutDate);

        for(Reservation item : reservations)
        {
            if(item.getRoom().equals(room) && item.getCustomer().equals(customer) && item.isOverlapping(temp.getCheckInDate(), temp.getCheckOutDate()))
            {
                impossibleReservation = true;
                InputReviewer.writeAComment("The Customer with the E-Mail " + item.getCustomer().getEmail() + " has already booked this room !!", Indentation.ONE_TAB);
            }

            else if(item.getRoom().equals(room) && item.isOverlapping(temp.getCheckInDate(), temp.getCheckOutDate()))
            {
                impossibleReservation = true;
                InputReviewer.writeAComment("The Room with the Number " + room.getRoomNumber() + " is already booked !!", Indentation.ONE_TAB);
            }
        }

        if(!impossibleReservation)              {reservations.add(temp);}
        else if(impossibleReservation)          {temp = null;}

        return temp;
    }

    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate)
    {
        Set<IRoom> freeRooms = new HashSet<>();
        Collection<IRoom> roomsToRemove = new LinkedList<IRoom>();
        Calendar calIn = Calendar.getInstance();
        Calendar calOut = Calendar.getInstance();
        calIn.setTime(checkInDate);
        calOut.setTime(checkOutDate);
        String comment = "Suggestion for the Date Range (from "
                + (calIn.get(Calendar.MONTH) + 1) + "/"
                + calIn.get(Calendar.DAY_OF_MONTH) + "/"
                + calIn.get(Calendar.YEAR) + " to "
                + (calOut.get(Calendar.MONTH) + 1) + "/"
                + calOut.get(Calendar.DAY_OF_MONTH) + "/"
                + calOut.get(Calendar.YEAR) + ") :";

        for(IRoom roomItem : rooms)
            {freeRooms.add(new FreeRoom(roomItem.getRoomNumber(), roomItem.getRoomType()));}

            for(IRoom freeRoomItem : freeRooms)
            {
                for(Reservation reservationItem : reservations)
                {
                    if ((reservationItem.getRoom().getRoomNumber()).equals(freeRoomItem.getRoomNumber()))
                    {
                        if (reservationItem.isOverlapping(checkInDate, checkOutDate))
                            {roomsToRemove.add(freeRoomItem);}
                    }
                }
            }

        roomsToRemove.forEach(freeRooms::remove);

        for (IRoom roomItem : rooms)
        {
            for (Reservation reservationItem : reservations)
            {
                if ((reservationItem.getRoom().getRoomNumber()).equals(roomItem.getRoomNumber()))
                {
                    if (!reservationItem.isOverlapping(checkInDate, checkOutDate))
                        {freeRooms.add(new FreeRoom(roomItem.getRoomNumber(), roomItem.getRoomType()));}
                }
            }
        }
        if(!freeRooms.isEmpty())
        {
            System.out.print("\n");
            InputReviewer.writeAComment(comment, Indentation.TWO_TABS);
        }

        if(freeRooms.isEmpty())
        {
            System.out.print("\n");
            InputReviewer.writeAComment("There is no " + comment, Indentation.TWO_TABS);
        }

        return freeRooms;
    }

    public Collection<Reservation> getCustomersReservation(Customer customer)
    {
        ArrayList<Reservation> erg = new ArrayList<>();
        for(Reservation item : reservations)
            {if(item.getCustomer().equals(customer))             {erg.add(item);}}

        return erg;
    }

    public void printAllReservations()
    {
        System.out.println("There is " + reservations.size() + " Reservation(s):");
        for(Reservation item : reservations)
            {item.printReservation(Indentation.ONE_TAB);}
    }
}