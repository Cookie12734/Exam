package scoremanager.main;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import bean.Subject;
import dao.SubjectDAO;
import tool.Action;

public class SubjectDeleteExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        // 1. JSPのパラメータ名と一致させる（一覧画面で渡した "?cd=..."）
        String subjectCd = req.getParameter("cd");

        // 2. Beanの作成
        Subject subject = new Subject();
        subject.setSubjectCd(subjectCd);

        // 3. DAOを使って削除
        SubjectDAO sDao = new SubjectDAO();

        try {
            // ※ SubjectDAOに delete メソッドが実装されている必要があります
            sDao.delete(subject);

            // 4. 削除成功後、完了ページへリダイレクト（コンテキストパスを付与）
            res.sendRedirect(req.getContextPath() + "/scoremanager/subject_delete_done.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
