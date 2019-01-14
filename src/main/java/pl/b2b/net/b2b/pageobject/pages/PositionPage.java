package pl.b2b.net.b2b.pageobject.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PositionPage extends AbstractPageObject {
    public PositionPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = ".we_offer")
    protected WebElement weOfferSection;

    @FindBy(css = ".requirements")
    protected WebElement requirements;

    @FindBy(css = ".tasks")
    protected WebElement tasks;

    @FindBy(css = ".training a[class*='red']")
    protected WebElement applyBtn;
}
