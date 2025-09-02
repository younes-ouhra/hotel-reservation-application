package model;
import java.util.Date;
import java.util.Calendar;
import java.util.Objects;

public class Reservation
{
    private Customer customer;
    private IRoom room;
    private Date checkInDate;
    private Date checkOutDate;

    public Reservation(Customer customer, IRoom room, Date checkInDate, Date checkOutDate)
    {
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public Customer getCustomer()   {return customer;}
    public IRoom getRoom()          {return room;}
    public Date getCheckInDate()    {return checkInDate;}
    public Date getCheckOutDate()   {return checkOutDate;}

    public boolean isOverlapping(Date checkInDate, Date checkOutDate)
    {
        return (this.checkInDate).before(checkOutDate) && checkInDate.before(this.checkOutDate);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
            {return true;}
        if (o == null || getClass() != o.getClass())
            {return false;}
        Reservation myReservation = (Reservation) o;
        return room.equals(myReservation.room) && checkInDate.equals(myReservation.checkInDate) && checkOutDate.equals(myReservation.checkOutDate);
    }

    @Override
    public int hashCode()
        {return Objects.hash(customer, room, checkInDate, checkOutDate);}


    public void printReservation(Indentation indentationType)
    {
        Calendar calIn = Calendar.getInstance();
        Calendar calOut = Calendar.getInstance();
        calIn.setTime(checkInDate);
        calOut.setTime(checkOutDate);

        String erg = "\n" + InputReviewer.getPreComment(indentationType) + "The reservation of the Room "
                + room.getRoomNumber() + " belongs to the Costumer with the E-Mail "
                + customer.getEmail() + " :\n"
                + InputReviewer.getPreComment(indentationType)
                + "+ Check-In (mm/dd/yyyy) : "
                + (calIn.get(Calendar.MONTH) + 1) + "/"
                + calIn.get(Calendar.DAY_OF_MONTH) + "/"
                + calIn.get(Calendar.YEAR) + "\n"
                + InputReviewer.getPreComment(indentationType)
                + "+ Check-Out (mm/dd/yyyy) : "
                + (calOut.get(Calendar.MONTH) + 1) + "/"
                + calOut.get(Calendar.DAY_OF_MONTH) + "/"
                + calOut.get(Calendar.YEAR) + "\n"
                ;

        System.out.println(erg);
    }

    @Override
    public String toString()
        {return "A reservation!!\n";}
}