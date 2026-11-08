package fit.iuh.bai06_gk.servlet;

import fit.iuh.bai06_gk.dao.DanhSachDanhMuc;
import fit.iuh.bai06_gk.model.DanhMuc;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/danhmucs")
public class DanhMucServlet extends HttpServlet {
    private DanhSachDanhMuc danhSachDanhMuc;
    @Override
    public void init(ServletConfig config) throws ServletException {
        danhSachDanhMuc = new DanhSachDanhMuc();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action != null){
            Integer maDm = Integer.parseInt(req.getParameter("maDm"));
            req.setAttribute("danhmuc", danhSachDanhMuc.findById(maDm));


            req.getRequestDispatcher("danh-muc-form.jsp").forward(req, resp);
            return;
        } else {
            req.setAttribute("danhmucs", danhSachDanhMuc.findAll());
        }
        req.getRequestDispatcher("danh-sach-danh-muc.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action != null){
            switch (action){
                case "DELETE" -> {
                    Integer maDm = Integer.parseInt(req.getParameter("maDm"));
                    danhSachDanhMuc.delete(maDm);
                    resp.sendRedirect("danhmucs");
                }

                case "SAVE" -> {
                    String maDm = req.getParameter("maDm");
                    String tenDM = req.getParameter("tenDM");
                    String nguoiQuanLy = req.getParameter("nguoiQuanLy");
                    String ghiChu = req.getParameter("ghiChu");

                    DanhMuc danhMuc = new DanhMuc(null, tenDM, nguoiQuanLy, ghiChu, null);

                    if (maDm != null){
                        danhSachDanhMuc.update(Integer.parseInt(maDm), danhMuc);
                    }else{
                        danhSachDanhMuc.save(danhMuc);
                    }
                    resp.sendRedirect("danhmucs");
                }
            }
        }
    }
}
