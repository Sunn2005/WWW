package fit.iuh.jdbctemplate.service;

import fit.iuh.jdbctemplate.dao.DanhMucDao;
import fit.iuh.jdbctemplate.dao.TinTucDao;
import fit.iuh.jdbctemplate.model.DanhMuc;
import fit.iuh.jdbctemplate.model.TinTuc;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DanhMucService {
    private final DanhMucDao dmDao;
    private final TinTucDao tinDao;

    public DanhMucService(DanhMucDao dmDao, TinTucDao tinDao) {
        this.dmDao = dmDao;
        this.tinDao = tinDao;
    }

    public List<DanhMuc> findAll() { return dmDao.findAll(); }
    public DanhMuc create(DanhMuc d) { return dmDao.insert(d); }

    @Transactional
    public void delete(Integer id) {
        List<TinTuc> list = tinDao.findByMaDM(id);
        for (TinTuc t : list) tinDao.delete(t.getMaTT());
        dmDao.delete(id);
    }
}
