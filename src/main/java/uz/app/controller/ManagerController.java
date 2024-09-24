package uz.app.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.app.entity.Category;
import uz.app.entity.Product;
import uz.app.repository.CategoryRepository;
import uz.app.repository.ProductRepository;

import java.util.List;

@Controller
@RequestMapping("/manager")
@RequiredArgsConstructor
public class ManagerController {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    @GetMapping
    public String manager() {
        return "manager-cabinet";
    }
    @GetMapping("product/add")
    public String addProduct(HttpServletRequest request) {
        List<Category> categories = categoryRepository.findAll();
        request.setAttribute("categorys",categories);
        return "add-product";
    }
    @PostMapping("product/add")
    public String addProduct(String name,String category,String description,String brand) {
        if(name.isBlank()||category.isBlank()) {
            return "redirect:/manager";
        }
        Product product = new Product();
        product.setName(name);
        product.setCategory(categoryRepository.findById(category).get());
        product.setDescription(description);
        product.setBrand(brand);
        productRepository.save(product);
        return "redirect:/manager";
    }
    @GetMapping("category/add")
    public String addCategory() {
        return "add-category";
    }
    @PostMapping("category/add")
    public String addCategory(String name) {
        Category category = new Category();
        category.setName(name);
        categoryRepository.save(category);
        return "redirect:/manager";
    }



    @GetMapping("/input")
    public String acceptance() {
        return "input";
    }

}
