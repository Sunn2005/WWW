package fit.iuh.bai03_cartmvc.dao;

import fit.iuh.bai03_cartmvc.model.Product;
import fit.iuh.bai03_cartmvc.util.JpaUtil;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class ProductDao {

    public List<Product> findAll(){
        EntityManager em = JpaUtil.getEmf().createEntityManager();

        try {
            String jpql = """
                    select p from Product p
                    """;
            TypedQuery<Product> query = em.createQuery(jpql, Product.class);

            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            em.close();
        }
        return null;
    }

    public Product findById(int id) {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        try {
            return em.find(Product.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return null;
    }
}
