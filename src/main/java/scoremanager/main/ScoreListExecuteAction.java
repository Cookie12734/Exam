package scoremanager.main;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import bean.GradeScore;
import bean.School;
import bean.Teacher;
import dao.GradeScoreDao;
import tool.Action;

public class ScoreListExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // セッションからログインユーザー（教員）の情報を取得し、学校情報を取得する
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        School school = teacher.getSchool();

        // ... (省略: セレクトボックス用のデータ取得処理)

        // 2. 検索条件を取得し、結果を取得する
        String entYearStr = request.getParameter("entYear");
        String classNum = request.getParameter("classNum");
        String subjectCd = request.getParameter("subjectCd");
        String numStr = request.getParameter("num");

        // nullチェックのみ行い、"0"が含まれていてもDAOを呼び出すように変更
        if (entYearStr != null && classNum != null && subjectCd != null && numStr != null) {
            
            int entYear = Integer.parseInt(entYearStr); // "0"の場合は0が代入される
            int no = Integer.parseInt(numStr);          // "0"の場合は0が代入される
            
            GradeScoreDao tDao = new GradeScoreDao();
            // 先ほど取得したschool変数を渡す
            List<GradeScore> testList = tDao.filter(school, entYear, classNum, subjectCd, no);
            request.setAttribute("testList", testList);
        }

        // JSPへフォワード
        request.getRequestDispatcher("/scoremanager/score_list.jsp").forward(request, response);
    }
}
