package scoremanager.main;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import bean.Student;
import dao.StudentDao;
import tool.Action;

public class StudentDeleteExecuteAction extends Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // POSTされた学生番号を取得
        String no = req.getParameter("no");
        
        // DAOを使って削除処理を実行
        StudentDao sDao = new StudentDao();
        
        // Studentモデルのプロパティ（studentNo）に合わせてメソッドを修正
        Student student = new Student();
        student.setStudentNo(no);
        sDao.delete(student);
        
        // mainパッケージ内のActionへ正しくフォワードされるようにパスを修正
        req.getRequestDispatcher("StudentList.action").forward(req, res);
    }
}
