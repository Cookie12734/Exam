<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="../common/base.jsp">
    <c:param name="title">得点管理システム</c:param>
    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-norma">学生情報削除</h2>

            <form action="StudentDeleteExecute.action" method="post">
                <p>以下の学生情報を削除します。よろしいですか？</p>

                <div class="mb-3">
                    <label class="form-label">入学年度</label>
                    <p class="form-control-plaintext">${student.entYear}</p>
                </div>

                <div class="mb-3">
                    <label class="form-label">学生番号</label>
                    <p class="form-control-plaintext">${student.studentNo}</p>
                    <%-- 削除処理時に必要なためhiddenで学生番号を送信する --%>
                    <input type="hidden" name="no" value="${student.studentNo}">
                </div>

                <div class="mb-3">
                    <label class="form-label">氏名</label>
                    <p class="form-control-plaintext">${student.studentName}</p>
                </div>

                <div class="mb-3">
                    <label class="form-label">クラス</label>
                    <p class="form-control-plaintext">${student.classNum}</p>
                </div>

                <button class="btn btn-danger" type="submit">削除</button>
            </form>

            <div class="mt-3">
                <a href="StudentList.action">戻る</a>
            </div>
        </section>
    </c:param>
</c:import>
