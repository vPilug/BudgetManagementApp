package budgetManagement.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletUtils {
    public static Action getActionFromRequest(HttpServletRequest req, HttpServletResponse resp) {
        String actionAsString = req.getParameter("action");
        actionAsString = (actionAsString != null) ? actionAsString : "LIST";
        Action action = Action.LIST;
        try {
            action = Action.valueOf(actionAsString);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return action;
    }
}
