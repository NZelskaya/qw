package pl.b2b.net;

import org.testng.annotations.Test;
import pl.b2b.net.b2b.testfactory.BaseTF;
import pl.b2b.net.b2b.testfactory.TestFactorySteps;

public class DemoTest {
    @Test
    public void test() throws Exception {
        BaseTF.setUp("FF");
        TestFactorySteps testFactorySteps = new TestFactorySteps();
        testFactorySteps.setUp();
        testFactorySteps.openHomePage();
        testFactorySteps.setUp();
        testFactorySteps.verifyB2BHomePageOpened();
        testFactorySteps.setUp();
        testFactorySteps.navigateToJobOffers();
        testFactorySteps.setUp();
        testFactorySteps.verifyJobOffersPageOpened();
        testFactorySteps.setUp();
        testFactorySteps.verifyAvailableJobPositions();
        testFactorySteps.setUp();
        testFactorySteps.openJobOfferByIndex(0);
        testFactorySteps.setUp();
        testFactorySteps.verifySelectedOffersPage();
        BaseTF.tearDown();
    }
}
