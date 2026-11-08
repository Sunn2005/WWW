package fit.iuh.jdbc.service;

import fit.iuh.jdbc.dto.DanhMucThongKe;
import fit.iuh.jdbc.dto.TinTucKemTenDMDto;
import fit.iuh.jdbc.enitity.TinTuc;
import fit.iuh.jdbc.repo.DanhMucRepo;
import fit.iuh.jdbc.repo.TinTucRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class TinTucService {
    private TinTucRepo tinTucRepo;
    private DanhMucRepo danhMucRepo;

    public TinTucService(TinTucRepo tinTucRepo, DanhMucRepo danhMucRepo) {
        this.tinTucRepo = tinTucRepo;
        this.danhMucRepo = danhMucRepo;
    }

    public Iterable<TinTuc> findAll(){
        Iterable<TinTuc> res = tinTucRepo.findAll();
        System.out.println("");
        return res;
    }

    public Optional<TinTuc> findById(Integer maTT){
        return tinTucRepo.findById(maTT);
    }

    public TinTuc create(TinTuc t){
        return tinTucRepo.save(t);
    }

    public TinTuc update(TinTuc tinTuc){
        if (tinTuc.getMaTT() == null) throw new RuntimeException("Khong tim thay ma");
        return tinTucRepo.save(tinTuc);
    }

    public void delete(Integer maTT){
         tinTucRepo.deleteById(maTT);
    }

    public List<TinTuc> findTinTucsByTieuDeContainingIgnoreCase(String tieuDe){
        return tinTucRepo.findTinTucsByTieuDeContainingIgnoreCase(tieuDe);
    }

    public List<TinTuc> findTinTucsByMaDmOrderByNgayDangDesc(Integer maDm){
        return tinTucRepo.findTinTucsByMaDmOrderByNgayDangDesc(maDm);
    }

    public List<TinTucKemTenDMDto> findTinTucKemTenDM(){
        return tinTucRepo.findTinTucKemTenDM();
    }

    public List<DanhMucThongKe> staticsDanhMucByTinTuc(){
        return tinTucRepo.staticsDanhMucByTinTuc();
    }
    public List<DanhMucThongKe> thongKeTheoThang(int thang, int nam){
        return tinTucRepo.thongKeTheoThang(thang, nam);
    }
}

