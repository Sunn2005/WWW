package fit.iuh.bai6_restapi.dao;

import fit.iuh.bai6_restapi.util.JpaUtil;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;

public class Generic<T>{
    Class<T> type;

    public Generic(Class<T> type) {
        this.type = type;
    }

    public void create(T entity){
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(entity);
            em.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            em.close();
        }

    }

    public T findById(Long id){
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        try{
            return em.find(type, id);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            em.close();
        }
        return null;
    }

    public void delete(Long id){
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        try{
            em.getTransaction().begin();
            T entity = em.find(type, id);
            if (id != null){
                em.remove(entity);
            }
            em.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            em.close();
        }

    }
}
