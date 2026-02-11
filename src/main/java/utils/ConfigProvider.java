package utils;

import org.aeonbits.owner.ConfigFactory;

public class ConfigProvider {
    public static final AppConfig config =
            ConfigFactory.create(AppConfig.class, System.getProperties());
}

