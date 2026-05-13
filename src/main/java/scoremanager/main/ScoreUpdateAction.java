package scoremanager.main;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import bean.Score;
import bean.Teacher;
import dao.ScoreDao;
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
        
        // 学生番号パラメータを取得
        String studentNo = req.getParameter("studentNo");

        // 学生一覧＋既存の点数を取得
        ScoreDao sDao = new ScoreDao();
        List<Score> scoreList = sDao.filter(schoolCd, entYear, classNum, subjectCd, no);

        // 学生番号が指定されている場合、その学生のみにリストを絞り込む
        if (studentNo != null && !studentNo.isEmpty()) {
            scoreList.removeIf(score -> !score.getStudentNo().equals(studentNo));
        }

        // JSPに渡す
        req.setAttribute("scoreList",  scoreList);  
        req.setAttribute("entYear",    entYear);
        req.setAttribute("classNum",   classNum);
        req.setAttribute("subjectCd",  subjectCd);
        req.setAttribute("no",         no);

        req.getRequestDispatcher("/scoremanager/score_update.jsp").forward(req, res);
    }
}
