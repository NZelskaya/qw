package pl.b2b.net.b2b.pageobject.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends AbstractPageObject {

    public HomePage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = "#menu-menu-header a[href*='oferty-pracy']")
    protected WebElement jobOffersLink;

    @FindBy(css = "#menu-menu-header a[href*='kontakt']")
    protected WebElement contactUsLink;
}
