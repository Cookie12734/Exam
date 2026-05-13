package scoremanager.main;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import bean.Subject;
import dao.SubjectDAO;
import tool.Action;

public class SubjectDeleteExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        //  JSPのパラメータ名と一致させる
        String subjectCd = req.getParameter("cd");

        //  Beanの作成
        Subject subject = new Subject();
        subject.setSubjectCd(subjectCd);

        //  DAOを使って削除
        SubjectDAO sDao = new SubjectDAO();

        try {

            sDao.delete(subject);

            //  削除成功後、完了ページへリダイレクト
            res.sendRedirect(req.getContextPath() + "/scoremanager/subject_delete_done.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
