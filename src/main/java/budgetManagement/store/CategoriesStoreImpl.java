package budgetManagement.store;

import budgetManagement.model.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CategoriesStoreImpl implements CategoriesStore {

    private Connection dbConnection;

    public CategoriesStoreImpl(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public void addCategory(Category category) throws SQLException {
        String insertStatement = "INSERT INTO categories VALUES (?, ?)";
        PreparedStatement createCategoryStatement = dbConnection.prepareStatement(insertStatement);
        createCategoryStatement.setObject(1, category.getId());
        createCategoryStatement.setString(2, category.getName());
        createCategoryStatement.executeUpdate();
    }

    @Override
    public List<Category> getCategories() throws SQLException {
        Statement getCategoriesStatement;
        getCategoriesStatement = dbConnection.createStatement();
        ResultSet categoriesResultSet;
        categoriesResultSet = getCategoriesStatement.executeQuery("SELECT * FROM categories");
        List<Category> categoriesList = new ArrayList<>();
        while (true) {
            if (!categoriesResultSet.next()) break;
            UUID categoryId;
            categoryId = UUID.fromString(categoriesResultSet.getObject("id").toString());
            String categoryName;
            categoryName = categoriesResultSet.getString(2);
            categoriesList.add(new Category(categoryId, categoryName));
        }
        return categoriesList;
    }

    @Override
    public void updateCategory(UUID id, Category category) throws SQLException {
        String updateStatement = "UPDATE categories SET name = ? WHERE id = ?";
        PreparedStatement updateCategoryStatement;
        updateCategoryStatement = dbConnection.prepareStatement(updateStatement);
        updateCategoryStatement.setString(1, category.getName());
        updateCategoryStatement.setObject(2, id);
        updateCategoryStatement.executeUpdate();
    }

    @Override
    public void deleteCategory(UUID id) throws SQLException {
        PreparedStatement deleteCategoryStatement = dbConnection.prepareStatement("DELETE FROM categories WHERE id = ?");
        deleteCategoryStatement.setObject(1, id);
        deleteCategoryStatement.executeUpdate();
    }

    @Override
    public Category findCategoryByName(String name) throws SQLException {
        UUID categoryId;
        String categoryName;
        PreparedStatement findCategoryByNameStatement = dbConnection.prepareStatement("SELECT * FROM categories WHERE name = ?");
        findCategoryByNameStatement.setString(1, name);
        ResultSet categoryResultSet = findCategoryByNameStatement.executeQuery();
        categoryResultSet.next();
        categoryId = UUID.fromString(categoryResultSet.getObject("id").toString());
        categoryName = categoryResultSet.getString("name");
        return new Category(categoryId, categoryName);
    }
}
