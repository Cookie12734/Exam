package scoremanager.main;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import bean.Score;
import bean.Teacher;
import dao.ScoreDao;
import tool.Action;

public class ScoreUpdateExecuteAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        String schoolCd = teacher.getSchool().getSchoolCd();

        String entYearStr = req.getParameter("entYear");
        int entYear = (entYearStr != null && !entYearStr.isEmpty()) ? Integer.parseInt(entYearStr) : 0;
        String classNum = req.getParameter("classNum");
        if (classNum == null) classNum = "";

        // パラメータをすべて配列として取得する
        String[] studentNos = req.getParameterValues("studentNo");
        String[] subjectCds = req.getParameterValues("subjectCd");
        String[] nosStr     = req.getParameterValues("no");
        String[] points     = req.getParameterValues("point");

        boolean hasError = false;
        if (points != null) {
            for (String p : points) {
                if (p == null || p.trim().isEmpty()) continue;
                try {
                    // 小数点付きの入力も許容するため、一度 Double でパースする
                    double dVal = Double.parseDouble(p.trim());
                    int val = (int) dVal;
                    
                    // 0〜100の範囲チェックと、小数点以下が0以外の値（例:100.5）ではないかのチェック
                    if (val < 0 || val > 100 || dVal != val) {
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
            req.setAttribute("errorMsg", "点数は0〜100の整数で入力してください。");
            req.getRequestDispatcher("/scoremanager/score_update.jsp").forward(req, res);
            return;
        }

        ScoreDao sDao = new ScoreDao();

        // 送信された点数の数だけループさせる
        if (points != null) {
            for (int i = 0; i < points.length; i++) {
                String p = points[i].trim();
                if (p.isEmpty()) continue;

                // 単一送信と複数送信（画面によって異なる）両方に対応
                String sNo = (studentNos != null && studentNos.length == 1) ? studentNos[0] : studentNos[i];
                String subCd = (subjectCds != null && subjectCds.length == 1) ? subjectCds[0] : subjectCds[i];
                String noVal = (nosStr != null && nosStr.length == 1) ? nosStr[0] : nosStr[i];

                Score score = new Score();
                score.setStudentNo(sNo);
                score.setSchoolCd(schoolCd);
                score.setSubjectCd(subCd);
                score.setNo(Integer.parseInt(noVal));
                score.setPoint((int) Double.parseDouble(p)); // Double経由でintに変換
                score.setClassNum(classNum);

                sDao.save(score);
            }
        }

        req.setAttribute("entYear",   entYear);
        req.setAttribute("classNum",  classNum);
        req.setAttribute("subjectCd", (subjectCds != null && subjectCds.length > 0) ? subjectCds[0] : "");
        req.setAttribute("no",        (nosStr != null && nosStr.length > 0) ? Integer.parseInt(nosStr[0]) : 0);
        
        req.getRequestDispatcher("/scoremanager/score_update_done.jsp").forward(req, res);
    }
}
