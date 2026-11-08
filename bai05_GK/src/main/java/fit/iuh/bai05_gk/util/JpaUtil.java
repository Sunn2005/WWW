package fit.iuh.bai05_gk.util;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lombok.Getter;

public class JpaUtil {
    @Getter
    private static EntityManagerFactory emf;

    public static void init(){
        if (emf == null){
            emf = Persistence.createEntityManagerFactory("default");
        }
    }

    public static void destroy(){
        if (emf != null && emf.isOpen()) {
             emf.close();
        }
    }

}
