package com.example.webbshop.bo.Service;

import com.example.webbshop.bo.model.Category;
import com.example.webbshop.db.dao.CategoryDAO;
import com.example.webbshop.ui.DTO.CategoryDTO;

import java.util.List;

/**
 * Service class for managing product categories.
 * This class handles the business logic related to categories, including adding, updating, retrieving, and deleting.
 */
public class CategoryService {

    private final CategoryDAO categoryDAO;

    public CategoryService() {
        this.categoryDAO = new CategoryDAO();
    }

    /**
     * Adds a new category.
     *
     * @param categoryName the name of the category to add
     * @return the added Category object, or null if the addition failed
     */
    public Category addCategory(String categoryName) {
        if (categoryName == null || categoryName.trim().isEmpty()) {
            throw new IllegalArgumentException("Category name cannot be null or empty.");
        }
        Category newCategory = new Category();
        newCategory.setCategoryName(categoryName);
        return categoryDAO.addCategory(newCategory);
    }

    /**
     * Retrieves a category by its ID.
     *
     * @param categoryId the ID of the category to retrieve
     * @return the Category object if found, or null if not found
     */
    public Category getCategoryById(int categoryId) {
        return categoryDAO.getCategoryById(categoryId);
    }

    /**
     * Retrieves a category by its name.
     *
     * @param categoryName the name of the category to retrieve
     * @return the Category object if found, or null if not found
     */
    public Category getCategoryByName(String categoryName) {
        if (categoryName == null || categoryName.trim().isEmpty()) {
            throw new IllegalArgumentException("Category name cannot be null or empty.");
        }
        return categoryDAO.getCategoryByName(categoryName);
    }

    /**
     * Retrieves a list of all categories.
     *
     * @return a list of Category objects, or an empty list if no categories exist
     */
    public List<Category> getAllCategories() {
        return categoryDAO.getAllCategories();
    }

    /**
     * Updates an existing category.
     *
     * @param categoryId   the ID of the category to update
     * @param categoryName the new name of the category
     * @return true if the update was successful, false otherwise
     */
    public boolean updateCategory(int categoryId, String categoryName) {
        if (categoryId <= 0) {
            throw new IllegalArgumentException("Invalid category ID.");
        }
        if (categoryName == null || categoryName.trim().isEmpty()) {
            throw new IllegalArgumentException("Category name cannot be null or empty.");
        }

        Category category = new Category();
        category.setCategoryId(categoryId);
        category.setCategoryName(categoryName);
        return categoryDAO.updateCategory(category);
    }

    /**
     * Deletes a category by its ID.
     *
     * @param categoryId the ID of the category to delete
     * @return true if the category was deleted, false otherwise
     */
    public boolean deleteCategory(int categoryId) {
        if (categoryId <= 0) {
            throw new IllegalArgumentException("Invalid category ID.");
        }
        return categoryDAO.deleteCategory(categoryId);
    }
}
