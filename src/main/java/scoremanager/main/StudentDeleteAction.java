package scoremanager.main;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import bean.Student;
import dao.StudentDao;
import tool.Action;

public class StudentDeleteAction extends Action {
    @Override
    public void execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // GETパラメータから学生番号を取得
        String no = req.getParameter("no");
        
        // DAOを使って該当の学生情報を取得
        StudentDao sDao = new StudentDao();
        Student student = sDao.get(no); // ※getメソッドが存在する前提
        
        // JSPにデータを渡してフォワード
        req.setAttribute("student", student);
        req.getRequestDispatcher("../student_delete.jsp").forward(req, res);
    }
}
