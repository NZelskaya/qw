package pl.b2b.net.b2b.pageobject.actions;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import pl.b2b.net.b2b.pageobject.pages.HomePage;

import static pl.b2b.net.GlobalDefinitions.B2B_URL;

public class HomePageActions extends HomePage {
    public HomePageActions(WebDriver driver) {
        super(driver);
    }

    public void openHomePage() {
        navigateToUrl(B2B_URL);
        waitForPageLoadComplete();
    }

    public void verifyHomePageOpened() {
        String url = getCurrentUrl();
        Assert.assertEquals(url, B2B_URL, "B2B home page wasn't opened");
    }

    public void goToJobOffers(){
        click(jobOffersLink);
        waitForPageLoadComplete();
    }

    public void goToContactUs(){
        click(contactUsLink);
        waitForPageLoadComplete();
    }
}
