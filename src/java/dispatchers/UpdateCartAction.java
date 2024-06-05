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
    /**
    * Updates the shopping cart by removing selected books and updating the quantities of the remaining books.
    * 
    * @param request  the HttpServletRequest object that contains the request the client has made of the servlet
    * @param response the HttpServletResponse object that contains the response the servlet sends to the client
    * @return a String representing the path to the cart page
    * @throws ServletException if the request for the POST could not be handled
    * @throws IOException if an input or output error is detected when the servlet handles the request
    */
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
