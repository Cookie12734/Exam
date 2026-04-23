package scoremanager.main;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;

@WebServlet("/scoremanager/main/StudentCreate.action")
public class StudentCreateAction extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        try {
            // ログイン済みの教員の学校情報を元にクラス一覧を取得
            if (teacher != null && teacher.getSchool() != null) {
                ClassNumDao cNumDao = new ClassNumDao();
                List<String> classNumList = cNumDao.filter(teacher.getSchool());
                req.setAttribute("class_num_set", classNumList);
            }

            // 入学年度の候補を生成（例: 現在の年の前後10年）
            int currentYear = LocalDate.now().getYear();
            List<Integer> entYearSet = new ArrayList<>();
            for (int i = currentYear - 10; i <= currentYear + 1; i++) {
                entYearSet.add(i);
            }
            req.setAttribute("ent_year_set", entYearSet);

        } catch (Exception e) {
            req.setAttribute("errorMessage", "データの取得に失敗しました。");
        }

        // 新規登録画面（JSP）へフォワード
        req.getRequestDispatcher("student_create.jsp").forward(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // フォームから送信されたパラメータの取得
        String entYearStr = req.getParameter("ent_year");
        String studentNo = req.getParameter("student_no");
        String studentName = req.getParameter("student_name");
        String classNum = req.getParameter("class_num");

        // バリデーションチェック (簡易チェック)
        if (entYearStr == null || entYearStr.isEmpty() ||
            studentNo == null || studentNo.isEmpty() ||
            studentName == null || studentName.isEmpty() ||
            classNum == null || classNum.isEmpty()) {
            
            req.setAttribute("errorMessage", "すべての項目を入力してください。");
            doGet(req, res); // リストを再取得するためにdoGetを呼び出す
            return;
        }

        try {
            int entYear = Integer.parseInt(entYearStr);

            // Studentオブジェクトの生成と値のセット
            Student student = new Student();
            student.setEntYear(entYear);
            student.setStudentNo(studentNo);
            student.setStudentName(studentName);
            student.setClassNum(classNum);
            student.setAttend(true); // デフォルトで在学中に設定

            // セッションから学校情報を取得してセット（必要に応じて実装）
            HttpSession session = req.getSession();
            Teacher teacher = (Teacher) session.getAttribute("user");
            if (teacher != null) {
                student.setSchool(teacher.getSchool());
            }

            // データベースへの登録処理
            StudentDao dao = new StudentDao();
            boolean success = dao.save(student);

            if (success) {
                // 登録完了後、学生一覧へリダイレクト
                res.sendRedirect("StudentList.action");
            } else {
                req.setAttribute("errorMessage", "登録に失敗しました。（学番が重複している可能性があります）");
                doGet(req, res);
            }

        } catch (NumberFormatException e) {
            // 入学年度が数値でない場合のエラーハンドリング
            req.setAttribute("errorMessage", "入学年度には数値を入力してください。");
            doGet(req, res);
        } catch (Exception e) {
            // その他の例外ハンドリング
            req.setAttribute("errorMessage", "システムエラーが発生しました。");
            doGet(req, res);
        }
    }
}
