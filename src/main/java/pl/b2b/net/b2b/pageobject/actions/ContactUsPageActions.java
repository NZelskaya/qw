package pl.b2b.net.b2b.pageobject.actions;

import org.openqa.selenium.WebDriver;
import pl.b2b.net.b2b.pageobject.pages.ContactUsPage;

public class ContactUsPageActions extends ContactUsPage {
    public ContactUsPageActions(WebDriver driver) {
        super(driver);
    }

    public void enterPhoneNumber(String message) throws Exception {
        pasteRobot(phoneField, message, true);
    }
}
