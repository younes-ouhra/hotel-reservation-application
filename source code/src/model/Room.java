package model;

public class Room implements IRoom
{
    protected String roomNumber;
    protected Double price;
    protected RoomType enumeration;

    public Room(String roomNumber, Double price, RoomType enumeration)
    {
        this.roomNumber = roomNumber;
        this.price = price;
        this.enumeration = enumeration;
        if(price != 0.0)            {System.out.print("created ");}
    }

    public String getRoomNumber()   {return roomNumber;}
    public Double getRoomPrice()    {return price;}
    public RoomType getRoomType()   {return enumeration;}
    public boolean isFree()         {return false;}

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {return true;}
        if (o == null || getClass() != o.getClass())
        {return false;}
        Room myRoom = (Room) o;
        return roomNumber.equals(myRoom.roomNumber);
    }

    @Override
    public int hashCode()
        {return roomNumber.hashCode();}


    @Override
    public String toString()
    {
        String res = "";
        if(enumeration == RoomType.SINGLE)
            {res = "\tA Single-Room with the following informations: \n";}

        else if(enumeration == RoomType.DOUBLE)
            {res = "\tA Double-Room has the following informations: \n";}

        return "\n" + res + "\t- The Room's number: " + roomNumber + "\n" + "\t- The Price: " + price;
    }

    public void printRoom(Indentation indentation)
    {
        if(enumeration == RoomType.SINGLE)
            {System.out.println(InputReviewer.getPreComment(indentation) + "A Single-Room with the following informations:");}

        else if(enumeration == RoomType.DOUBLE)
            {System.out.println(InputReviewer.getPreComment(indentation) + "A Double-Room with the following informations:");}

        System.out.println(InputReviewer.getPreComment(indentation) + "\t- The Room's number: " + roomNumber);
        System.out.println(InputReviewer.getPreComment(indentation) + "\t- The Price: " + price);
    }
    public void printFreeRoom(Indentation indentation)
    {}
}