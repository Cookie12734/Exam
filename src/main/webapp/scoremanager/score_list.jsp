<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">成績管理</c:param>
    <c:param name="scripts"></c:param>
    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">成績管理</h2>
            
            <form action="ScoreListExecute.action" method="get" class="row g-3 my-3 px-4">
                <div class="col-auto">
                    <label for="entYear" class="form-label">入学年度</label>
                    <select name="entYear" id="entYear" class="form-select">
                        <option value="0">--------</option>
                        <c:forEach var="year" items="${entYearSet}">
                            <option value="${year}" <c:if test="${year == param.entYear}">selected</c:if>>${year}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="col-auto">
                    <label for="classNum" class="form-label">クラス</label>
                    <select name="classNum" id="classNum" class="form-select">
                        <option value="0">--------</option>
                        <c:forEach var="num" items="${classNumSet}">
                            <option value="${num}" <c:if test="${num == param.classNum}">selected</c:if>>${num}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="col-auto">
                    <label for="subjectCd" class="form-label">科目</label>
                    <select name="subjectCd" id="subjectCd" class="form-select">
                        <option value="0">--------</option>
                        <c:forEach var="subject" items="${subjectList}">
                            <option value="${subject.subjectCd}" <c:if test="${subject.subjectCd == param.subjectCd}">selected</c:if>>${subject.subjectName}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="col-auto">
                    <label for="num" class="form-label">回数</label>
                    <select name="num" id="num" class="form-select">
                        <option value="0">--------</option>
                        <c:forEach var="i" begin="1" end="2">
                            <option value="${i}" <c:if test="${i == param.num}">selected</c:if>>${i}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="col-auto align-self-end">
                    <button type="submit" class="btn btn-secondary">検索</button>
                </div>
            </form>

            <div class="px-4">
                <%-- エラーメッセージの表示 --%>
                <c:if test="${not empty errors}">
                    <div class="alert alert-danger" role="alert">
                        ${errors}
                    </div>
                </c:if>

                <c:choose>
                    <c:when test="${not empty testList}">
                        <table class="table table-hover table-bordered">
                            <thead>
                                <tr>
                                    <th>学生番号</th>
                                    <th>氏名</th>
                                    <th>点数</th>
                                    <th></th>
                                    <th></th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="test" items="${testList}">
                                    <tr>
                                        <td><c:out value="${test.studentNo}" /></td>
                                        <td><c:out value="${test.studentName}" /></td>
                                        <td><c:out value="${test.point}" /></td>
                                        <td>
                                        	<a href="ScoreUpdate.action?entYear=${param.entYear}&classNum=${param.classNum}&subjectCd=${param.subjectCd}&no=${param.num}">変更</a>
                                    	</td>
                                   		<td>
                                        	<a href="ScoreDelete.action?cd=${test.studentNo}">削除</a>
                                    	</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </c:when>
                    <%-- 検索結果が空、かつエラーメッセージが無い場合のみ表示 --%>
                    <c:when test="${empty testList and not empty param.entYear and empty errors}">
                        <p>学生情報が存在しませんでした</p>
                    </c:when>
                </c:choose>
            </div>
        </section>
    </c:param>
</c:import>
