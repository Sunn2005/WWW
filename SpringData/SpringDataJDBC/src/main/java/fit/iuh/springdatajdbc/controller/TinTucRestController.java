package fit.iuh.springdatajdbc.controller;

import fit.iuh.springdatajdbc.dto.TinTucVoiTen;
import fit.iuh.springdatajdbc.enitity.TinTuc;
import fit.iuh.springdatajdbc.repo.DanhMucRepository;
import fit.iuh.springdatajdbc.repo.TinTucRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tintuc")
public class TinTucRestController {
    private final TinTucRepository tinRepo;
    private final DanhMucRepository dmRepo;

    public TinTucRestController(TinTucRepository tinRepo, DanhMucRepository dmRepo) {
        this.tinRepo = tinRepo;
        this.dmRepo = dmRepo;
    }

    @GetMapping
    public Iterable<TinTuc> getAll() { return tinRepo.findAll(); }

    @GetMapping("/kem-ten-danh-muc")
    public List<TinTucVoiTen> kemTen() { return tinRepo.findAllVoiTenDanhMuc(); }

    @GetMapping("/theo-danh-muc")
    public List<TinTucVoiTen> theoTen(@RequestParam String ten) {
        return tinRepo.findByTenDanhMuc(ten);
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody TinTuc t) {
        if (t.getMaDM() == null || !dmRepo.existsById(t.getMaDM())) {
            return ResponseEntity.badRequest().body("maDM khong ton tai");
        }
        t.setMaTT(null);
        return ResponseEntity.status(201).body(tinRepo.save(t));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) { tinRepo.deleteById(id); }
}
