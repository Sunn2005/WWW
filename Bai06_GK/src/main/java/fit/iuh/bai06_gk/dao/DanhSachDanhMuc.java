package fit.iuh.bai06_gk.dao;

import fit.iuh.bai06_gk.model.DanhMuc;
import fit.iuh.bai06_gk.model.TinTuc;
import fit.iuh.bai06_gk.util.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class DanhSachDanhMuc extends Generic<DanhMuc> {

    public DanhSachDanhMuc() {
        super(DanhMuc.class);
    }

    public List<DanhMuc> findAll(){
        EntityManager em = JpaUtil.getEmf().createEntityManager();

        try {
            String jpql = """
                    select dm from DanhMuc dm
                    """;
            TypedQuery<DanhMuc> query = em.createQuery(jpql, DanhMuc.class);

            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return null;
    }

    public void update(Integer id, DanhMuc danhMucMoi){
        EntityManager em = JpaUtil.getEmf().createEntityManager();
        try{

            em.getTransaction().begin();
            DanhMuc danhMuc = em.find(DanhMuc.class, id);
            if (id != null){
                danhMuc.setTenDM(danhMucMoi.getTenDM());
                danhMuc.setNguoiQuanLy(danhMuc.getNguoiQuanLy());
                danhMuc.setGhiChu(danhMucMoi.getGhiChu());
            }

            em.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            em.close();
        }
    }
}
