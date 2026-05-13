package scoremanager.main;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import bean.GradeScore;
import bean.Teacher;
import dao.GradeScoreDao;
import tool.Action;

public class ScoreSaveAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // セッションからユーザー情報を取得
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        //  隠しフィールドから科目コードと回数を取得
        String subjectCd = req.getParameter("subject_cd");
        int num = 0;
        try {
            num = Integer.parseInt(req.getParameter("num"));
        } catch (NumberFormatException e) {
            // パラメータが不正な場合はエラー画面へ
            req.setAttribute("errors", "回数が正しくありません");
            req.getRequestDispatcher("error.jsp").forward(req, res);
            return;
        }

        //  画面から送られてきた全学生の点数をリストにまとめる
        List<GradeScore> list = new ArrayList<>();
        
        // リクエストパラメータのキー（point_学生番号）を全走査
        Enumeration<String> names = req.getParameterNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            if (name.startsWith("point_")) {
                // name属性から学生番号を抽出
                String studentNo = name.replace("point_", "");
                
                // 学生番号の最大文字数（10文字）チェック
                if (studentNo.length() > 10) {
                    req.setAttribute("errors", "学生番号は10文字以内で入力してください");
                    req.getRequestDispatcher("error.jsp").forward(req, res);
                    return;
                }

                int point = 0;
                try {
                    point = Integer.parseInt(req.getParameter(name));
                } catch (NumberFormatException e) {
               
                    req.setAttribute("errors", "点数は数値で入力してください");
                    req.getRequestDispatcher("error.jsp").forward(req, res);
                    return;
                }
                
                // 登録用のBeanを作成してリストに追加
                GradeScore gs = new GradeScore();
                gs.setStudentNo(studentNo);
                gs.setPoint(point);

                list.add(gs);
            }
        }

        //  DAOを使ってDBに保存
        GradeScoreDao dao = new GradeScoreDao();
        boolean isSuccess = dao.save(list, teacher.getSchool(), subjectCd, num);

        if (isSuccess) {
            // 保存成功：完了画面（または一覧画面）へ
            req.getRequestDispatcher("/scoremanager/score_save_done.jsp").forward(req, res);
        } else {
            // 保存失敗：エラー画面などへ
            req.setAttribute("errors", "成績の保存に失敗しました");
            req.getRequestDispatcher("error.jsp").forward(req, res);
        }
    }
}
