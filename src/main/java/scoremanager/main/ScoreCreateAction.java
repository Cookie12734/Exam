package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import bean.Subject;
import bean.Teacher;
import dao.ClassNumDao;
import dao.SubjectDAO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class ScoreCreateAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // 1. セッションからユーザー情報を取得
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // 2. DAOをインスタンス化
        SubjectDAO sDao = new SubjectDAO();
        ClassNumDao cDao = new ClassNumDao();

        // 3. ログインユーザーの学校コードを元にデータを取得
        // SubjectDAO.java の filter(String schoolCd) を使用
        List<Subject> subjects = sDao.filter(teacher.getSchool().getSchoolCd());
        List<String> class_list = cDao.filter(teacher.getSchool());

        // 4. 入学年度プルダウン用のリスト作成（現在の年から前後10年程度）
        List<Integer> entYearList = new ArrayList<>();
        int year = LocalDate.now().getYear();
        for (int i = year - 10; i <= year; i++) {
            entYearList.add(i);
        }

        // 5. JSPに渡すデータをリクエスト属性にセット
        req.setAttribute("subjects", subjects);
        req.setAttribute("class_num_set", class_list);
        req.setAttribute("ent_year_set", entYearList);

        // 6. 検索画面（score_registration.jsp）へフォワード
        req.getRequestDispatcher("/scoremanager/score_registration.jsp").forward(req, res);
    }
}