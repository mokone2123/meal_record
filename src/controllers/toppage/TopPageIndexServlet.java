package controllers.toppage;

import java.io.IOException;
import java.util.Iterator;
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
import utils.DBUtil;

/**
 * Servlet implementation class TopPageIndexServlet
 */
@WebServlet("/index.html")
public class TopPageIndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public TopPageIndexServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        User login_user = (User)request.getSession().getAttribute("login_user");

        int page;
        try{
            page = Integer.parseInt(request.getParameter("page"));
        } catch(Exception e){
            page = 1;
        }

        List<Record> records = em.createNamedQuery("getMyAllRecords", Record.class)
                                .setParameter("user", login_user)
                                .setFirstResult(10 * (page - 1))
                                .setMaxResults(10)
                                .getResultList();

        long records_count = (long)em.createNamedQuery("getMyRecordsCount", Long.class)
                .setParameter("user", login_user)
                .getSingleResult();

        em.close();

        // textarea内の改行をbrに置き換える
        Iterator<Record> i_records = records.iterator();
        while(i_records.hasNext()){
            Record r = i_records.next();
            r.setBreakfast(r.getBreakfast().replace("\r\n" ,"<br/>"));
            r.setLunch(r.getLunch().replace("\r\n" ,"<br/>"));
            r.setDinner(r.getDinner().replace("\r\n" ,"<br/>"));
        }

        // 各パラメータを設定
        request.setAttribute("records", records);
        request.setAttribute("index", true);
        request.setAttribute("records_count", records_count);
        request.setAttribute("page", page);

        // フラッシュメッセージの設定
        if(request.getSession().getAttribute("flush") != null) {
            request.setAttribute("flush", request.getSession().getAttribute("flush"));
            request.getSession().removeAttribute("flush");
        }

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/topPage/index.jsp");
        rd.forward(request, response);
    }

}
