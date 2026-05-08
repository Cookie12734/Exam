package scoremanager.main;

import java.util.List;

import bean.Subject;
import bean.Teacher; // 追加
import dao.SubjectDAO; // 追加
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession; // 追加
import tool.Action;

public class SubjectListAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // 1. セッションからログイン中の教師情報を取得
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // 2. 教師の学校コードを取得
        String schoolCd = teacher.getSchool().getSchoolCd();

        // 3. SubjectDAOを使ってデータベースから科目一覧を取得
        SubjectDAO sDao = new SubjectDAO();
        List<Subject> subjectList = sDao.filter(schoolCd);

        // 4. リクエストスコープに科目リストをセット
        req.setAttribute("subjectList", subjectList);

        // 5. JSPへフォワード
        // ファイル名は image_6e6fc3.png のエラーメッセージに合わせています
        req.getRequestDispatcher("/scoremanager/Subjectlist.jsp").forward(req, res);
    }
}