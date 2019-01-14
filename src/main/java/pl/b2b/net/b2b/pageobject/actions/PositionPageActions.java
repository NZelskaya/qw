package pl.b2b.net.b2b.pageobject.actions;

import org.openqa.selenium.WebDriver;

import org.testng.asserts.SoftAssert;
import pl.b2b.net.b2b.pageobject.pages.PositionPage;

public class PositionPageActions extends PositionPage {
    public PositionPageActions(WebDriver driver) {
        super(driver);
    }

    public void verifyOpenedPositionsPage() {
        waitForPageLoadComplete();

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertTrue(weOfferSection.getText().contains("Oferujemy"), "No 'Oferujemy' section");
        softAssert.assertTrue(requirements.getText().contains("Wymagania"), "No 'Wymagania' section");
        softAssert.assertTrue(tasks.getText().contains("Główne zadania"), "No 'Główne zadania' section");
        softAssert.assertTrue(applyBtn.isDisplayed(), "No 'Aplikuj' button displayed");

        softAssert.assertAll();
    }
}
