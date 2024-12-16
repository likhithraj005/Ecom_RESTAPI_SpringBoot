package com.ecommerce.project.controller;

import com.ecommerce.project.config.AppConstants;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;
import com.ecommerce.project.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired //field injection
    private CategoryService categoryService;
//    private List<Category> categories = new ArrayList<>();

//    public CategoryController(CategoryService categoryService) { //Constructor injection
//        this.categoryService = categoryService;
//    }

    @GetMapping("/echo")
    public ResponseEntity<String> echoMessage(@RequestParam(name = "message", defaultValue = "Hey", required = false) String message){
        return new ResponseEntity<>("Echo message: " + message, HttpStatus.OK);
    }

//    @GetMapping("/api/public/categories")
//    public List<Category> getAllCategories(){
////        return categories;
//        return categoryService.getAllCategories();
//    }
    //@RequestMapping(value = "/api/public/categories", method = RequestMethod.GET) //method level
    //@GetMapping("/api/public/categories")
//    @GetMapping("/public/categories")
//    public ResponseEntity<List<Category>> getAllCategories(){
////        return categories;
//        List<Category> categories = categoryService.getAllCategories();
//        return new ResponseEntity<>(categories, HttpStatus.OK);
//    }
    @GetMapping("/public/categories")
    public ResponseEntity<CategoryResponse> getAllCategories(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_CATEGORIES_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIR, required = false) String sortOrder){
        CategoryResponse categoryResponse = categoryService.getAllCategories(pageNumber, pageSize, sortBy, sortOrder);
        return new ResponseEntity<>(categoryResponse, HttpStatus.OK);
    }

//    @PostMapping("/api/public/categories")
//    public String createCategory(@RequestBody Category category){
////        categories.add(category);
//        categoryService.createCategory(category);
//        return "Category added successfully: " + category;
//    }
    //@PostMapping("/api/public/categories")
//    @PostMapping("/public/categories")
//    public ResponseEntity<String> createCategory(@Valid @RequestBody Category category){
////        categories.add(category);
//        categoryService.createCategory(category);
//        return new ResponseEntity<>("Category added successfully" + category, HttpStatus.CREATED);
//    }
    @PostMapping("/public/categories")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO){
        CategoryDTO savedCategoryDTO = categoryService.createCategory(categoryDTO);
        return new ResponseEntity<>(savedCategoryDTO, HttpStatus.CREATED);
    }

//    @DeleteMapping("/api/admin/categories/{categoryId}")
//    public String deleteCategory(@PathVariable Long categoryId) {
//        String status = categoryService.deleteCategory(categoryId);
//        return status;
//    }
    //@DeleteMapping("/api/admin/categories/{categoryId}")
//    @DeleteMapping("/admin/categories/{categoryId}")
//    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId) {
//        try {
//            String status = categoryService.deleteCategory(categoryId);
////            return new ResponseEntity<>(status, HttpStatus.OK);
////            return ResponseEntity.ok(status);
//            return ResponseEntity.status(HttpStatus.OK).body(status);
//        } catch (ResponseStatusException e){
//            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
//        }
//    }
//    @DeleteMapping("/admin/categories/{categoryId}")
//    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId) {
//        String status = categoryService.deleteCategory(categoryId);
//        return new ResponseEntity<>(status, HttpStatus.OK);
//    }
    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity<CategoryDTO > deleteCategory(@PathVariable Long categoryId) {
        CategoryDTO deleteCategory = categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>(deleteCategory, HttpStatus.OK);
    }

//    //@PutMapping("/api/public/categories/{categoryId}")
//    @PutMapping("/public/categories/{categoryId}")
//    public ResponseEntity<String> updateCategory(@RequestBody Category category, @PathVariable Long categoryId){
//        try{
//            Category savedCategory = categoryService.updateCategory(category, categoryId);
//            return new ResponseEntity<>("Category with category id: " + categoryId, HttpStatus.OK);
//        }catch (ResponseStatusException e){
//            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
//        }
//    }
//    @PutMapping("/public/categories/{categoryId}")
//    public ResponseEntity<String> updateCategory(@Valid @RequestBody Category category, @PathVariable Long categoryId){
//        Category savedCategory = categoryService.updateCategory(category, categoryId);
//        return new ResponseEntity<>("Category with category id: " + categoryId, HttpStatus.OK);
//    }
    @PutMapping("/public/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO, @PathVariable Long categoryId){
        CategoryDTO savedCategoryDTO = categoryService.updateCategory(categoryDTO, categoryId);
        return new ResponseEntity<>(savedCategoryDTO, HttpStatus.OK);
    }
}
