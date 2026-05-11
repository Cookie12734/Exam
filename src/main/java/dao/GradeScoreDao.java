package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.GradeScore;
import bean.School;

public class GradeScoreDao extends Dao {
    public List<GradeScore> filter(School school, int entYear, String classNum, String subjectCd, int num) throws Exception {
        List<GradeScore> list = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement statement = null;
        try {
            // 回数（t.NO）の条件を追加したSQL
            String sql = "SELECT s.ENT_YEAR, s.CLASS_NUM, s.STUDENT_NO, s.STUDENT_NAME, sub.SUBJECT_NAME, t.POINT " +
                         "FROM STUDENT s " +
                         "JOIN TEST t ON s.STUDENT_NO = t.STUDENT_NO AND s.SCHOOL_CD = t.SCHOOL_CD " +
                         "JOIN SUBJECT sub ON t.SUBJECT_CD = sub.SUBJECT_CD AND t.SCHOOL_CD = sub.SCHOOL_CD " +
                         "WHERE s.SCHOOL_CD = ? AND s.ENT_YEAR = ? AND s.CLASS_NUM = ? AND sub.SUBJECT_CD = ? AND t.NO = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, school.getSchoolCd());
            statement.setInt(2, entYear);
            statement.setString(3, classNum);
            statement.setString(4, subjectCd);
            statement.setInt(5, num); // 回数をセット
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                GradeScore gs = new GradeScore();
                gs.setEntYear(rs.getInt("ENT_YEAR"));
                gs.setClassNum(rs.getString("CLASS_NUM"));
                gs.setStudentNo(rs.getString("STUDENT_NO"));
                gs.setStudentName(rs.getString("STUDENT_NAME"));
                gs.setSubjectName(rs.getString("SUBJECT_NAME"));
                gs.setPoint(rs.getInt("POINT"));
                list.add(gs);
            }
        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        return list;
    }
}
