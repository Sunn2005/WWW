package fit.iuh.bai6_restapi.dao;

import fit.iuh.bai6_restapi.model.DanhMuc;
import fit.iuh.bai6_restapi.model.TinTuc;
import fit.iuh.bai6_restapi.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class TinTucDao extends Generic<TinTuc> {


    public TinTucDao() {
        super(TinTuc.class);
    }

    public List<TinTuc> findAll(){
        EntityManager em = JpaUtil.getEmf().createEntityManager();

        try {
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

    public void update(Long id, TinTuc ttMoi){
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        try {
            em.getTransaction().begin();
            TinTuc tinTuc = em.find(TinTuc.class, id);
            if(id != null){
                tinTuc.setTieuDe(ttMoi.getTieuDe());
                tinTuc.setNdTT(ttMoi.getNdTT());
                tinTuc.setLienKet(ttMoi.getLienKet());
                tinTuc.setDanhMuc(ttMoi.getDanhMuc());
            }

            em.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            em.close();
        }
    }
}
