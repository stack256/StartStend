package Box;

class Objects {
    static class AuthPage {
        static String username = "//input[@name='username']";
        static String password = "//input[@name='password']";
        static String login = "//button[text()='Войти']";
    }

    static class MenuBar{
        static String user_menu_popup = "//*[@id='HEADER_USER_MENU_POPUP_text']";
        static String user_menu_logout = "//*[@id='HEADER_USER_MENU_LOGOUT_text']";
    }

}
