package dispatchers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.Iterator;
import javax.servlet.http.HttpSession;
import model.CartItem;

public class UpdateCartAction implements IDispatcher {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Map cart = (Map) session.getAttribute("cart");
        String[] booksToRemove = request.getParameterValues("remove");

        if (booksToRemove != null) {
            for (String isbn : booksToRemove) {
                cart.remove(isbn);
            }
        }

        Set entries = cart.entrySet();
        Iterator iter = entries.iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String isbn = (String) entry.getKey();
            CartItem item = (CartItem) entry.getValue();
            int quantity = Integer.parseInt(request.getParameter(isbn));
            item.updateQuantity(quantity);
        }
        
        return "/jsp/cart.jsp";
    }
}
