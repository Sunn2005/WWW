package fit.iuh.bai05_gk;

import jakarta.ws.rs.ApplicationPath;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/api")
public class RestApplication extends ResourceConfig {

    public RestApplication() {
        packages("fit.iuh.bai05_gk.api");
        register(JacksonFeature.class);
    }
}
