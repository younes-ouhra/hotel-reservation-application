import menu.AdminMenu;
import menu.MainMenu;

public class HotelApplication
{
    public static void main(String[] args)
    {
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