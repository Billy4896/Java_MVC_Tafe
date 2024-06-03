package dispatchers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpSession;
import model.Book;
import model.CartItem;

public class AddToCartAction implements IDispatcher {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String nextPage = "/jsp/titles.jsp";
        Map cart = (Map) session.getAttribute("cart");
        String[] selectedBooks = request.getParameterValues("add");

        if (cart == null) {
            cart = new HashMap();
        }
        for (String isbn : selectedBooks) {
            int quantity = Integer.parseInt(request.getParameter(isbn));
            if (cart.containsKey(isbn)) {
                CartItem item = (CartItem) cart.get(isbn);
                item.setQuantity(quantity);
            } else {
                Book book = getBookFromList(isbn, session);
                CartItem item = new CartItem(book);
                item.setQuantity(quantity);
                cart.put(isbn, item);
            }
        }
        session.setAttribute("cart", cart);
        return nextPage;
    }

    private Book getBookFromList(String isbn, HttpSession session) {
        List<Book> list = (List<Book>) session.getAttribute("Books");
        for (Book book : list) {
            if (isbn.equals(book.getIsbn())) {
                return book;
            }
        }
        return null;
    }
}
