package fit.iuh.bai06_gk.dao;

import fit.iuh.bai06_gk.model.TinTuc;
import fit.iuh.bai06_gk.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class DanhSachTinTucQuanLy extends Generic<TinTuc>{

    public DanhSachTinTucQuanLy( ) {
        super(TinTuc.class);
    }

    public List<TinTuc> findAll(){
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        try{
            String jpql = """
                    select tt from TinTuc tt
                    """;
            TypedQuery<TinTuc> query = em.createQuery(jpql, TinTuc.class);

            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return null;
    }


    public void update(Integer id, TinTuc tinTucMoi){
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        try{
            em.getTransaction().begin();
            TinTuc tinTuc = em.find(TinTuc.class, id);
            if (id != null){
                tinTuc.setTieuDe(tinTucMoi.getTieuDe());
                tinTuc.setNdTT(tinTucMoi.getNdTT());
                tinTuc.setLienKet(tinTucMoi.getLienKet());
                tinTuc.setDanhMuc(tinTucMoi.getDanhMuc());
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }

    }
}
