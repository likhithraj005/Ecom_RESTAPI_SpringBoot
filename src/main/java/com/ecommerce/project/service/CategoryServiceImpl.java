package com.ecommerce.project.service;

import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;
import com.ecommerce.project.repositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService{

    @Autowired
    private CategoryRepository categoryRepository;
//    private List<Category> categories = new ArrayList<>();

//    private Long nextId = 1L;

    @Autowired
    private ModelMapper modelMapper;

//    @Override
//    public List<Category> getAllCategories() {
////        return categories;
////        return categoryRepository.findAll();
//        List<Category> categories = categoryRepository.findAll();
//        if(categories.isEmpty())
//            throw new APIException("No category created till now!");
//        return categories;
//    }
    @Override
    public CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {

        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<Category> categoryPage = categoryRepository.findAll(pageDetails);

//        List<Category> categories = categoryRepository.findAll();
        List<Category> categories = categoryPage.getContent();
        if(categories.isEmpty())
            throw new APIException("No category created till now!");


        List<CategoryDTO> categoryDTOS = categories.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
//                .collect(Collectors.toList());
                .toList();

        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(categoryDTOS);

        categoryResponse.setPageNumber(categoryPage.getNumber());
        categoryResponse.setPageSize(categoryPage.getSize());
        categoryResponse.setTotalElements(categoryPage.getTotalElements());
        categoryResponse.setTotalPages(categoryPage.getTotalPages());
        categoryResponse.setLastPage(categoryPage.isLast());

        return categoryResponse;
    }

//    @Override
//    public void createCategory(Category category) {
////        category.setCategoryId(nextId++);
////        categories.add(category);
//        Category savedCategory = categoryRepository.findByCategoryName(category.getCategoryName());
//        if(savedCategory != null)
//            throw new APIException("Category with the name " + category.getCategoryName() + " already exists!");
//        categoryRepository.save(category);
//    }
    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = modelMapper.map(categoryDTO, Category.class);
        Category categoryFromDb = categoryRepository.findByCategoryName(category.getCategoryName());

        if(categoryFromDb != null)
            throw new APIException("Category with the name " + category.getCategoryName() + " already exists!");

        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDTO.class);
    }

//    @Override
//    public String deleteCategory(Long categoryId) {
//        Category category = categories.stream()
//                        .filter(c -> c.getCategoryId().equals(categoryId))
////                        .findFirst().get();
////                        .findFirst().orElse(null);
//                        .findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found"));
//
////        if(category == null){
////            return "CATEGORY NOT FOUND!";
////        }
//
//        categories.remove(category);
//        return "Category with categoryId: " + categoryId + " deleted successfully!";
//    }
//    @Override
//    public String deleteCategory(Long categoryId) {
//        List<Category> categories = categoryRepository.findAll();
//
//        Category category = categories.stream()
//                .filter(c -> c.getCategoryId().equals(categoryId))
////                        .findFirst().get();
////                        .findFirst().orElse(null);
//                .findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found"));
//
//        categoryRepository.delete(category);
//        return "Category with categoryId: " + categoryId + " deleted successfully!";
//    }
//    @Override
//    public String deleteCategory(Long categoryId) {
//        Category category = categoryRepository.findById(categoryId)
////                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found"));
//                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
//        categoryRepository.delete(category);
//        return "Category with categoryId: " + categoryId + " deleted successfully!";
//    }
    @Override
    public CategoryDTO deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
        categoryRepository.delete(category);

        return modelMapper.map(category, CategoryDTO.class);
    }

//    @Override
//    public Category updateCategory(Category category, Long categoryId) {
//        Category savedCategory = categoryRepository.findById(categoryId)
////                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found"));
//                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
//        category.setCategoryId(categoryId);
//        savedCategory = categoryRepository.save(category);
//        return savedCategory;
//    }
    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId) {
        Category savedCategory = categoryRepository.findById(categoryId)
    //                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found"));
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        Category category = modelMapper.map(categoryDTO, Category.class);
        category.setCategoryId(categoryId);
        savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDTO.class);
    }
}
