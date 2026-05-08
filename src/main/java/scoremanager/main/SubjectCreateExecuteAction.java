package scoremanager.main;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDAO;
import tool.Action;

public class SubjectCreateExecuteAction extends Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher)session.getAttribute("user");

        // 1. JSPの name="cd" と一致させる
        String subjectCd = req.getParameter("cd"); 
        String subjectName = req.getParameter("name");

        // 2. Beanの作成
        Subject subject = new Subject();
        subject.setSubjectCd(subjectCd);
        subject.setSubjectName(subjectName);
        
        // ログインユーザーの学校情報をセット
        if (teacher != null && teacher.getSchool() != null) {
            subject.setSchoolCd(teacher.getSchool().getSchoolCd());
        }

        // 3. DAOを使って保存
        SubjectDAO sDao = new SubjectDAO();
        
        try {
            sDao.save(subject);
            // 4. 保存成功後、科目一覧へ飛ばす（パスを正確に指定）
            // ※ファイルの場所に合わせて /scoremanager/subject_list.jsp などに変更してください
            res.sendRedirect("../subject_create_done.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            // 本来はここでエラーページへ飛ばすが、error.jspがないならひとまず現状維持
            throw e; 
        }
    }
}