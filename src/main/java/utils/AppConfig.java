package utils;

import org.aeonbits.owner.Config;

@Config.Sources({"classpath:app.properties"})
public interface AppConfig extends Config {

    @Key("ui.baseUrl")
    String uiBaseUrl();

    @Key("api.baseUrl")
    String apiBaseUrl();

    @Key("browser")
    String browser();

    @Key("headless")
    Boolean headless();

    @Key("timeoutMs")
    Long timeoutMs();
}

