package budgetManagement.store;

import budgetManagement.model.Category;

import java.sql.SQLException;
import java.util.List;

public interface CategoriesStore {
    void addCategory(Category category) throws SQLException;

    List<Category> getCategories() throws SQLException;

}
