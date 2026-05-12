package scoremanager.main;

import java.util.List;

import bean.Score;
import bean.Teacher;
import dao.ScoreDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class ScoreUpdateAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        String schoolCd = teacher.getSchool().getSchoolCd();

        // 成績一覧画面から送られてくるパラメータを取得
        int entYear      = Integer.parseInt(req.getParameter("entYear"));
        String classNum  = req.getParameter("classNum");
        String subjectCd = req.getParameter("subjectCd");
        int no           = Integer.parseInt(req.getParameter("no"));

        // 学生一覧＋既存の点数を取得
        ScoreDao sDao = new ScoreDao();
        List<Score> scoreList = sDao.filter(schoolCd, entYear, classNum, subjectCd, no);

        // JSPに渡す
        req.setAttribute("scoreList",  scoreList);  // score_update.jsp の ${scoreList} に合わせる
        req.setAttribute("entYear",    entYear);
        req.setAttribute("classNum",   classNum);
        req.setAttribute("subjectCd",  subjectCd);
        req.setAttribute("no",         no);

        req.getRequestDispatcher("/scoremanager/score_update.jsp").forward(req, res);
    }
}
