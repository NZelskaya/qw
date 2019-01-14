package pl.b2b.net.b2b.pageobject.actions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import pl.b2b.net.b2b.pageobject.pages.JobOffersPage;
import pl.b2b.testfactory.TestFactoryUtils;

import java.util.ArrayList;
import java.util.List;

import static pl.b2b.net.GlobalDefinitions.B2B_URL;

public class JobOffersPageActions extends JobOffersPage {

    public JobOffersPageActions(WebDriver driver) {
        super(driver);
    }

    public void verifyJobOffersPageOpened() {
        String url = getCurrentUrl();
        Assert.assertEquals(url, B2B_URL + ENDPOINT, "B2B job offers page wasn't opened");
    }

    public void verifyAvailableJobPositions() {
        SoftAssert softAssert = new SoftAssert();
        List<String> availablePositionTypes = new ArrayList<>();
        for (WebElement positionLink : positionTypesLink) {
            availablePositionTypes.add(positionLink.getText().trim());
        }

        List<String> expectedJobPositions = getExpectedJobPositions();

        Assert.assertEquals(availablePositionTypes.size(), expectedJobPositions.size(), "Job positions filters number isn't as expected");

        for (String position : availablePositionTypes) {
            softAssert.assertTrue(expectedJobPositions.contains(position), "Positions isn't expected to be shown : " + position);
        }

        softAssert.assertAll();
    }

    public void openOfferByIndex(int index) {
        List<WebElement> jobOfferDetailsBtn = this.jobOfferDetailsBtn;

        int numberOfJobOffers = jobOfferDetailsBtn.size();

        Assert.assertTrue(numberOfJobOffers > 0, "No job offers found");
        click(jobOfferDetailsBtn.get(index));
        waitForPageLoadComplete();
//        TestFactoryUtils.addOutputData("Found job offers number", String.valueOf(numberOfJobOffers));
    }

    private List<String> getExpectedJobPositions() {
        ArrayList<String> positions = new ArrayList<>();
        positions.add("Wszystkie oferty");
        positions.add("Programista");
        positions.add("Tester");
        positions.add("Inne");

        return positions;
    }
}
