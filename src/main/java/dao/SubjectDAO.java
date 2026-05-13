package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet; // 追加
import java.util.ArrayList; // 追加
import java.util.List; // 追加

import bean.Subject;

public class SubjectDAO extends Dao {

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
    
    public Subject get(String subjectCd, String schoolCd) throws Exception {
        Subject subject = null;
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet rSet = null;
 
        try {
            // 科目コードと学校コードの両方で一致を確認
            statement = connection.prepareStatement("SELECT * FROM SUBJECT WHERE SUBJECT_CD = ? AND SCHOOL_CD = ?");
            statement.setString(1, subjectCd);
            statement.setString(2, schoolCd);
            rSet = statement.executeQuery();
 
            if (rSet.next()) {
                subject = new Subject();
                subject.setSubjectCd(rSet.getString("SUBJECT_CD"));
                subject.setSubjectName(rSet.getString("SUBJECT_NAME"));
                subject.setSchoolCd(rSet.getString("SCHOOL_CD"));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (rSet != null) rSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        return subject;
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
    
    public boolean delete(Subject subject) throws Exception {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        int count = 0;

        try {
            String sql = "DELETE FROM SUBJECT WHERE SUBJECT_CD = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, subject.getSubjectCd());

            count = statement.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
        return count > 0;
    }
    
    public boolean update(Subject subject, String oldSubjectCd) throws Exception {
        Connection connection = getConnection();
        PreparedStatement statement = null;
        int count = 0;
 
        try {
            // SETで新しいCDとNAMEを指定し、WHEREで元のCDを指定する
            String sql = "UPDATE SUBJECT SET SUBJECT_CD = ?, SUBJECT_NAME = ? WHERE SUBJECT_CD = ? AND SCHOOL_CD = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, subject.getSubjectCd());   // 新しいコード
            statement.setString(2, subject.getSubjectName()); // 新しい名前
            statement.setString(3, oldSubjectCd);            // 更新前の古いコード
            statement.setString(4, subject.getSchoolCd());
 
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