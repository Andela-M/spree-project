package testframework.enums;

public enum FrameworkSettings {
    BROWSER_TYPE("browserType"),
    BROWSER_MODE("browserMode"),
    DEFAULT_TIMEOUT_SECONDS("defaultTimeoutSeconds"),
    BASE_ESPREE_URL("baseEspreeUrl"),
    STOREFRONT_PATH("storefrontPath"),
    PLATFORM_PATH("platformPath"),
    TOKEN_PATH("tokenPath");

    FrameworkSettings(String propName) {
        value = propName;
    }

    public String getValue() {
        return value;
    }

    final String value;
}
