package fit.iuh.demogk_05.dao;

import fit.iuh.demogk_05.util.JpaUtil;
import jakarta.persistence.EntityManager;

public class GenericDao<T> {

    Class<T> type;

    public GenericDao(Class<T> type) {
        this.type = type;
    }

    public void save(T type){
        EntityManager em = JpaUtil.getEmf().createEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(type);
            em.getTransaction().commit();


        }catch (Exception e){
            e.printStackTrace();
        }finally {
            em.close();
        }
    }

    public void delete(Long id){
        EntityManager em = JpaUtil.getEmf().createEntityManager();

        try {
            em.getTransaction().begin();
            T entity = em.find(type, id);
            if (entity != null) em.remove(entity);
            em.getTransaction().commit();


        }catch (Exception e){
            e.printStackTrace();
        }finally {
            em.close();
        }
    }

    public T findById(Long id){
        EntityManager em = JpaUtil.getEmf().createEntityManager();

        try {

           return em.find(type, id);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return null;
    }
}
