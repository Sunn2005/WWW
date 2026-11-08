package fit.iuh.jdbctemplate.service;


import fit.iuh.jdbctemplate.dao.DanhMucDao;
import fit.iuh.jdbctemplate.dao.TinTucDao;
import fit.iuh.jdbctemplate.dto.TinTucVoiTen;
import fit.iuh.jdbctemplate.model.TinTuc;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TinTucService {
    private final TinTucDao tinDao;
    private final DanhMucDao dmDao;

    public TinTucService(TinTucDao tinDao, DanhMucDao dmDao) {
        this.tinDao = tinDao;
        this.dmDao = dmDao;
    }

    public List<TinTuc> findAll() { return tinDao.findAll(); }

    public TinTuc findById(Integer id) {
        TinTuc t = tinDao.findById(id);
        if (t == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Khong tim thay tin");
        return t;
    }

    public TinTuc create(TinTuc t) {
        if (t.getMaDM() == null || !dmDao.existsById(t.getMaDM())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "maDM khong ton tai");
        }
        return tinDao.insert(t);
    }

    public TinTuc update(Integer id, TinTuc t) {
        if (tinDao.findById(id) == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Khong tim thay tin");
        }
        if (t.getMaDM() == null || !dmDao.existsById(t.getMaDM())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "maDM khong ton tai");
        }
        tinDao.update(id, t);
        return tinDao.findById(id);
    }

    public void delete(Integer id) { tinDao.delete(id); }

    public List<TinTucVoiTen> findKemTen(){
        return tinDao.findKemTen();
    }

    public List<TinTucVoiTen> findByTenDanhMuc(String ten){
        return tinDao.findByTenDanhMuc(ten);
    }

    public List<TinTuc> findTrongKhoangNgay(LocalDateTime tu, LocalDateTime den){
        return tinDao.findTrongKhoangNgay(tu, den);
    }

}
