package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import bean.GradeScore;
import bean.School;
import bean.Subject;
import bean.Teacher;
import dao.ClassNumDao;
import dao.GradeScoreDao;
import dao.SubjectDAO;
import tool.Action;

public class ScoreListExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        School school = teacher.getSchool();

        // 1. 再表示用にセレクトボックスのデータを取得してセットする
        List<Integer> entYearSet = new ArrayList<>();
        int currentYear = LocalDate.now().getYear();
        for (int i = currentYear - 10; i <= currentYear + 1; i++) {
            entYearSet.add(i);
        }
        ClassNumDao cDao = new ClassNumDao();
        List<String> classNumSet = cDao.filter(school);
        
        SubjectDAO sDao = new SubjectDAO();
        List<Subject> subjectList = sDao.filter(school.getSchoolCd());

        // 科目の重複を排除
        List<Subject> uniqueSubjectList = new ArrayList<>();
        List<String> existingSubjectCds = new ArrayList<>();
        for (Subject subject : subjectList) {
            if (!existingSubjectCds.contains(subject.getSubjectCd())) {
                uniqueSubjectList.add(subject);
                existingSubjectCds.add(subject.getSubjectCd());
            }
        }

        request.setAttribute("entYearSet", entYearSet);
        request.setAttribute("classNumSet", classNumSet);
        request.setAttribute("subjectList", uniqueSubjectList);

        // 2. 検索条件を取得
        String entYearStr = request.getParameter("entYear");
        String classNum = request.getParameter("classNum");
        String subjectCd = request.getParameter("subjectCd");
        String numStr = request.getParameter("num");

        if (entYearStr != null && classNum != null && subjectCd != null && numStr != null) {
            
            int entYear = Integer.parseInt(entYearStr);
            int no = Integer.parseInt(numStr);
            
            // 3. 入学年度、クラス、科目のいずれかが未選択（0）の場合のエラーハンドリング
            if (entYear == 0 || classNum.equals("0") || subjectCd.equals("0")) {
                request.setAttribute("errors", "入学年度とクラスと科目を選択してください");
            } else {
                // 条件が満たされている場合のみ検索を実行
                GradeScoreDao tDao = new GradeScoreDao();
                List<GradeScore> testList = tDao.filter(school, entYear, classNum, subjectCd, no);
                request.setAttribute("testList", testList);
            }
        }

        // JSPへフォワード
        request.getRequestDispatcher("/scoremanager/score_list.jsp").forward(request, response);
    }
}
