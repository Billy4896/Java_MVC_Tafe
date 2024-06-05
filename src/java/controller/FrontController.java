package controller;

import dispatchers.AddToCartAction;
import dispatchers.CheckoutAction;
import dispatchers.ContinueAction;
import dispatchers.DefaultAction;
import dispatchers.IDispatcher;
import dispatchers.UpdateCartAction;
import dispatchers.ViewCartAction;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
//import dispatchers.*;
import model.Book;
import model.CartItem;
import model.Tbooks;
import utility.AdmitBookStoreDAO;


public class FrontController extends HttpServlet {
    @PersistenceContext(unitName = "BookShopPU")
    private EntityManager em;
    @Resource
    private javax.transaction.UserTransaction utx;

    private final HashMap actions = new HashMap();

    //Initialize global variables
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        actions.put(config.getInitParameter("default_action"), new DefaultAction());
        actions.put(config.getInitParameter("add_to_cart_action"), new AddToCartAction());
        actions.put(config.getInitParameter("checkout_action"), new CheckoutAction());
        actions.put(config.getInitParameter("continue_action"), new ContinueAction());
        actions.put(config.getInitParameter("update_cart_action"), new UpdateCartAction());
        actions.put(config.getInitParameter("view_cart_action"), new ViewCartAction());
    }
    
    
    //Process the HTTP Get request
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.err.println("doGet()");
        doPost(request, response);

    }

    // Process the HTTP Post request
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    
    /**
    * Unified method to process both GET and POST requests.
    * Determines the appropriate action to take based on the "action" parameter and forwards the request to the corresponding page.
    * 
    * @param request  the HttpServletRequest object that contains the request the client has made of the servlet
    * @param response the HttpServletResponse object that contains the response the servlet sends to the client
    * @throws ServletException if the request could not be handled
    * @throws IOException if an input or output error is detected when the servlet handles the request
    */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String action = request.getParameter("action");
        IDispatcher disp = (IDispatcher) ((action == null) ? actions.get("default") : actions.get(action));
        
        if (disp != null) {
            String next_page = disp.execute(request, response);
            request.getRequestDispatcher(next_page).forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    //Get Servlet information
    public String getServletInfo() {
        return "controller.FrontController Information";
    }

    public void persist(Object object) {
        try {
            utx.begin();
            em.persist(object);
            utx.commit();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", e);
            throw new RuntimeException(e);
        }
    }
    
     // New method to get all books
    public List<Tbooks> getAllBooks() {
        return em.createNamedQuery("Tbooks.findAll", Tbooks.class).getResultList();
    }

}
