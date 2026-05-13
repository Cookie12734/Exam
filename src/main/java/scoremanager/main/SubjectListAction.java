package scoremanager.main;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession; // 追加

import bean.Subject;
import bean.Teacher; // 追加
import dao.SubjectDAO; // 追加1
import tool.Action;

public class SubjectListAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        //  セッションからログイン中の教師情報を取得
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        //  教師の学校コードを取得
        String schoolCd = teacher.getSchool().getSchoolCd();

        //  SubjectDAOを使ってデータベースから科目一覧を取得
        SubjectDAO sDao = new SubjectDAO();
        List<Subject> subjectList = sDao.filter(schoolCd);

        //  リクエストスコープに科目リストをセット
        req.setAttribute("subjectList", subjectList);

        //  JSPへフォワード
        req.getRequestDispatcher("/scoremanager/Subjectlist.jsp").forward(req, res);
    }
}