package fit.iuh.bai06_gk.servlet;

import fit.iuh.bai06_gk.dao.DanhSachDanhMuc;
import fit.iuh.bai06_gk.dao.DanhSachTinTucQuanLy;
import fit.iuh.bai06_gk.model.DanhMuc;
import fit.iuh.bai06_gk.model.TinTuc;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/tintucs")
public class DanhSachTinTucServlet extends HttpServlet {
    private DanhSachTinTucQuanLy danhSachTinTucQuanLy;
    private DanhSachDanhMuc danhSachDanhMuc;

    @Override
    public void init(ServletConfig config) throws ServletException {
        danhSachTinTucQuanLy = new DanhSachTinTucQuanLy();
        danhSachDanhMuc = new DanhSachDanhMuc();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action != null) {
            req.setAttribute("danhmucs", danhSachDanhMuc.findAll());
            if (action.equals("EDIT")){
                Integer maTT = Integer.parseInt(req.getParameter("maTT"));
                req.setAttribute("tintuc", danhSachTinTucQuanLy.findById(maTT));
            }

            req.getRequestDispatcher("tin-tuc-form.jsp").forward(req, resp);
        } else {
            req.setAttribute("tintucs", danhSachTinTucQuanLy.findAll());
            req.getRequestDispatcher("danh-sach-tin-tuc.jsp").forward(req, resp);
        }


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action != null){
            switch (action){
                case "DELETE" -> {
                    Integer maTT = Integer.parseInt(req.getParameter("maTT"));
                    danhSachTinTucQuanLy.delete(maTT);
                    resp.sendRedirect("tintucs");
                }
                case "SAVE" ->{
                    String maTT = req.getParameter("maTT");
                    String tieuDe = req.getParameter("tieuDe");
                    String ndTT = req.getParameter("ndTT");
                    String lienKet = req.getParameter("lienKet");
                    String maDm = req.getParameter("maDm");
                    DanhMuc danhMuc = danhSachDanhMuc.findById(Integer.parseInt(maDm));

                    TinTuc tinTuc = new TinTuc(null, tieuDe, ndTT, lienKet, danhMuc);
                    if (maTT != null){
                        danhSachTinTucQuanLy.update(Integer.parseInt(maTT), tinTuc);
                    } else {
                        danhSachTinTucQuanLy.save(tinTuc);
                    }
                    resp.sendRedirect("tintucs");
                }
            }
        }
    }
}
