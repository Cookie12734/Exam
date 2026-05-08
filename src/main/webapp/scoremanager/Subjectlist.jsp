<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">科目一覧</c:param>
    <c:param name="scripts"></c:param>
    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">科目一覧</h2>
				<div class="my-2 text-end px-4">
					<a href="SubjectCreate.action"　class="fs-3">新規登録</a>
				</div>            
            <table class="table table-hover table-bordered">
                <thead>
                    <tr>
                        <th>科目コード</th>
                        <th>科目名</th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${not empty subjectList}">
                            <c:forEach var="subject" items="${subjectList}">
                                <tr>
                                    <td><c:out value="${subject.subjectCd}" /></td>
                                    <td><c:out value="${subject.subjectName}" /></td>
                                </tr>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <tr>
                                <td colspan="2">科目が登録されていません。</td>
                            </tr>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </section>
    </c:param>
</c:import>
