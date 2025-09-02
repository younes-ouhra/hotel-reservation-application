package model;

public class FreeRoom extends Room
{
    public FreeRoom(String roomNumber, RoomType enumeration)
    {
        super(roomNumber, 0.0, enumeration);
    }


    public String toString()
    {
        return "The Room with the number " + roomNumber + " is free\n";
    }

    public void printFreeRoom(Indentation indentation)
    {
        if(enumeration == RoomType.SINGLE)
            {System.out.println(InputReviewer.getPreComment(indentation) + "A Single-Room with the number " + roomNumber);}

        else if(enumeration == RoomType.DOUBLE)
            {System.out.println(InputReviewer.getPreComment(indentation) + "A Double-Room with the number " + roomNumber);}
    }
}