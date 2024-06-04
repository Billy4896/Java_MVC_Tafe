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
    
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        String nextPage = "/jsp/error.jsp";
        try {
            emf = Persistence.createEntityManagerFactory("BookShopPU");
            em = emf.createEntityManager();
            List<Tbooks> books = em.createNamedQuery("Tbooks.findAll", Tbooks.class).getResultList();
//            emf.close();
            session.setAttribute("Books", books);
            nextPage = "/jsp/titles.jsp";
        } catch (Exception ex) {
            request.setAttribute("result", ex.getMessage());
        } finally {
            return nextPage;
        }
    }
    
    
}

