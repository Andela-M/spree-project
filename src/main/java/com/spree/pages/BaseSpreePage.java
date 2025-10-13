package com.spree.pages;

import testframework.PropertiesManager;
import testframework.core.BaseWebPage;

public class BaseSpreePage extends BaseWebPage {
    public BaseSpreePage(String pageSpecificUrl) {
        super(pageSpecificUrl);
    }

    @Override
    public String getBasePageUrl() { return PropertiesManager.getConfigProperties().getProperty("baseEspreeUrl"); }
}

