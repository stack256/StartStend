package Box;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.allure.annotations.*;

import static Box.About.*;
import static Box.Objects.*;

class Base {
    @Step("Развернуть формы")
    static void forms() {
        drivermap.get(Thread.currentThread().getId()).get(baseUrl + "page/doc-forms-list");
        click("Все","//input[@id='template_x002e_deploy_x002e_doc-forms-list_x0023_default-select-all']");
        click("Развернуть","//button[text()='Развернуть']");
        waitelement("//td[@id='all_deploy']","OK",1200);
    }

    @Step("Развернуть машины состояний")
    static void statemachines() {
        drivermap.get(Thread.currentThread().getId()).get(baseUrl + "page/statemachine");
        click("Все","//input[@id='deploy-control-select-all']");
        click("Развернуть","//button[text()='Развернуть']");
        waitelement("//td[@id='lecm-initiative-on-change-lnd_document_deploy']","OK",1200);
    }

    @Step("Развернуть групповые операции")
    static void groupactions() {
        drivermap.get(Thread.currentThread().getId()).get(baseUrl + "page/group-actions");
        click("Развернуть все","//button[text()='Развернуть всё']");
        waitelement("//div[@id='message']//span[text()='Сведения изменены']",1200);
    }

    @Step("Развернуть АРМ")
    static void arm() {
        drivermap.get(Thread.currentThread().getId()).get(baseUrl + "page/arm-settings");
        click("АРМ ЛНД","//span[text()='АРМ ЛНД']");
        waitelement("//button[text()='Сохранить']",1200);
        click("Публикация АРМ","//button[@title='Публикация АРМ']");
        waitelement("//button[@id='template_x002e_toolbar_x002e_arm-settings_x0023_default-deployForm-form-submit-button']",1200);
        click("ОК","//button[@id='template_x002e_toolbar_x002e_arm-settings_x0023_default-deployForm-form-submit-button']");
        waitelement("//div[@id='message']//span[text()='АРМ успешно опубликован']",1200);
    }

    @Step("Развернуть отчеты")
    static void reports() {
        drivermap.get(Thread.currentThread().getId()).get(baseUrl + "page/reports-editor");
        click("Выбрать все","(//tr//input[@type='checkbox'])[1]");
        click("Следующая","//a[@title='Следующая']");
        waitelement("(//tr//input[@type='checkbox'])[2]",false,1200);
        click("Выбрать все","(//tr//input[@type='checkbox'])[1]");
        click("Следующая","//a[@title='Следующая']");
        waitelement("(//tr//input[@type='checkbox'])[2]",false,1200);
        click("Выбрать все","(//tr//input[@type='checkbox'])[1]");
        click("Следующая","//a[@title='Следующая']");
        waitelement("(//tr//input[@type='checkbox'])[2]",false,1200);
        click("Выбрать все","(//tr//input[@type='checkbox'])[1]");
        click("Следующая","//a[@title='Следующая']");
        waitelement("(//tr//input[@type='checkbox'])[2]",false,1200);
        click("Выбрать все","(//tr//input[@type='checkbox'])[1]");
        click("Следующая","//a[@title='Следующая']");
        waitelement("(//tr//input[@type='checkbox'])[2]",false,1200);
        click("Выбрать все","(//tr//input[@type='checkbox'])[1]");

        click("Действия с выбранными","//button[text()='Действия с выбранными']");
        click("Опубликовать отчет","//a[text()='Опубликовать отчет']");
        click("OK","//div[contains(@style,'visibility: visible')]//button[text()='Ок']");
        waitelement("//div[contains(@style,'visibility: visible')]//*[text()='Результат выполнения операции \"Опубликовать отчет\"']",1200);
        click("OK","//div[contains(@style,'visibility: visible')]//button[text()='Ок']");
    }

    @Step("Авторизоваться пользователем {0}")
    static void auth(String login, String pass) {
        if (current_login != null && current_login != login)
            logout();
        settext("Имя пользователя", AuthPage.username, login);
        settext("Пароль", AuthPage.password, pass);
        String currenturl = drivermap.get(Thread.currentThread().getId()).getCurrentUrl();
        int count = 100;
        click("Войти", AuthPage.login);
        while ((currenturl.equals(drivermap.get(Thread.currentThread().getId()).getCurrentUrl())) && (count >= 0)) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            count--;
        }

