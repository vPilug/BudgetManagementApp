package budgetManagement.filers;

import java.time.LocalDate;

public class IncomeFilter {
    private LocalDate fromDate;
    private LocalDate toDate;


    public IncomeFilter(String fromDate, String toDate) {
        this.fromDate = populateDate(fromDate);
        this.toDate = populateDate(toDate);
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


    @Override
    public String toString() {
        return "Filter{" +
                "fromDate=" + fromDate +
                ", toDate=" + toDate +
                '}';
    }
}

