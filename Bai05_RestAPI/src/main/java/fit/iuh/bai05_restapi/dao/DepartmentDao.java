package fit.iuh.bai05_restapi.dao;

import fit.iuh.bai05_restapi.model.Department;
import fit.iuh.bai05_restapi.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class DepartmentDao extends GenericDao<Department> {
    public DepartmentDao() {
        super(Department.class);
    }

    public List<Department> findAll(){
        EntityManager em = JpaUtil.getEmf().createEntityManager();

        try {
            String jpql = """
                    select d from Department d
                    """;
            TypedQuery<Department> query = em.createQuery(jpql, Department.class);
            return query.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            em.close();
        }
        return null;
    }
    public void update(Long id, Department newDepartment){
        EntityManager em = JpaUtil.getEmf().createEntityManager();

        try {
           em.getTransaction().begin();
           Department department = em.find(Department.class, id);
           if (id != null){
                department.setName(newDepartment.getName());
           }

           em.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            em.close();
        }

    }

    public List<Department> findByName(String name){
        EntityManager em = JpaUtil.getEmf().createEntityManager();

        try {
            String jpql = """
                    select d from Department d
                    where d.name like :name
                    """;
            TypedQuery<Department> query = em.createQuery(jpql, Department.class);
            query.setParameter("name", name);
            return query.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            em.close();
        }
        return null;
    }
}
