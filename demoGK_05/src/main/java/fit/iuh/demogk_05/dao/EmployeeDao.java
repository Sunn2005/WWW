package fit.iuh.demogk_05.dao;

import fit.iuh.demogk_05.model.Employee;
import fit.iuh.demogk_05.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class EmployeeDao extends GenericDao<Employee> {


    public EmployeeDao() {
        super(Employee.class);
    }

    public List<Employee> findAll(){
        EntityManager em = JpaUtil.getEmf().createEntityManager();

        try {
            String jpql = """
                    select e from Employee e
                    """;

            TypedQuery<Employee> query = em.createQuery(jpql, Employee.class);
            return query.getResultList();

        }catch (Exception e){
            e.printStackTrace();
        } finally {
            em.close();
        }
        return null;
    }

    public List<Employee> findByName(String name){
        EntityManager em = JpaUtil.getEmf().createEntityManager();

        try {
            String jpql = """
                    select e from Employee e
                    where e.name like :name
                    """;

            TypedQuery<Employee> query = em.createQuery(jpql, Employee.class);
            query.setParameter("name", "%"+name+"%");
            return query.getResultList();

        }catch (Exception e){
            e.printStackTrace();
        } finally {
            em.close();
        }
        return null;
    }

   public void update(Long id, Employee newEmployee) {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        try {

            em.getTransaction().begin();
            Employee employee = em.find(Employee.class, id);
            if (employee!=null){
                employee.setName(newEmployee.getName());
                employee.setSalary(newEmployee.getSalary());
                employee.setDepartment(newEmployee.getDepartment());
            }
            em.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }

   }
}
