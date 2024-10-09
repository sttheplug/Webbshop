package com.example.webbshop.db.dao;

import com.example.webbshop.bo.model.Category;
import com.example.webbshop.db.DatabaseConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) for managing product categories in the database.
 * Provides methods to create, read, update, and delete category records.
 */
public class CategoryDAO {
    private static final String INSERT_CATEGORY_SQL = "INSERT INTO categories (category_name) VALUES (?)";
    private static final String SELECT_CATEGORY_BY_ID = "SELECT * FROM categories WHERE category_id = ?";
    private static final String SELECT_CATEGORY_BY_NAME = "SELECT * FROM categories WHERE category_name = ?"; // New SQL statement
    private static final String SELECT_ALL_CATEGORIES = "SELECT * FROM categories";
    private static final String UPDATE_CATEGORY = "UPDATE categories SET category_name = ? WHERE category_id = ?";
    private static final String DELETE_CATEGORY = "DELETE FROM categories WHERE category_id = ?";

    /**
     * Adds a new category to the database.
     *
     * @param category the Category object to be added
     * @return the added Category object with its generated ID, or null if insertion failed
     */
    public Category addCategory(Category category) {
        try (Connection connection = DatabaseConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CATEGORY_SQL, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, category.getCategoryName());

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        category.setCategoryId(generatedKeys.getInt(1));  // Set the generated category ID
                    }
                }
            }
            return category;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves a category from the database by its ID.
     *
     * @param id the ID of the category to be retrieved
     * @return the Category object if found, or null if not found
     */
    public Category getCategoryById(int id) {
        Category category = null;
        try (Connection connection = DatabaseConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CATEGORY_BY_ID)) {

            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                category = new Category(
                        rs.getInt("category_id"),
                        rs.getString("category_name")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return category;
    }

    /**
     * Retrieves a category from the database by its name.
     *
     * @param name the name of the category to be retrieved
     * @return the Category object if found, or null if not found
     */
    public Category getCategoryByName(String name) {
        Category category = null;
        try (Connection connection = DatabaseConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CATEGORY_BY_NAME)) {

            preparedStatement.setString(1, name);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                category = new Category(
                        rs.getInt("category_id"),
                        rs.getString("category_name")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return category;
    }

    /**
     * Retrieves all categories from the database.
     *
     * @return a list of Category objects, or an empty list if no categories are found
     */
    public List<Category> getAllCategories() {
        List<Category> categories = new ArrayList<>();
        try (Connection connection = DatabaseConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_CATEGORIES)) {

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Category category = new Category(
                        rs.getInt("category_id"),
                        rs.getString("category_name")
                );
                categories.add(category);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

    /**
     * Updates an existing category in the database.
     *
     * @param category the Category object containing updated information
     * @return true if the update was successful, false otherwise
     */
    public boolean updateCategory(Category category) {
        boolean rowUpdated = false;
        try (Connection connection = DatabaseConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_CATEGORY)) {

            statement.setString(1, category.getCategoryName());
            statement.setInt(2, category.getCategoryId());
            rowUpdated = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowUpdated;
    }

    /**
     * Deletes a category from the database by its ID.
     *
     * @param id the ID of the category to be deleted
     * @return true if the category was deleted, false otherwise
     */
    public boolean deleteCategory(int id) {
        boolean rowDeleted = false;
        try (Connection connection = DatabaseConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_CATEGORY)) {

            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowDeleted;
    }
}
