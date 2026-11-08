package fit.iuh.bai5_jdbc;

import jakarta.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/api")
public class RestApplicationConfig extends ResourceConfig {
    public RestApplicationConfig() {
        packages("fit.iuh.bai5_jdbc.resource");
    }
}