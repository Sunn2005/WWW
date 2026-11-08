package fit.iuh.jdbc.controller;

import fit.iuh.jdbc.dto.DanhMucThongKe;
import fit.iuh.jdbc.dto.TinTucKemTenDMDto;
import fit.iuh.jdbc.enitity.TinTuc;
import fit.iuh.jdbc.service.TinTucService;
import jakarta.websocket.server.PathParam;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tintuc")
public class TinTucController {
    private TinTucService tinTucService;

    public TinTucController(TinTucService tinTucService) {
        this.tinTucService = tinTucService;
    }
    
    @GetMapping
    public Iterable<TinTuc> findAll(){
        return  tinTucService.findAll();
    }

    @GetMapping("/search")
    public List<TinTuc> findTinTucsByTieuDe(@RequestParam String tieuDe){
        return tinTucService.findTinTucsByTieuDeContainingIgnoreCase(tieuDe);
    }

    @GetMapping("/theo-danh-muc/{dmid}")
    public List<TinTuc> findTinTucsByMaDmOrderByNgayDangDesc(@PathVariable Integer dmid){
        return tinTucService.findTinTucsByMaDmOrderByNgayDangDesc(dmid);
    }

    @GetMapping("/kem-ten-dm")
    public List<TinTucKemTenDMDto> findTinTucKemTenDM(){
        return tinTucService.findTinTucKemTenDM();
    }

    @GetMapping("/thong-ke")
    public List<DanhMucThongKe> staticsDanhMucByTinTuc(){
        return tinTucService.staticsDanhMucByTinTuc();
    }

    @GetMapping("/thong-ke-theo-thang")
    public List<DanhMucThongKe> thongKeTheoThang(@RequestParam int thang, @RequestParam int nam){
        return tinTucService.thongKeTheoThang(thang, nam);
    }
}
