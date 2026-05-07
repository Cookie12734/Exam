package scoremanager.main;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDAO; // DAOのクラス名（大文字のDAO）に合わせてください
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class SubjectCreateExecuteAction extends Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher)session.getAttribute("user");

        // 1. リクエストパラメータの取得（JSPのname属性に合わせてください）
        String subjectCd = req.getParameter("code");
        String subjectName = req.getParameter("name");

        // 2. Beanの作成とデータセット
        Subject subject = new Subject();
        
        // Subject.javaのメソッド名に合わせて修正
        subject.setSubjectCd(subjectCd);
        subject.setSubjectName(subjectName);
        
        // Subject.javaはString型のSchoolCdを持っているので、
        // teacherオブジェクトからSchoolCdを取得してセットします
        subject.setSchoolCd(teacher.getSchool().getSchoolCd());

        // 3. DAOを使って保存
        SubjectDAO sDao = new SubjectDAO();
        sDao.save(subject);

        // 4. 次の画面へ（完了画面など）
        req.getRequestDispatcher("subject_create_done.jsp").forward(req, res);
    }
}