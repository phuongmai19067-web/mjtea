package fi.haagahelia.mjtea.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.security.access.prepost.PreAuthorize;

import fi.haagahelia.mjtea.model.Supplier;
import fi.haagahelia.mjtea.model.SupplierRepository;

@Controller
public class SupplierController {

    @Autowired
    private SupplierRepository repository;

    // Danh sách nhà cung cấp
    @GetMapping("/supplierlist")
    public String supplierList(Model model) {
        model.addAttribute("suppliers", repository.findAll());
        return "supplierlist";
    }

    // Mở form thêm nhà cung cấp
    @GetMapping("/addsupplier")
    public String addSupplier(Model model) {
        model.addAttribute("supplier", new Supplier());
        return "addsupplier";
    }

    // Lưu (dùng chung thêm mới và sửa)
    @PostMapping("/savesupplier")
    public String saveSupplier(@ModelAttribute Supplier supplier) {
        repository.save(supplier);
        return "redirect:supplierlist";
    }

    // Sửa nhà cung cấp
    @GetMapping("/editsupplier/{id}")
    public String editSupplier(@PathVariable("id") Long id, Model model) {
        model.addAttribute("supplier", repository.findById(id).get());
        return "editsupplier";
    }

    // Xóa — chỉ MANAGER
    @PreAuthorize("hasRole('MANAGER')")
    @GetMapping("/deletesupplier/{id}")
    public String deleteSupplier(@PathVariable("id") Long id) {
        repository.deleteById(id);
        return "redirect:../supplierlist";
    }
}