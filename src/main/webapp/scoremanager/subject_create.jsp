<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- ヘッダーなどの共通部品があればここでインポートa --%>
<c:import url="../common/base.jsp">
    <c:param name="title">得点管理システム</c:param>
    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-norma">科目登録</h2>
            
            <%-- エラーメッセージの表示（仕様書の「重複」や「未入力」の警告用） --%>
            <c:if test="${not empty error}">
                <div class="alert alert-danger text-danger">
                    ${error}
                </div>
            </c:if>

            <form action="SubjectCreateExecute.action" method="post">
                <div class="mb-3">
                    <label class="form-label" for="subject-cd-input">科目コード</label>
                    <%-- 
                        value="${cd}" とすることで、エラーで戻ってきた時に入力内容を保持します。
                        required をつけることで、ブラウザ標準の「未入力チェック」が働きます。
                    --%>
                    <input class="form-control" type="text" id="subject-cd-input" name="cd" 
                           placeholder="科目コードを入力してください" value="${cd}" 
                           maxlength="3" required>
                </div>
                
                <div class="mb-3">
                    <label class="form-label" for="subject-name-input">科目名</label>
                    <input class="form-control" type="text" id="subject-name-input" name="name" 
                           placeholder="科目名を法入力してください" value="${name}" 
                           maxlength="20" required>
                </div>

                <button class="btn btn-primary" type="submit" id="regist-button">登録</button>
            </form>

            <div class="mt-3">
                <a href="SubjectList.action">戻る</a>
            </div>
        </section>
    </c:param>
</c:import>