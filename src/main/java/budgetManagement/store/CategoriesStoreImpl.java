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
}
