package pl.b2b.net.driver;

public enum BrowserEnum {
    CHROME("webdriver.chrome.driver", "chromedriver.exe"),
    FF("webdriver.gecko.driver", "geckodriver.exe");

    private String systemProperty;
    private String executableFile;

    BrowserEnum(String systemProperty, String executableFile) {
        this.systemProperty = systemProperty;
        this.executableFile = executableFile;
    }

    public String getSystemProperty() {
        return systemProperty;
    }

    public String getExecutableFile() {
        return executableFile;
    }
}