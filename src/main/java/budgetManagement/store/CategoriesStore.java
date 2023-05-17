package budgetManagement.store;

import budgetManagement.model.Category;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public interface CategoriesStore {
    void addCategory(Category category) throws SQLException;
    List<Category> getCategories() throws SQLException;
    void updateCategory(UUID id, Category category) throws SQLException;
    void deleteCategory(UUID id) throws SQLException;

    Category findCategoryByName(String name) throws SQLException;
}
