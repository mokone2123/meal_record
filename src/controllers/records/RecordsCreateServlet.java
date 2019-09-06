package controllers.records;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Record;
import models.User;
import models.validators.RecordValidator;
import utils.DBUtil;

/**
 * Servlet implementation class RecordsCreateServlet
 */
@WebServlet("/records/create")
public class RecordsCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public RecordsCreateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _token = (String)request.getParameter("_token");
        if(_token != null && _token.equals(request.getSession().getId())){
            EntityManager em = DBUtil.createEntityManager();

            // 作成する記録の日付を確認し、同じ日付の記録が既にある場合は、登録画面にリダイレクトする
            Date date = Date.valueOf(request.getParameter("date"));
            if((long)em.createNamedQuery("checkSameDateRecord", Long.class)
                        .setParameter("date", date)
                        .setParameter("user", (User)request.getSession().getAttribute("login_user"))
                        .getSingleResult() == 1){
                em.close();

                request.setAttribute("errors", "同じ日付の記録が登録されています");

                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/records/new.jsp");
                rd.forward(request, response);
            }
            else{

            Record r = new Record();

            r.setUser((User)request.getSession().getAttribute("login_user"));

            r.setDate(date);
            r.setBreakfast(request.getParameter("breakfast"));
            r.setLunch(request.getParameter("lunch"));
            r.setDinner(request.getParameter("dinner"));

            List<String> errors = RecordValidator.validate(r);
            if(errors.size() > 0){
                em.close();

                request.setAttribute("_token", request.getSession().getId());
                request.setAttribute("record", r);
                request.setAttribute("errors", errors);

                RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/records/new.jsp");
                rd.forward(request, response);
            } else{
                em.getTransaction().begin();
                em.persist(r);
                em.getTransaction().commit();
                em.close();
                request.getSession().setAttribute("flush", "登録が完了しました。");

                response.sendRedirect(request.getContextPath() + "/index.html");
            }
            }
        }
    }

}
