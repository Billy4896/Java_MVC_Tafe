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
import model.Tbooks;

public class AddToCartAction implements IDispatcher {
    
    /**
    * Adds selected books to the shopping cart stored in the session.
    * 
    * @param request  the HttpServletRequest object that contains the request the client has made of the servlet
    * @param response the HttpServletResponse object that contains the response the servlet sends to the client
    * @return a String representing the path to the next page (the titles page)
    * @throws ServletException if the request for the POST could not be handled
    * @throws IOException if an input or output error is detected when the servlet handles the request
    */
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
                Tbooks book = getBookFromList(isbn, session);
                CartItem item = new CartItem(book);
                item.setQuantity(quantity);
                cart.put(isbn, item);
            }
        }
        session.setAttribute("cart", cart);
        return nextPage;
    }

    /**
    * Retrieves a book from the list of books stored in the session based on its ISBN.
    * 
    * @param isbn the ISBN of the book to be retrieved
    * @param session the HttpSession object that contains the session the client has with the servlet
    * @return the Tbooks object representing the book with the specified ISBN, or null if not found
    */
    private Tbooks getBookFromList(String isbn, HttpSession session) {
        List<Tbooks> list = (List<Tbooks>) session.getAttribute("Books");
        for (Tbooks book : list) {
            if (isbn.equals(book.getIsbn())) {
                return book;
            }
        }
        return null;
    }
}
