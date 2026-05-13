<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">科目変更</c:param>
    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">科目変更</h2>

            <%-- エラーメッセージの表示 --%>
            <c:if test="${not empty error}">
                <div class="alert alert-danger" role="alert">
                    ${error}
                </div>
            </c:if>

            <form action="SubjectUpdateExecute.action" method="post">
                <input type="hidden" name="old_cd" value="${subject.subjectCd}">

                <div class="mb-3">
                    <label class="form-label" for="subject-cd-input">科目コード</label>
                    <input class="form-control" type="text" id="subject-cd-input" name="cd" 
                           value="${not empty inputCd ? inputCd : subject.subjectCd}" 
                           maxlength="3" minlength="3" required />
                </div>
                <div class="mb-3">
                    <label class="form-label" for="subject-name-input">科目名</label>
                    <input class="form-control" type="text" id="subject-name-input" name="name" 
                           value="${subject.subjectName}" placeholder="科目名を入力してください" 
                           maxlength="20" required />
                </div>
                <div class="mt-3">
                    <button class="btn btn-primary" type="submit">変更</button>
                    <a href="SubjectList.action" class="btn btn-secondary">キャンセル</a>
                </div>
            </form>
        </section>
    </c:param>
</c:import>
