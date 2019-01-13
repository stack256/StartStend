package Box;

import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Title;

import static Box.Base.*;

@Test
public class MainTest extends About {

    @Title("Развернуть формы")
    public void test1() {
        auth("admin",adminpass);
        forms();
    }

    @Title("Развернуть машины состояний")
    public void test2() {
        auth("admin",adminpass);
        statemachines();
    }

    @Title("Развернуть групповые операции")
    public void test3() {
        auth("admin",adminpass);
        groupactions();
    }

    @Title("Развернуть АРМ")
    public void test4() {
        auth("admin",adminpass);
        arm();
    }

    @Title("Развернуть Отчеты")
    public void test5() {
        auth("admin",adminpass);
        reports();
    }
}
