package budgetManagement.filers;

import java.time.LocalDate;
import java.util.UUID;

public class ExpenseFilter {
    private LocalDate fromDate;
    private LocalDate toDate;

    private UUID categoryId;

    public ExpenseFilter(String fromDate, String toDate, String categoryId) {
        this.fromDate = populateDate(fromDate);
        this.toDate = populateDate(toDate);
        this.categoryId = populateUUID(categoryId);
    }

    private UUID populateUUID(String categoryId) {
        try {
            return UUID.fromString(categoryId);
        } catch (Exception e) {
            return null;
        }
    }

    private LocalDate populateDate(String date) {
        try {
            return LocalDate.parse(date);
        } catch (Exception e) {
            return null;
        }
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public UUID getCategoryId() {
        return categoryId;
    }

    @Override
    public String toString() {
        return "Filter{" +
                "fromDate=" + fromDate +
                ", toDate=" + toDate +
                ", categoryId=" + categoryId +
                '}';
    }
}
