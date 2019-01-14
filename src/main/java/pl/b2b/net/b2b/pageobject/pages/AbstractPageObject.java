package pl.b2b.net.b2b.pageobject.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import pl.b2b.net.GlobalDefinitions;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class AbstractPageObject {
    private static String logoCss = "img[src*='b2blogo.svg']";

    public void setLogoId(String logoCss) {
        this.logoCss = logoCss;
    }

    protected WebElement getLogo() {
        Assert.assertNotNull(logoCss, "Logo CSS selector should be specified");
        return driver.findElement(By.cssSelector(logoCss));
    }

    private static int[] ints = {KeyEvent.VK_0, KeyEvent.VK_1, KeyEvent.VK_2, KeyEvent.VK_3, KeyEvent.VK_4, KeyEvent.VK_5,
            KeyEvent.VK_6, KeyEvent.VK_7, KeyEvent.VK_8, KeyEvent.VK_9};

    private WebDriverWait wait;
    protected WebDriver driver;

    protected WebElement getParentElement(WebElement child) {
        return (WebElement) ((JavascriptExecutor) driver).executeScript("return arguments[0].parentNode;", child);
    }

    public AbstractPageObject(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(this.driver, GlobalDefinitions.DRIVER_EXPLICIT_TIMEOUT);
    }

    protected String getCurrentUrl() {
        waitForPageLoadComplete();
        return driver.getCurrentUrl();
    }

    protected void waitForPageLoadComplete() {
        wait.until(driver1 -> String
                .valueOf(((JavascriptExecutor) driver1).executeScript("return document.readyState"))
                .equals("complete"));
    }

    protected void waitForPageLoadComplete(String urlRegex) {
        wait.until(driver1 -> String
                .valueOf(((JavascriptExecutor) driver1).executeScript("return document.readyState"))
                .equals("complete"));

        waitForElementToBeVisible(getLogo());

        wait.until(ExpectedConditions.urlMatches(urlRegex));
    }

    protected void waitForPageLoadCompleteWithoutLogo(String urlRegex) {
        wait.until(driver1 -> String
                .valueOf(((JavascriptExecutor) driver1).executeScript("return document.readyState"))
                .equals("complete"));

        wait.until(ExpectedConditions.urlMatches(urlRegex));
    }

    protected void waitForElementToBePresent(By locator) {
        wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    protected void waitForElementToBeVisible(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    protected void waitForElementToBeInvisible(WebElement element) {
        wait.until(ExpectedConditions.invisibilityOf(element));
    }

    protected void waitForElementToDisappear(By element) {
        wait.until(ExpectedConditions.not(ExpectedConditions.presenceOfElementLocated(element)));
    }

    public boolean isElementPresent(WebElement element) {
        try {
            element.getLocation();
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    protected void click(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.click();
    }

    protected void jsClick(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", element);
    }

    protected void checkboxClickBySpace(WebElement element, boolean check, boolean verifyIfChecked, boolean classContainsChecked) {
        boolean isCheckboxSelected = isCheckboxSelected(element, classContainsChecked);

        if (check) {
            if (verifyIfChecked)
                Assert.assertFalse(isCheckboxSelected, "Checkbox is already selected");
            element.sendKeys(Keys.SPACE);
        } else {
            if (verifyIfChecked)
                Assert.assertTrue(isCheckboxSelected, "Checkbox is already unselected");
            element.sendKeys(Keys.SPACE);
        }
        if (verifyIfChecked)
            Assert.assertEquals(isCheckboxSelected(element, classContainsChecked), check, "Checkbox isn't in expected condition");
    }


    protected void checkboxClick(WebElement element, boolean check, boolean classContainsChecked) {
        boolean isCheckboxSelected = isCheckboxSelected(element, classContainsChecked);

        if (check) {
            Assert.assertFalse(isCheckboxSelected, "Checkbox is already selected");
            click(element);
        } else {
            Assert.assertTrue(isCheckboxSelected, "Checkbox is already unselected");
            click(element);
        }

        isCheckboxSelected = isCheckboxSelected(element, classContainsChecked);
        Assert.assertEquals(isCheckboxSelected, check, String.format("Is checkbox checked = %s, but should be %s", isCheckboxSelected, check));
    }

    protected boolean isCheckboxSelected(WebElement element, boolean classContainsChecked) {
        if (classContainsChecked)
            return element.getAttribute("class").contains("checked");
        return element.getAttribute("class").contains("ng-not-empty");
    }

    protected void typeEnterRobot() throws AWTException {
        Robot robot = new Robot();
        robot.delay(1000);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.delay(1000);
    }

    protected void type(WebElement element, String text) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.click();
        element.clear();
        element.sendKeys(text);
        Assert.assertEquals(getElementsValue(element), text, "Text wasn't printed");
    }

    protected void dataRobot(WebElement element) throws AWTException {
        int ms = 300;
        wait.until(ExpectedConditions.visibilityOf(element));
        element.click();

        Robot robot = new Robot();

        robot.keyPress(KeyEvent.VK_2);
        robot.delay(ms);
        robot.keyPress(KeyEvent.VK_0);
        robot.delay(ms);
        robot.keyPress(KeyEvent.VK_2);
        robot.delay(ms);
        robot.keyPress(KeyEvent.VK_4);
        robot.delay(ms);
        robot.keyPress(KeyEvent.VK_0);
        robot.delay(ms);
        robot.keyPress(KeyEvent.VK_8);
        robot.delay(ms);
        robot.keyPress(KeyEvent.VK_2);
        robot.delay(ms);
        robot.keyPress(KeyEvent.VK_3);
        robot.delay(ms);
    }

    protected void typeIntRobot(WebElement element, int n) throws AWTException {
        Robot robot = new Robot();
        wait.until(ExpectedConditions.visibilityOf(element));
        element.click();
        for (int i = 0; i < n; i++) {
            int number = ints[new Random().nextInt(ints.length)];
            robot.keyPress(number);
            robot.delay(300);
        }

    }

    protected void clearField(WebElement element) throws AWTException {
        Robot robot = new Robot();

        wait.until(ExpectedConditions.visibilityOf(element));
        element.click();

        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_A);
        robot.keyRelease(KeyEvent.VK_A);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_DELETE);
        robot.keyRelease(KeyEvent.VK_DELETE);

        Assert.assertTrue(getElementsValue(element).isEmpty(), "Element wasn't cleaned up");
    }

    protected void pasteRobot(WebElement element, String text, boolean verifyEnteredData) throws AWTException {
        Robot robot = new Robot();

        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.click();

        StringSelection stringSelection = new StringSelection(text);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, stringSelection);

        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);

        if (verifyEnteredData)
            Assert.assertEquals(getElementsValue(element), text, "Text wasn't printed");
    }

    protected void selectFromList(WebElement element, int IdType) {
        Select dropdown = new Select(element);
        dropdown.selectByIndex(IdType);
    }

    public int getNumberOrOpenedTabs() {
        return new ArrayList<>(driver.getWindowHandles()).size();
    }

    public void openAndSwitchToANewTab() {
        ((JavascriptExecutor) driver).executeScript("window.open()");

        int numberOrOpenedTabs = getNumberOrOpenedTabs();
        switchToTheTab(numberOrOpenedTabs - 1);
    }

    public void switchToTheTab(int tabIndex) {
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(tabIndex));
    }

    public void navigateToUrl(String url) {
        driver.navigate().to(url);
    }

    protected void selectByOption(WebElement element, String valueToSelect) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
        Select dropDown = new Select(element);

        try {
            String selectedValue = getSelectedOptionText(dropDown);
            if (!selectedValue.equals(valueToSelect)) {
                selectByOption(valueToSelect, dropDown);
            }
        } catch (NoSuchElementException e) {
            selectByOption(valueToSelect, dropDown);
            return;
        }

        Assert.assertEquals(getSelectedOptionText(dropDown), valueToSelect, "Value wasn't selected from drop-down");
    }

    private String getSelectedOptionText(Select dropDown) {
        return dropDown.getFirstSelectedOption().getText();
    }

    private void selectByOption(String valueToSelect, Select dropDown) {
        List<WebElement> options = dropDown.getOptions();
        for (WebElement option : options) {
            if (option.getText().equals(valueToSelect))
                option.click();
        }
    }

    private String getElementsValue(WebElement element) {
        return element.getAttribute("value");
    }

    protected void waitForElementToBeDisable(WebElement element, String attribute, String value) {
        wait.until(ExpectedConditions.attributeContains(element, attribute, value));
    }

    protected void waitForElementToBeEnable(WebElement element, String attribute, String value) {
        wait.until(ExpectedConditions.not(ExpectedConditions.attributeContains(element, attribute, value)));
    }
}