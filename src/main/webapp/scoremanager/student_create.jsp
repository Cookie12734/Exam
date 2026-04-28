<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- base.jsp（ヘッダーやメニュー）を取り込む設定があればここに入れますa --%>
<c:import url="../common/base.jsp">
    <c:param name="title">得点管理システム</c:param>
    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-norma">学生情報登録</h2>
            
            <form action="StudentCreateExecute.action" method="post">
                <div class="mb-3">
                    <label class="form-label" for="student-ent-year-input">入学年度</label>
                    <input class="form-control" type="number" name="ent_year" 
                           placeholder="例: 2025" required>
                </div>
                
                <div class="mb-3">
                    <label class="form-label" for="student-no-input">学生番号</label>
                    <input class="form-control" type="text" name="no" 
                           placeholder="例: 1234567" required>
                </div>
                
                <div class="mb-3">
                    <label class="form-label" for="student-name-input">氏名</label>
                    <input class="form-control" type="text" name="name" 
                           placeholder="例: 大原 太郎" required>
                </div>
                
                <div class="mb-3">
                    <label class="form-label" for="student-class-num-select">クラス</label>
                    <select class="form-select" name="class_num">
                        <c:forEach var="num" items="${class_num_set}">
                            <option value="${num}">${num}</option>
                        </c:forEach>
                        <%-- 最初はテスト用に直接書いてもOKです --%>
                        <option value="101">101</option>
                        <option value="102">102</option>
                        <option value="201">201</option>
                    </select>
                </div>
                
                <button class="btn btn-primary" type="submit">登録</button>
            </form>
            
            <div class="mt-3">
                <a href="StudentList.action">戻る</a>
            </div>
        </section>
    </c:param>
</c:import>