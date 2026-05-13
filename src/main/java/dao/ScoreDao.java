package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.Score;

public class ScoreDao extends Dao {

	//成績一覧の取得
    public List<Score> filter(String schoolCd, int entYear, String classNum, String subjectCd, int no) throws Exception {
        List<Score> list = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet rSet = null;

        try {
            // LEFT JOINを使うことで、点数が登録されていない生徒も一覧に出るようになります
            String sql = "SELECT s.STUDENT_NO, s.STUDENT_NAME, t.POINT " +
                         "FROM STUDENT s " +
                         "LEFT JOIN TEST t ON s.STUDENT_NO = t.STUDENT_NO " +
                         "  AND t.SUBJECT_CD = ? AND t.NO = ? AND t.SCHOOL_CD = ? " +
                         "WHERE s.SCHOOL_CD = ? AND s.ENT_YEAR = ? AND s.CLASS_NUM = ? " +
                         "ORDER BY s.STUDENT_NO ASC";

            statement = connection.prepareStatement(sql);
            statement.setString(1, subjectCd);
            statement.setInt(2, no);
            statement.setString(3, schoolCd);
            statement.setString(4, schoolCd);
            statement.setInt(5, entYear);
            statement.setString(6, classNum);

            rSet = statement.executeQuery();

            while (rSet.next()) {
                Score test = new Score();
                test.setStudentNo(rSet.getString("STUDENT_NO"));
                test.setStudentName(rSet.getString("STUDENT_NAME"));
                // getIntはNULLの場合0を返します
                test.setPoint(rSet.getInt("POINT"));
                test.setNo(no);
                test.setSubjectCd(subjectCd);
                test.setSchoolCd(schoolCd);
                test.setClassNum(classNum);
                list.add(test);
            }
        } finally {
            if (rSet != null) rSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        return list;
    }

    public boolean save(Score test) throws Exception {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        int count = 0;

        try {
            // H2データベースのMERGE文。主キー4つすべてをKEYに指定
            String sql = "MERGE INTO TEST (STUDENT_NO, SCHOOL_CD, SUBJECT_CD, NO, POINT, CLASS_NUM) " +
                         "KEY (STUDENT_NO, SUBJECT_CD, NO, SCHOOL_CD) VALUES (?, ?, ?, ?, ?, ?)";

            statement = connection.prepareStatement(sql);
            statement.setString(1, test.getStudentNo());
            statement.setString(2, test.getSchoolCd());
            statement.setString(3, test.getSubjectCd());
            statement.setInt(4, test.getNo());
            statement.setInt(5, test.getPoint());
            statement.setString(6, test.getClassNum());

            count = statement.executeUpdate();
        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        return count > 0;
    }
}
