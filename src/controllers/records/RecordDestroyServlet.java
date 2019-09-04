package controllers.records;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Record;
import utils.DBUtil;

/**
 * Servlet implementation class RecordDestroyServlet
 */
@WebServlet("/records/destroy")
public class RecordDestroyServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public RecordDestroyServlet() {
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

            // スコープからレコードのIDを取得して
            // 該当のIDのレコードをデータベースから取得
            Record r = em.find(Record.class, Integer.parseInt(request.getParameter("id")));

            em.getTransaction().begin();
            em.remove(r);       // データ削除
            em.getTransaction().commit();
            em.close();

            // フラッシュメッセージの設定
            request.getSession().setAttribute("flush", "削除が完了しました。");

            // indexページへリダイレクト
            response.sendRedirect(request.getContextPath() + "/index.html");
        }
    }

}
