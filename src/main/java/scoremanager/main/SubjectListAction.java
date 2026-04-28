package scoremanager.main;

import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import bean.Subject;
import tool.Action;

public class SubjectListAction extends Action {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // TODO: 実際にはSubjectDaoを使用してデータベースから科目一覧を取得する処理を記述します
        List<Subject> subjectList = new ArrayList<>();
        
        // ダミーデータの追加（動作確認用）
        Subject subject1 = new Subject();
        subject1.setSubjectCd("A01");
        subject1.setSubjectName("国語");
        subjectList.add(subject1);
        
        Subject subject2 = new Subject();
        subject2.setSubjectCd("A02");
        subject2.setSubjectName("数学");
        subjectList.add(subject2);

        // リクエストスコープに科目リストをセット
        req.setAttribute("subjectList", subjectList);

        // JSPへフォワード
        req.getRequestDispatcher("/scoremanager/Subjectlist.jsp").forward(req, res);
    }
}
