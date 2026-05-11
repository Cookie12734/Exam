<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="../common/base.jsp">
    <c:param name="title">得点管理システム</c:param>
    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-norma">科目情報削除</h2>

            <div class="mb-3">
                <p>科目コード「${cd}」を削除してもよろしいですか？</p>
                <%-- 科目名も表示したい場合は、Actionクラスで setAttribute("name", ...) などを追加し、ここで ${name} を表示します --%>
            </div>

            <form action="SubjectDeleteExecute.action" method="post">
                <%-- Executeアクションへ渡すパラメータ --%>
                <input type="hidden" name="cd" value="${cd}">
                
                <button class="btn btn-danger" type="submit" id="delete-button">削除</button>
            </form>

            <div class="mt-3">
                <a href="SubjectList.action">戻る</a>
            </div>
        </section>
    </c:param>
</c:import>
