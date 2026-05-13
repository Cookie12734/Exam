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
        String schoolCd = teacher.getSchool().getSchoolCd();

        // 画面からの入力を受け取る
        String oldSubjectCd = req.getParameter("old_cd"); 
        String newSubjectCd = req.getParameter("cd");     // 入力された新しいコード
        String subjectName = req.getParameter("name");   // 入力された新しい名前

        Subject subject = new Subject();
        subject.setSubjectCd(newSubjectCd);
        subject.setSubjectName(subjectName);
        subject.setSchoolCd(schoolCd);

        SubjectDAO sDao = new SubjectDAO();
        
        // 科目コードが変更された場合のみ重複チェックを行う
        if (!oldSubjectCd.equals(newSubjectCd)) {
            Subject existingSubject = sDao.get(newSubjectCd, schoolCd);
            if (existingSubject != null) {
                // すでに同じ科目コードが存在している場合
                req.setAttribute("error", "科目コードが重複しています");
                // 画面で入力した値を復元するためにセット（変更前のコードをold_cdとして維持）
                subject.setSubjectCd(oldSubjectCd); // 入力エラー時は元のコードに戻して表示するか、入力値のままにするかはお好みですが、hidden用にセットします
                req.setAttribute("subject", subject);
                req.setAttribute("inputCd", newSubjectCd); // 入力した間違ったコードも画面に戻す
                
                req.getRequestDispatcher("/scoremanager/subject_update.jsp").forward(req, res);
                return;
            }
        }

        sDao.update(subject, oldSubjectCd);

        // 更新完了後、完了画面へリダイレクト
        res.sendRedirect(req.getContextPath() + "/scoremanager/subject_update_done.jsp");
    }
}
