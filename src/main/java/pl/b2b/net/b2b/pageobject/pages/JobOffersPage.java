package pl.b2b.net.b2b.pageobject.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class JobOffersPage extends AbstractPageObject {
    protected final String ENDPOINT = "oferty-pracy/";

    public JobOffersPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "[class*= 'hide_on_mobile'] .term_item_outer  a[href*= '/oferty-pracy/']")
    protected List<WebElement> positionTypesLink;

    @FindBy(css = ".list_trainings .list_training_outer a[class*='red']")
    protected List<WebElement> jobOfferDetailsBtn;
}