        current_login = login;
    }

    @Step("Выйти из системы")
    private static void logout(){
        drivermap.get(Thread.currentThread().getId()).get(drivermap.get(Thread.currentThread().getId()).getCurrentUrl());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String currenturl = drivermap.get(Thread.currentThread().getId()).getCurrentUrl();
        int count = 100;
        click("Меню пользователя",MenuBar.user_menu_popup);
        click("Выйти",MenuBar.user_menu_logout);
        while ((currenturl.equals(drivermap.get(Thread.currentThread().getId()).getCurrentUrl())) && (count >= 0)) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            count--;
        }
    }

    static Boolean waitelement(String xpath, int timeoutlnseconds) {
        waitForLoad();
        try {
            (new WebDriverWait(drivermap.get(Thread.currentThread().getId()), timeoutlnseconds))
                    .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
            (new WebDriverWait(drivermap.get(Thread.currentThread().getId()), timeoutlnseconds))
                    .until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
        } catch (Exception e) {
            softassertfail("Не найден элемент " + xpath);
            return false;
        }
        waitForLoad();
        return true;
    }

    static Boolean waitelement(String xpath, String value, int timeoutlnseconds) {
        waitForLoad();
        try {
            (new WebDriverWait(drivermap.get(Thread.currentThread().getId()), timeoutlnseconds))
                    .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
            (new WebDriverWait(drivermap.get(Thread.currentThread().getId()), timeoutlnseconds))
                    .until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
            (new WebDriverWait(drivermap.get(Thread.currentThread().getId()), timeoutlnseconds))
                    .until(ExpectedConditions.attributeToBe(By.xpath(xpath),"textContent",value));
        } catch (Exception e) {
            softassertfail("Не найден элемент " + xpath);
            return false;
        }
        /*while (!drivermap.get(Thread.currentThread().getId()).findElement(By.xpath(xpath)).getAttribute("textContent").equals("OK")) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/
        waitForLoad();
        return true;
    }

    static Boolean waitelement(String xpath, boolean value, int timeoutlnseconds) {
        waitForLoad();
        try {
            (new WebDriverWait(drivermap.get(Thread.currentThread().getId()), timeoutlnseconds))
                    .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
            (new WebDriverWait(drivermap.get(Thread.currentThread().getId()), timeoutlnseconds))
                    .until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
        } catch (Exception e) {
            softassertfail("Не найден элемент " + xpath);
            return false;
        }
        while (drivermap.get(Thread.currentThread().getId()).findElements(By.xpath(xpath)).isEmpty()){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        while (drivermap.get(Thread.currentThread().getId()).findElement(By.xpath(xpath)).isSelected() != value){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        waitForLoad();
        return true;
    }

    @Step("Заполнить атрибут <{0}> значением <{2}>")
    static void settext(String attrname, String xpath, String text) {
        waitForLoad();
        try {
            (new WebDriverWait(drivermap.get(Thread.currentThread().getId()), timeoutlnseconds))
                    .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
            (new WebDriverWait(drivermap.get(Thread.currentThread().getId()), timeoutlnseconds))
                    .until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
        } catch (Exception e) {
            hardassertfail("Не найден элемент " + xpath);
        }
        drivermap.get(Thread.currentThread().getId()).findElement(By.xpath(xpath)).clear();
        drivermap.get(Thread.currentThread().getId()).findElement(By.xpath(xpath)).sendKeys(text);
        waitForLoad();
    }

    @Step("Нажать кнопку <{0}>")
    static void click(String report, String xpath) {
        waitForLoad();
        try {
            (new WebDriverWait(drivermap.get(Thread.currentThread().getId()), timeoutlnseconds))
                    .until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
        } catch (Exception e) {
            hardassertfail("Не найден элемент " + xpath);
        }
        try {
            (new WebDriverWait(drivermap.get(Thread.currentThread().getId()), timeoutlnseconds))
                    .until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
        } catch (Exception e) {
            hardassertfail("Не кликабелен элемент " + xpath);
        }
        drivermap.get(Thread.currentThread().getId()).findElement(By.xpath(xpath)).click();
        if (report.equals("Следующая")) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        waitForLoad();
    }

}
