package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import bean.Subject;

public class SubjectDAO extends Dao {
    /**
     * 科目コードを指定して1件取得（重複チェック用）
     */
    public Subject get(String cd) throws Exception {
        Subject subject = null;
        // データベース接続
        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement("SELECT * FROM SUBJECT WHERE SUBJECT_CD = ?")) {
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
        String sql = "INSERT INTO SUBJECT (SUBJECT_CD, SUBJECT_NAME, SUBJECT_CD) VALUES (?, ?, ?)";
        
        try (Connection con = getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            // Subject.javaの新しいメソッド名に合わせて修正
            st.setString(1, subject.getSubjectCd());
            st.setString(2, subject.getSubjectName());
            st.setString(3, subject.getSubjectCd());
            
            count = st.executeUpdate();
        }
        return count > 0;
    }
}




    