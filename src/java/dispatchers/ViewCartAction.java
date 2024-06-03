package dispatchers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Map;
import javax.servlet.http.HttpSession;

public class ViewCartAction implements IDispatcher {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String nextPage = "/jsp/cart.jsp";
        Map cart = (Map) session.getAttribute("cart");
        if (cart == null) {
            nextPage = "/jsp/titles.jsp";
        }
        return nextPage;
    }
}
