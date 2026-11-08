package fit.iuh.bai06_gk.dao;

import fit.iuh.bai06_gk.util.JpaUtil;
import jakarta.persistence.EntityManager;

import java.security.spec.ECField;

public class Generic<T>{
    private Class<T> type;

    public Generic(Class<T> type) {
        this.type = type;
    }

    public T findById(Integer id){
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        try {
            return em.find(type, id);
        }catch (Exception e){
            e.printStackTrace();
        } finally {
            em.close();
        }
        return null;
    }

    public void save(T enity){
        EntityManager em = JpaUtil.getEmf().createEntityManager();

        try{
            em.getTransaction().begin();
            em.persist(enity);
            em.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
    public void delete(int id){
        EntityManager em = JpaUtil.getEmf().createEntityManager();

        try{
            em.getTransaction().begin();
            T entity  = em.find(type, id);
            if (entity != null) {
                em.remove(entity);
            }
            em.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}
