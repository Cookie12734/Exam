package scoremanager.main;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import bean.GradeScore;
import bean.Teacher;
import dao.GradeScoreDao;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import tool.Action;

public class ScoreSaveAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // 1. セッションからユーザー情報を取得
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // 2. 隠しフィールドから科目コードと回数を取得
        String subjectCd = req.getParameter("subject_cd");
        int num = Integer.parseInt(req.getParameter("num"));

        // 3. 画面から送られてきた全学生の点数をリストにまとめる
        List<GradeScore> list = new ArrayList<>();
        
        // リクエストパラメータのキー（point_学生番号）を全走査
        Enumeration<String> names = req.getParameterNames();
        while (names.hasMoreElements()) {
            String name = names.nextElement();
            if (name.startsWith("point_")) {
                // name属性から学生番号を抽出（例："point_2221100" -> "2221100"）
                String studentNo = name.replace("point_", "");
                int point = Integer.parseInt(req.getParameter(name));
                
                // 登録用のBeanを作成してリストに追加
                GradeScore gs = new GradeScore();
                gs.setStudentNo(studentNo);
                gs.setPoint(point);
                // クラス番号など、保存に必要な他の情報があれば適宜セット
                list.add(gs);
            }
        }

        // 4. DAOを使ってDBに保存
        GradeScoreDao dao = new GradeScoreDao();
        boolean isSuccess = dao.save(list, teacher.getSchool(), subjectCd, num);

        // 5. 結果に応じた画面遷移
        if (isSuccess) {
            // 保存成功：完了画面（または一覧画面）へ
            req.getRequestDispatcher("/scoremanager/score_save_done.jsp").forward(req, res);
        } else {
            // 保存失敗：エラー画面などへ
            req.getRequestDispatcher("error.jsp").forward(req, res);
        }
    }
}