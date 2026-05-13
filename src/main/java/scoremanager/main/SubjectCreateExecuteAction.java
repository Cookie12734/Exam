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

        String subjectCd = req.getParameter("cd"); 
        String subjectName = req.getParameter("name");

        SubjectDAO sDao = new SubjectDAO();

        //  科目コードの文字数チェック
        if (subjectCd == null || subjectCd.length() != 3) {
            req.setAttribute("error", "科目コードは3文字で入力してください");
            req.setAttribute("cd", subjectCd);
            req.setAttribute("name", subjectName);
            // scoremanager直下を探すよう絶対パスに変更
            req.getRequestDispatcher("/scoremanager/subject_create.jsp").forward(req, res);
            return;
        }

        //  科目コードの重複チェック

        Subject existingSubject = null;
        if (teacher != null && teacher.getSchool() != null) {
            existingSubject = sDao.get(subjectCd, teacher.getSchool().getSchoolCd());
        }

        if (existingSubject != null) {
            req.setAttribute("error", "科目コードが重複しています");
            req.setAttribute("cd", subjectCd);
            req.setAttribute("name", subjectName);
            // scoremanager直下を探す
            req.getRequestDispatcher("/scoremanager/subject_create.jsp").forward(req, res);
            return;
        }

        // 3. Beanの作成とセット
        Subject subject = new Subject();
        subject.setSubjectCd(subjectCd);
        subject.setSubjectName(subjectName);
        
        if (teacher != null && teacher.getSchool() != null) {
            subject.setSchoolCd(teacher.getSchool().getSchoolCd());
        }

        //  データ保存
        try {
            sDao.save(subject);
            
            res.sendRedirect(req.getContextPath() + "/scoremanager/subject_create_done.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            throw e; 
        }
    }
}
