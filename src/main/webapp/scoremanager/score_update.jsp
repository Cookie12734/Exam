<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">成績変更</c:param>
    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">成績変更</h2>

            <c:if test="${not empty errorMsg}">
                <p class="text-danger">${errorMsg}</p>
            </c:if>

            <form action="ScoreUpdateExecute.action" method="post">
                <input type="hidden" name="entYear"   value="${entYear}">
                <input type="hidden" name="classNum"  value="${classNum}">
                <input type="hidden" name="subjectCd" value="${subjectCd}">
                <input type="hidden" name="no"        value="${no}">

                <table class="table table-bordered">
                    <thead>
                        <tr>
                            <th>学生番号</th>
                            <th>学生名</th>
                            <th>点数</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="score" items="${scoreList}">
                            <tr>
                                <td>
                                    ${score.studentNo}
                                    <input type="hidden" name="studentNo" value="${score.studentNo}">
                                </td>
                                <td>${score.studentName}</td>
                                <td>
                                    <input type="text" name="point" value="${score.point}" size="5" maxlength="3">
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

                <div class="mt-3">
                    <button class="btn btn-primary" type="submit">登録して終了</button>
                    <a href="ScoreListAction" class="btn btn-secondary">キャンセル</a>
                </div>
            </form>
        </section>
    </c:param>
</c:import>