package fit.iuh.jdbctemplate.controller;

import fit.iuh.jdbctemplate.dto.TinTucVoiTen;
import fit.iuh.jdbctemplate.model.TinTuc;
import fit.iuh.jdbctemplate.service.TinTucService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/tintuc")
public class TinTucController {
    private final TinTucService service;

    public TinTucController(TinTucService service) {
        this.service = service;
    }

    @GetMapping
    public List<TinTuc> findAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public TinTuc findById(@PathVariable Integer id) { return service.findById(id); }

//    @GetMapping("/search")
//    public List<TinTuc> search(@RequestParam String tieuDe) { return service.searchByTieuDe(tieuDe); }

    @GetMapping("/kem-ten-dm")
    public List<TinTucVoiTen> kemTen() { return service.findKemTen(); }

    @GetMapping("/theo-danh-muc")
    public List<TinTucVoiTen> theoDanhMuc(@RequestParam String ten) { return service.findByTenDanhMuc(ten); }

    @PostMapping
    public ResponseEntity<TinTuc> create(@RequestBody TinTuc t) {
        return ResponseEntity.status(201).body(service.create(t));
    }

    @PutMapping("/{id}")
    public TinTuc update(@PathVariable Integer id, @RequestBody TinTuc t) { return service.update(id, t); }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) { service.delete(id); }

    @GetMapping("/trong-khoang-ngay")
    public List<TinTuc> trongKhoangNgay(@RequestParam LocalDateTime tu, @RequestParam LocalDateTime den) {
        return service.findTrongKhoangNgay(tu, den);
    }
}
