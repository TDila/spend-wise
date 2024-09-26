package com.vulcan.spend_wise.service.category;

import com.vulcan.spend_wise.exceptions.AlreadyExistsException;
import com.vulcan.spend_wise.exceptions.ResourceNotFoundException;
import com.vulcan.spend_wise.model.Category;
import com.vulcan.spend_wise.respository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{
    private CategoryRepository categoryRepository;
    @Override
    public Category createCategory(Category category) {
        return Optional.of(category)
                .filter(c -> categoryRepository.existsByName(c.getName()))
                .map(categoryRepository::save)
                .orElseThrow(() -> {
                    return new AlreadyExistsException("Category: "+category.getName()+" already exists!");
                });
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> {
                    return new ResourceNotFoundException("Category not found with ID: "+id);
                });
    }

    @Override
    public Category updateCategory(Long id, Category category) {
        return Optional.ofNullable(getCategoryById(id))
                .map(oldCategory -> {
                    oldCategory.setName(category.getName());
                    return categoryRepository.save(oldCategory);
                }).orElseThrow(() -> {
                    return new ResourceNotFoundException("Category not found with ID: "+id);
                });
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.findById(id).ifPresentOrElse(category -> {
            categoryRepository.delete(category);
        }, () -> {
            throw new ResourceNotFoundException("Category not found with ID: "+id);
        });
    }
}
