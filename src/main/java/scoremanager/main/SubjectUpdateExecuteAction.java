package scoremanager.main;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDAO;
import tool.Action;

public class SubjectUpdateExecuteAction extends Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // 画面からの入力を受け取る
        String oldSubjectCd = req.getParameter("old_cd"); 
        String newSubjectCd = req.getParameter("cd");     // 入力された新しいコード
        String subjectName = req.getParameter("name");   // 入力された新しい名前

        Subject subject = new Subject();
        subject.setSubjectCd(newSubjectCd);
        subject.setSubjectName(subjectName);
        subject.setSchoolCd(teacher.getSchool().getSchoolCd());

        SubjectDAO sDao = new SubjectDAO();
 
        sDao.update(subject, oldSubjectCd);

        // 更新完了後、完了画面へリダイレクト
        res.sendRedirect(req.getContextPath() + "/scoremanager/subject_update_done.jsp");
    }
}