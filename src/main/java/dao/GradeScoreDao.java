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
    
    //学生番号を指定して該当学生の成績一覧を取得する
    public List<GradeScore> filter(School school, String studentNo) throws Exception {
        List<GradeScore> list = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement statement = null;
        try {
            // 学生テーブルをベースに、テストテーブルと科目テーブルを結合して対象学生のみ絞り込む
            String sql = "SELECT s.ENT_YEAR, s.CLASS_NUM, s.STUDENT_NO, s.STUDENT_NAME, sub.SUBJECT_NAME, t.SUBJECT_CD, t.NO, t.POINT " +
                         "FROM STUDENT s " +
                         "LEFT JOIN TEST t ON s.STUDENT_NO = t.STUDENT_NO AND s.SCHOOL_CD = t.SCHOOL_CD " +
                         "LEFT JOIN SUBJECT sub ON t.SUBJECT_CD = sub.SUBJECT_CD AND s.SCHOOL_CD = sub.SCHOOL_CD " +
                         "WHERE s.STUDENT_NO = ? AND s.SCHOOL_CD = ? " +
                         "ORDER BY t.SUBJECT_CD ASC, t.NO ASC";

            statement = connection.prepareStatement(sql);
            statement.setString(1, studentNo);
            statement.setString(2, school.getSchoolCd());

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                GradeScore gs = new GradeScore();
                gs.setEntYear(rs.getInt("ENT_YEAR"));
                gs.setClassNum(rs.getString("CLASS_NUM"));
                gs.setStudentNo(rs.getString("STUDENT_NO"));
                gs.setStudentName(rs.getString("STUDENT_NAME"));
                gs.setSubjectName(rs.getString("SUBJECT_NAME"));
                
                // 科目コードと回数をセットする
                gs.setSubjectCd(rs.getString("SUBJECT_CD"));
                gs.setNo(rs.getInt("NO"));
                
                // 点数が未登録(NULL)の場合は -1 などを設定
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

}
