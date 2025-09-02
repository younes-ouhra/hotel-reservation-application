package model;

import java.util.Date;
import java.time.ZoneId;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.regex.Pattern;

public class InputReviewer
{
    private static final String emailMessage = "Enter Email (formal - name@domain.de): ";
    private static final String firstNameMessage = "Enter first Name: ";
    private static final String lastNameMessage = "Enter last Name: ";
    private static final String roomMessage = "Enter the room's number: ";
    private static final String priceMessage = "Enter the room's price (example 119.99): ";
    private static final String roomTypeMessage = "Enter the room's type (1 for single bed, 2 for double bed): ";
    private static final String checkInDateMessage = "Enter CheckIN Date (mm/dd/yyyy - example 02/01/2020): ";
    private static final String checkOutDateMessage = "Enter CheckOUT Date (mm/dd/yyyy - example 02/01/2020): ";
    private static final String addAnotherRoom = "Would you like to add another room? (y/n): ";
    private static final String bookAroom = "Would you like to book a room? (y/n): ";
    private static final String accountQuestion = "Do you have an account with us? (y/n): ";
    private static final String bookAsuggestion = "Would you like to book one of those suggestions?";

    private static final String emailRegEx = "^[\\w.%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
    private static final String nameRegEx = "^[A-Za-zÄÖÜäöüß\\-']{2,30}$";
    private static final String roomNumberRegEx = "^\\d+$";
    private static final String priceRegEx = "(\\d+)\\.(\\d+)|(\\d+)";
    private static final String roomTypeRegEx = "[12]";
    private static final String dateRegEx = "^(0[1-9]|1[0-2])/(0[1-9]|[12]\\d|3[01])/\\d{4}$";
    private static final String yesOrNoRegEx = "[ynYN]";

    public static String scanData(String regEx, String comment, Indentation indentationType)
    {
        String preComment = getPreComment(indentationType);
        String data = "";
        boolean inputValidated = false;
        System.out.print(preComment + comment);
        Scanner myScanner = new Scanner(System.in);
        while(!inputValidated)
        {
            try
            {
                data = myScanner.nextLine();
                Pattern myPattern = Pattern.compile(regEx);
                if(!myPattern.matcher(data).matches())
                    {throw new IllegalArgumentException();}
                inputValidated = true;
            }

            catch (IllegalArgumentException ex)
                {System.out.print("\t" + preComment + "Input invalid ... \n" + preComment + comment);}
        }
        return data;
    }

    public static String getValidData(Data dataType, Indentation indentationType)
    {
        String validData = "";
        String msg = "";

        if(dataType == Data.EMAIL)
            {return scanData(emailRegEx, emailMessage, indentationType);}

        else if(dataType == Data.FIRST_NAME)
            {return scanData(nameRegEx, firstNameMessage, indentationType);}

        else if(dataType == Data.LAST_NAME)
            {return scanData(nameRegEx, lastNameMessage, indentationType);}

        else if(dataType == Data.ROOM_NUMBER)
            {return scanData(roomNumberRegEx, roomMessage, indentationType);}

        else if(dataType == Data.ROOM_PRICE)
            {return scanData(priceRegEx, priceMessage, indentationType);}

        else if(dataType == Data.ROOM_TYP)
            {return scanData(roomTypeRegEx, roomTypeMessage, indentationType);}

        else if(dataType == Data.CHECKIN_DATE)
            {return scanData(dateRegEx, checkInDateMessage, indentationType);}

        else if(dataType == Data.CHECKOUT_DATE)
            {return scanData(dateRegEx, checkOutDateMessage, indentationType);}

        else if(dataType == Data.ADDING_ANOTHER_ROOM)
            {return scanData(yesOrNoRegEx, addAnotherRoom, indentationType);}

        else if(dataType == Data.BOOKING_A_ROOM)
            {return scanData(yesOrNoRegEx, bookAroom, indentationType);}

        else if(dataType == Data.ACCOUNT_QUESTION)
            {return scanData(yesOrNoRegEx, accountQuestion, indentationType);}

        else if(dataType == Data.BOOK_A_SUGGESTION)
            {return scanData(yesOrNoRegEx, bookAsuggestion, indentationType);}

        return validData;
    }

    public static Date convertStringToDate(String dateStr)
    {
        LocalDate dateLD = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        return Date.from(dateLD.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static boolean convertStringToBool(String myString)
        {return myString.equalsIgnoreCase("y");}

    public static void writeAComment(String comment, Indentation indentationType)
        {System.out.println(getPreComment(indentationType) + comment);}

    public static String getPreComment(Indentation indentationType)
    {
        if(indentationType == Indentation.ONE_TAB)                  {return "\t";}
        else if(indentationType == Indentation.TWO_TABS)            {return "\t\t";}
        else if(indentationType == Indentation.THREE_TABS)          {return  "\t\t\t";}
        else if(indentationType == Indentation.FOUR_TABS)           {return  "\t\t\t\t";}
        return null;
    }

}
