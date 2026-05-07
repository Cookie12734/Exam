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
/**修正用aaaaaaa**/
