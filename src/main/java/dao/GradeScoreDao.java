package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.GradeScore;
import bean.School;

public class GradeScoreDao extends Dao {

    /**
     * 指定された条件に合致する学生と成績の一覧を取得します。
     * テスト仕様書 No.64（成績入力欄の表示）に対応。
     */
    public List<GradeScore> filter(School school, int entYear, String classNum, String subjectCd, int num) throws Exception {
        List<GradeScore> list = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement statement = null;
        try {
            // ポイント：LEFT JOIN を使い、TESTテーブルにレコードがなくても学生を表示させる
            String sql = "SELECT s.ENT_YEAR, s.CLASS_NUM, s.STUDENT_NO, s.STUDENT_NAME, sub.SUBJECT_NAME, t.POINT " +
                         "FROM STUDENT s " +
                         "JOIN SUBJECT sub ON sub.SUBJECT_CD = ? AND sub.SCHOOL_CD = s.SCHOOL_CD " +
                         "LEFT JOIN TEST t ON s.STUDENT_NO = t.STUDENT_NO " +
                         "  AND s.SCHOOL_CD = t.SCHOOL_CD " +
                         "  AND t.SUBJECT_CD = sub.SUBJECT_CD " +
                         "  AND t.NO = ? " +
                         "WHERE s.SCHOOL_CD = ? AND s.ENT_YEAR = ? AND s.CLASS_NUM = ? " +
                         "ORDER BY s.STUDENT_NO ASC";

            statement = connection.prepareStatement(sql);
            // プレースホルダの順番に注意
            statement.setString(1, subjectCd);
            statement.setInt(2, num);
            statement.setString(3, school.getSchoolCd());
            statement.setInt(4, entYear);
            statement.setString(5, classNum);

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                GradeScore gs = new GradeScore();
                gs.setEntYear(rs.getInt("ENT_YEAR"));
                gs.setClassNum(rs.getString("CLASS_NUM"));
                gs.setStudentNo(rs.getString("STUDENT_NO"));
                gs.setStudentName(rs.getString("STUDENT_NAME"));
                gs.setSubjectName(rs.getString("SUBJECT_NAME"));
                
                // 点数が未登録(NULL)の場合は -1 などを入れて未入力を判別可能にする（仕様に合わせて調整）
                int point = rs.getInt("POINT");
                if (rs.wasNull()) {
                    gs.setPoint(-1); 
                } else {
                    gs.setPoint(point);
                }
                
                list.add(gs);
            }
        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        return list;
    }

    /**
     * 入力された成績情報をDBに保存します。
     * テスト仕様書 No.68（DBに保存する）に対応。
     */
    public boolean save(List<GradeScore> list, School school, String subjectCd, int num) throws Exception {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        int count = 0;

        try {
            for (GradeScore gs : list) {
                // H2の MERGE INTO を使用（キーが重複していれば UPDATE、なければ INSERT）
                String sql = "MERGE INTO TEST (STUDENT_NO, SUBJECT_CD, SCHOOL_CD, NO, POINT, CLASS_NUM) " +
                             "KEY(STUDENT_NO, SUBJECT_CD, NO) VALUES (?, ?, ?, ?, ?, ?)";
                
                statement = connection.prepareStatement(sql);
                statement.setString(1, gs.getStudentNo());
                statement.setString(2, subjectCd);
                statement.setString(3, school.getSchoolCd());
                statement.setInt(4, num);
                statement.setInt(5, gs.getPoint());
                statement.setString(6, gs.getClassNum());
                
                count += statement.executeUpdate();
            }
        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        // リストの件数分更新されていれば成功
        return count > 0;
    }
}