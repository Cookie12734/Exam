package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet; // 追加
import java.util.ArrayList; // 追加
import java.util.List; // 追加

import bean.Subject;

public class SubjectDAO extends Dao {

    /**
     * 科目一覧を取得する（学校コードで絞り込み）
     */
    public List<Subject> filter(String schoolCd) throws Exception {
        List<Subject> list = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet rSet = null;

        try {
            // H2データベースから、ログインユーザーと同じ学校コードのデータを取得
            statement = connection.prepareStatement("SELECT * FROM SUBJECT WHERE SCHOOL_CD = ?");
            statement.setString(1, schoolCd);
            rSet = statement.executeQuery();

            while (rSet.next()) {
                Subject subject = new Subject();
                // カラム名は提供されたテーブル定義に合わせています
                subject.setSubjectCd(rSet.getString("SUBJECT_CD"));
                subject.setSubjectName(rSet.getString("SUBJECT_NAME"));
                subject.setSchoolCd(rSet.getString("SCHOOL_CD"));
                list.add(subject);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (rSet != null) rSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        return list;
    }

    /**
     * 科目を保存する
     */
    public boolean save(Subject subject) throws Exception {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        int count = 0;

        try {
            String sql = "INSERT INTO SUBJECT (SCHOOL_CD, SUBJECT_CD, SUBJECT_NAME) VALUES (?, ?, ?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1, subject.getSchoolCd());
            statement.setString(2, subject.getSubjectCd());
            statement.setString(3, subject.getSubjectName());

            count = statement.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        return count > 0;
    }
}