package fit.iuh.bai04_bookstoremvc.dao;

import fit.iuh.bai04_bookstoremvc.model.Book;
import fit.iuh.bai04_bookstoremvc.util.JpaUtil;
import jakarta.persistence.EntityManager;

import java.util.List;

public class BookDao {

    public List<Book> findAll() {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        try {
            return em.createQuery("select b from Book b order by b.id", Book.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Book> search(String keyword) {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        try {
            return em.createQuery(
                            "select b from Book b where lower(b.tittle) like :kw or lower(b.author) like :kw order by b.id",
                            Book.class)
                    .setParameter("kw", "%" + keyword.toLowerCase() + "%")
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public Book findById(int id) {
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        try {
            return em.find(Book.class, id);
        } finally {
            em.close();
        }
    }
}
