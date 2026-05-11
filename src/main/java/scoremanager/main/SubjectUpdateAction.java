package scoremanager.main;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDAO;
import tool.Action;

public class SubjectUpdateAction extends Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        
        // 一覧画面から送られてくる科目コードを取得
        String subjectCd = req.getParameter("cd");
        String schoolCd = teacher.getSchool().getSchoolCd();

        SubjectDAO sDao = new SubjectDAO();
        Subject subject = sDao.get(subjectCd, schoolCd);

        // 取得したデータをリクエストスコープにセットして編集画面へ
        req.setAttribute("subject", subject);
        req.getRequestDispatcher("/scoremanager/subject_update.jsp").forward(req, res);
    }
}