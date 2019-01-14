package pl.b2b.net.b2b.testfactory;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pl.b2b.net.b2b.pageobject.actions.HomePageActions;
import pl.b2b.net.b2b.pageobject.actions.JobOffersPageActions;
import pl.b2b.net.b2b.pageobject.actions.PositionPageActions;

import pl.b2b.net.driver.DriverSetup;
import pl.b2b.testfactory.TestFactoryUtils;
import pl.b2b.testfactory.annotations.TestFactoryMethod;

public class TestFactorySteps extends BaseTF {
    private HomePageActions homePage;
    private JobOffersPageActions jobOffersPage;
    private PositionPageActions positionPage;

    @BeforeMethod
    public void setUp() {
        WebDriver driver = DriverSetup.getDriver();
        homePage = new HomePageActions(driver);
        jobOffersPage = new JobOffersPageActions(driver);
        positionPage = new PositionPageActions(driver);
    }

    @Test
    @TestFactoryMethod(value = "Open B2B home page", description = "Open B2B Home page and wait till page load ends", group = "Home page")
    public void openHomePage() {
        homePage.openHomePage();
    }

    @Test
    @TestFactoryMethod(value = "Verify B2B home page opened", description = "Verify that opened page is a B2B home page URL", group = "Home page")
    public void verifyB2BHomePageOpened() {
        homePage.verifyHomePageOpened();
    }

    @Test
    @TestFactoryMethod(value = "Navigate to Job Offers", description = "Click at 'Oferty pracy' and wait till page load ends", group = "Home page")
    public void navigateToJobOffers() {
        homePage.goToJobOffers();
    }

    @Test
    @TestFactoryMethod(value = "Verify job offers page opened", description = "Verify that job offers page opened", group = "Job offers page")
    public void verifyJobOffersPageOpened() {
        jobOffersPage.verifyJobOffersPageOpened();
    }

    @Test
    @TestFactoryMethod(value = "Verify available job position filters", description = "Verify filters are - Wszystkie oferty, Programista, Tester, Inne", group = "Job offers page")
    public void verifyAvailableJobPositions() {
        jobOffersPage.verifyAvailableJobPositions();
    }

    @Test
    @TestFactoryMethod(value = "Open job offer by index on page", description = "Open job offer by index on page - starts from 0", group = "Job offers page")
    @Parameters({"offersIndex"})
    public void openJobOfferByIndex(int offersIndex) {
        jobOffersPage.openOfferByIndex(offersIndex);
    }

    @Test
    @TestFactoryMethod(value = "Verify opened position page", description = "Verify page contains sections: Główne zadania, Wymagania, Oferujemy sections and Aplikuj button", group = "Position page")
    public void verifySelectedOffersPage() {
        positionPage.verifyOpenedPositionsPage();
    }
}
