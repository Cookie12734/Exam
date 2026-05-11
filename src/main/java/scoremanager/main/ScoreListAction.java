package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import bean.School;
import bean.Subject;
import bean.Teacher;
import dao.ClassNumDao;
import dao.SubjectDAO;
import tool.Action;

public class ScoreListAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        School school = teacher.getSchool();

        // セレクトボックス用のデータを取得
        List<Integer> entYearSet = new ArrayList<>();
        int currentYear = LocalDate.now().getYear();
        for (int i = currentYear - 10; i <= currentYear + 1; i++) {
            entYearSet.add(i);
        }

        ClassNumDao cDao = new ClassNumDao();
        List<String> classNumSet = cDao.filter(school);

        SubjectDAO sDao = new SubjectDAO();
        List<Subject> subjectList = sDao.filter(school.getSchoolCd());

        // リクエストスコープにセット
        request.setAttribute("entYearSet", entYearSet);
        request.setAttribute("classNumSet", classNumSet);
        request.setAttribute("subjectList", subjectList);

        // JSPへフォワード
        request.getRequestDispatcher("/scoremanager/score_list.jsp").forward(request, response);
    }
}
