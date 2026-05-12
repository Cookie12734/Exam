package scoremanager.main;

import bean.Score;
import bean.Teacher;
import dao.ScoreDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class ScoreUpdateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        String schoolCd = teacher.getSchool().getSchoolCd();

        int entYear      = Integer.parseInt(req.getParameter("entYear"));
        String classNum  = req.getParameter("classNum");
        String subjectCd = req.getParameter("subjectCd");
        int no           = Integer.parseInt(req.getParameter("no"));

        String[] studentNos = req.getParameterValues("studentNo");
        String[] points     = req.getParameterValues("point");

        boolean hasError = false;
        if (studentNos != null && points != null) {
            for (String p : points) {
                if (p == null || p.trim().isEmpty()) continue;
                try {
                    int val = Integer.parseInt(p.trim());
                    if (val < 0 || val > 100) {
                        hasError = true;
                        break;
                    }
                } catch (NumberFormatException e) {
                    hasError = true;
                    break;
                }
            }
        }

        if (hasError) {
            req.setAttribute("errorMsg",    "点数は0〜100の整数で入力してください。");
            req.setAttribute("entYear",     entYear);
            req.setAttribute("classNum",    classNum);
            req.setAttribute("subjectCd",   subjectCd);
            req.setAttribute("no",          no);
            req.setAttribute("studentNos",  studentNos);
            req.setAttribute("points",      points);
            req.getRequestDispatcher("/scoremanager/score_update.jsp").forward(req, res);
            return;  // ← 修正
        }

        ScoreDao sDao = new ScoreDao();

        if (studentNos != null) {
            for (int i = 0; i < studentNos.length; i++) {
                String p = (points != null && i < points.length) ? points[i].trim() : "";
                if (p.isEmpty()) continue;

                Score score = new Score();
                score.setStudentNo(studentNos[i]);
                score.setSchoolCd(schoolCd);
                score.setSubjectCd(subjectCd);
                score.setNo(no);
                score.setPoint(Integer.parseInt(p));
                score.setClassNum(classNum);

                sDao.save(score);
            }
        }

        req.setAttribute("entYear",   entYear);
        req.setAttribute("classNum",  classNum);
        req.setAttribute("subjectCd", subjectCd);
        req.setAttribute("no",        no);
        req.getRequestDispatcher("/scoremanager/score_update_done.jsp").forward(req, res);
    }
}
