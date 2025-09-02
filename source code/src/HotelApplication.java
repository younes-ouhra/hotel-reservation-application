import menu.AdminMenu;
import menu.MainMenu;

public class HotelApplication
{
    public static void main(String[] args)
    {
        /*
        HotelResource.addNewCustomer("younes@gmx.de", "Younes", "Ouhra");
        HotelResource.addNewCustomer("mehdi@gmx.de", "Mehdi", "Ouhra");

        AdminResource.addNewRoom("10", 99.99, RoomType.SINGLE);
        AdminResource.addNewRoom("11", 109.99, RoomType.SINGLE);
        AdminResource.addNewRoom("12", 129.99, RoomType.DOUBLE);
        */

        boolean applicationActiv = true;
        while(applicationActiv)
        {
            int mainMenuAnswer = MainMenu.getValidAnswerMainMenu();
            if(mainMenuAnswer == 5)
                {applicationActiv = false;}
            else if(mainMenuAnswer == 4)
            {
                boolean adminMenuAktiv = true;
                while(adminMenuAktiv)
                {
                    int adminMenuAnswer = AdminMenu.getValidAnswerAdminMenu();
                    if(adminMenuAnswer == 5)
                        {adminMenuAktiv = false;}
                }
            }
        }
    }
}