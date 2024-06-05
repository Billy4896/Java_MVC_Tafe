package dispatchers;

import dispatchers.IDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;
import model.Tbooks;
import utility.AdmitBookStoreDAO;

public class DefaultAction implements IDispatcher {
    @PersistenceContext(unitName = "BookShopPU")
    protected EntityManager em;
    
    protected EntityManagerFactory emf;

    @Resource
    protected UserTransaction utx;
    
    /**
    * Executes the default action dispatcher to retrieve all books from the database using a JPU and sets them in the session.
    * 
    * @param request  the HttpServletRequest object that contains the request the client has made of the servlet
    * @param response the HttpServletResponse object that contains the response the servlet sends to the client
    * @return a String representing the path to the next page (either an error page or the titles page)
    * @throws ServletException if the request for the POST could not be handled
    * @throws IOException if an input or output error is detected when the servlet handles the request
    */
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        String nextPage = "/jsp/error.jsp";
        try {
            emf = Persistence.createEntityManagerFactory("BookShopPU");
            em = emf.createEntityManager();
            List<Tbooks> books = em.createNamedQuery("Tbooks.findAll", Tbooks.class).getResultList();
            emf.close();
            session.setAttribute("Books", books);
            nextPage = "/jsp/titles.jsp";
        } catch (Exception ex) {
            request.setAttribute("result", ex.getMessage());
        } finally {
            return nextPage;
        }
    }
}

