package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;

public class SubjectDAO extends Dao {
    /**
     * 科目コードを指定して1件取得（重複チェック用）
     */
    public Subject get(String cd) throws Exception {
        Subject subject = null;
        // データベース接続
        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement("SELECT * FROM SUBJECT WHERE CD = ?")) {
            st.setString(1, cd);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                subject = new Subject();
                // Subject.javaの新しいメソッド名に合わせて修正
                subject.setSubjectCd(rs.getString("CD"));
                subject.setSubjectName(rs.getString("NAME"));
                // SchoolCdも保持する場合
                subject.setSchoolCd(rs.getString("SCHOOL_CD"));
            }
        }
        return subject;
    }

    /**
     * 新規登録
     */
    public boolean save(Subject subject) throws Exception {
        int count = 0;
        // DBのカラム名に合わせてSQLを調整してください（ここではCD, NAME, SCHOOL_CDと仮定）
        String sql = "INSERT INTO SUBJECT (CD, NAME, SCHOOL_CD) VALUES (?, ?, ?)";
        
        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            // Subject.javaの新しいメソッド名に合わせて修正
            st.setString(1, subject.getSubjectCd());
            st.setString(2, subject.getSubjectName());
            st.setString(3, subject.getSchoolCd());
            
            count = st.executeUpdate();
        }
        return count > 0;
    }





    /**
     * 指定された学校の科目一覧を取得します。
     * 
     * @param school 学校インスタンス
     * @return 科目のリスト
     * @throws Exception
     */
    public List<Subject> filter(School school) throws Exception {
        List<Subject> list = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet rSet = null;

        try {
            // 学校コードを条件にして科目テーブルから検索
            String sql = "SELECT * FROM SUBJECT WHERE SCHOOL_CD = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, school.getSchoolCd());
            rSet = statement.executeQuery();

            while (rSet.next()) {
                Subject subject = new Subject();
                // データベースの列名とBeanのプロパティ名は環境に合わせて修正してください
                subject.setSubjectCd(rSet.getString("SUBJECT_CD"));
                subject.setSubjectName(rSet.getString("SUBJECT_NAME"));
                // subject.setSchool(school); // 必要に応じて学校オブジェクトもセット
                
                list.add(subject);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (rSet != null) {
                try {
                    rSet.close();
                } catch (Exception e) {
                    throw e;
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (Exception e) {
                    throw e;
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    throw e;
                }
            }
        }

        return list;
    }
}
