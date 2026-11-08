package fit.iuh.controller;


import fit.iuh.dto.EmployeeForm;
import fit.iuh.dto.EmployeeSearchCriteria;
import fit.iuh.model.Employee;
import fit.iuh.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("employees", employeeService.findAll());
        return "employees/list";
    }

    @GetMapping("/search")
    public String search(@ModelAttribute("criteria") EmployeeSearchCriteria criteria, Model model) {
        // Security: GUEST không được tìm theo lương -> xóa điều kiện lương trước khi tìm
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean canSearchSalary = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_USER")
                        || a.getAuthority().equals("ROLE_ADMIN"));
        if (!canSearchSalary && criteria.getMinSalary() != null) {
            criteria.setMinSalary(null);
            model.addAttribute("message", "Tài khoản GUEST không được tìm theo lương.");
        }

        if (criteria.hasAny()) {
            model.addAttribute("employees", employeeService.search(criteria));
        }
        model.addAttribute("positions", employeeService.findAllPositions());
        return "employees/search";
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("employeeForm", new EmployeeForm());
        model.addAttribute("departments", employeeService.findAllDepartments());
        model.addAttribute("positions", employeeService.findAllPositions());
        return "employees/form";
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping
    public String create(@Valid @ModelAttribute("employeeForm") EmployeeForm form,
                         BindingResult result, RedirectAttributes redirect, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("departments", employeeService.findAllDepartments());
            model.addAttribute("positions", employeeService.findAllPositions());
            return "employees/form";
        }
        Employee saved = employeeService.save(null, form);
        redirect.addFlashAttribute("message", "Đã thêm nhân viên " + saved.getName() + ".");
        return "redirect:/employees";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable Integer id, Model model) {
        model.addAttribute("employee", employeeService.findById(id));
        return "employees/detail";
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Integer id, Model model) {
        model.addAttribute("employeeForm", employeeService.getForm(id));
        model.addAttribute("departments", employeeService.findAllDepartments());
        model.addAttribute("positions", employeeService.findAllPositions());
        return "employees/form";
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @PostMapping("/{id}")
    public String update(@PathVariable Integer id,
                         @Valid @ModelAttribute("employeeForm") EmployeeForm form,
                         BindingResult result, RedirectAttributes redirect, Model model) {
        form.setId(id);
        if (result.hasErrors()) {
            model.addAttribute("departments", employeeService.findAllDepartments());
            model.addAttribute("positions", employeeService.findAllPositions());
            return "employees/form";
        }
        Employee saved = employeeService.save(id, form);
        redirect.addFlashAttribute("message", "Đã lưu thay đổi của nhân viên " + saved.getName() + ".");
        return "redirect:/employees";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Integer id, RedirectAttributes redirect) {
        employeeService.delete(id);
        redirect.addFlashAttribute("message", "Đã xóa nhân viên có ID " + id + ".");
        return "redirect:/employees";
    }

    // --- Quan hệ 1-n + thống kê (Yêu cầu 2 phần 3) ---
    @GetMapping("/by-position")
    public String byPosition(@RequestParam Integer positionId, Model model) {
        model.addAttribute("employees", employeeService.findByPositionId(positionId));
        model.addAttribute("positions", employeeService.findAllPositions());
        return "employees/list";
    }

    @GetMapping("/by-department-position")
    public String byDeptAndPos(@RequestParam Integer departmentId, @RequestParam Integer positionId, Model model) {
        model.addAttribute("employees", employeeService.findByDepartmentIdAndPositionId(departmentId, positionId));
        return "employees/list";
    }

    @GetMapping("/stats")
    public String stats(Model model) {
        model.addAttribute("stats", employeeService.getDepartmentStats());
        model.addAttribute("emptyDepartments", employeeService.findEmptyDepartments());
        model.addAttribute("departments", employeeService.findAllDepartments());
        return "employees/stats";
    }

    @GetMapping("/top")
    public String top(@RequestParam Integer departmentId, @RequestParam(defaultValue = "5") int n, Model model) {
        model.addAttribute("employees", employeeService.findTopN(departmentId, n));
        return "employees/list";
    }
}
