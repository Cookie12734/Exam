package scoremanager.main; 

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import bean.GradeScore;
import bean.Teacher;
import dao.GradeScoreDao;
import tool.Action; 

public class ScoreCreateExecuteAction extends Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // パラメータの取得

        int entYear = Integer.parseInt(req.getParameter("ent_year"));
        String classNum = req.getParameter("class_num");
        String subjectCd = req.getParameter("subject_cd");
        int num = Integer.parseInt(req.getParameter("num"));

        //  セッションからログインユーザー（教員）情報を取得
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        //  DAOを使って学生一覧と成績を取得
        GradeScoreDao dao = new GradeScoreDao();
        List<GradeScore> list = dao.filter(teacher.getSchool(), entYear, classNum, subjectCd, num);

        //  JSPで使うためにリクエスト属性にセット
        req.setAttribute("score_list", list);
        
        req.setAttribute("ent_year", entYear);
        req.setAttribute("class_num", classNum);
        req.setAttribute("subject_cd", subjectCd);
        req.setAttribute("num", num);

        // 5. 成績登録画面（JSP）へフォワード
        req.getRequestDispatcher("/scoremanager/score_insert.jsp").forward(req, res);
    }
}