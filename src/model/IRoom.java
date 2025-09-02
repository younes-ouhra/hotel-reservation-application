package model;

public interface IRoom
{
    public String getRoomNumber();
    public Double getRoomPrice();
    public RoomType getRoomType();
    public boolean isFree();
    public void printRoom(Indentation indentation);
    public void printFreeRoom(Indentation indentation);
}