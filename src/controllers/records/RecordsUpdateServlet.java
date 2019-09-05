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
import models.validators.RecordValidator;
import utils.DBUtil;

/**
 * Servlet implementation class RecordsUpdateServlet
 */
@WebServlet("/records/update")
public class RecordsUpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public RecordsUpdateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String _token = (String)request.getParameter("_token");
        if(_token != null && _token.equals(request.getSession().getId())) {
            EntityManager em = DBUtil.createEntityManager();

            Record r = em.find(Record.class, (Integer)(request.getSession().getAttribute("record_id")));

            // 編集前と日付が変わった場合、同じ日付の記録が登録されているかチェックする
            // 登録されていた場合は、編集画面にリダイレクトする
            Date date = Date.valueOf(request.getParameter("date"));
            if(date != r.getDate() && (long)em.createNamedQuery("getSameDateRecord", Long.class).setParameter("date", date).getSingleResult() == 1){
                    em.close();

                    request.setAttribute("record", r);
                    request.setAttribute("_token", request.getSession().getId());
                    request.setAttribute("errors", "同じ日付の記録が登録されています");

                    RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/records/edit.jsp");
                    rd.forward(request, response);
            } else{
                r.setDate(Date.valueOf(request.getParameter("date")));
                r.setBreakfast(request.getParameter("breakfast"));
                r.setLunch(request.getParameter("lunch"));
                r.setDinner(request.getParameter("dinner"));

                List<String> errors = RecordValidator.validate(r);
                if(errors.size() > 0){
                    em.close();

                    request.setAttribute("_token", request.getSession().getId());
                    request.setAttribute("record", r);
                    request.setAttribute("errors", errors);

                    RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/records/edit.jsp");
                    rd.forward(request, response);
                } else {
                    em.getTransaction().begin();
                    em.getTransaction().commit();
                    em.close();
                    request.getSession().setAttribute("flush", "更新が完了しました。");

                    request.getSession().removeAttribute("report_id");

                    response.sendRedirect(request.getContextPath() + "/index.html");
                    }
            }
        }
    }

}
