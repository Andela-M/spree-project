package com.spree.api;

import testframework.PropertiesManager;
import testframework.core.BaseApiService;
import testframework.enums.FrameworkSettings;

public class PlatformApi extends BaseApiService {
    public PlatformApi() {
        super(PropertiesManager.getConfigProperties().getProperty(FrameworkSettings.BASE_ESPREE_URL.getValue())
                , PropertiesManager.getConfigProperties().getProperty(FrameworkSettings.PLATFORM_PATH.getValue()));
    }
}
