package dispatchers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Map;
import javax.servlet.http.HttpSession;

public class ViewCartAction implements IDispatcher {
    /**
    * Determines the next page based on the presence of a shopping cart in the session.
    * If the cart exists, redirects the user to the cart page; otherwise, redirects to the titles page.
    * 
    * @param request  the HttpServletRequest object that contains the request the client has made of the servlet
    * @param response the HttpServletResponse object that contains the response the servlet sends to the client
    * @return a String representing the path to the next page (either the cart page or the titles page)
    * @throws ServletException if the request for the POST could not be handled
    * @throws IOException if an input or output error is detected when the servlet handles the request
    */
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
