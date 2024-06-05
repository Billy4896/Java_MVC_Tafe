package dispatchers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;

public class CheckoutAction implements IDispatcher {
    /**
    * Redirects the user to the checkout page.
    * 
    * @param request  the HttpServletRequest object that contains the request the client has made of the servlet
    * @param response the HttpServletResponse object that contains the response the servlet sends to the client
    * @return a String representing the path to the checkout page
    * @throws ServletException if the request for the POST could not be handled
    * @throws IOException if an input or output error is detected when the servlet handles the request
    */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        return "/jsp/checkout.jsp";
    }
}
