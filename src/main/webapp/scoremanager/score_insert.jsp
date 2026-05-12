<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">
    <c:param name="title">成績登録 - 得点管理システム</c:param>
    <c:param name="scripts"></c:param>

    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">成績登録</h2>
            
            <div class="mb-3 px-4">
                <c:choose>
                    <c:when test="${not empty score_list}">
                        <p class="fw-bold">科目：${score_list[0].subjectName}（${num}回目）</p>
                    </c:when>
                    <c:otherwise>
                        <p class="alert alert-warning">対象の学生が見つかりませんでした。</p>
                    </c:otherwise>
                </c:choose>
            </div>

            <form action="ScoreSave.action" method="post" class="px-4">
                <input type="hidden" name="subject_cd" value="${subject_cd}">
                <input type="hidden" name="num" value="${num}">

                <table class="table table-hover mt-3">
                    <thead class="table-light">
                        <tr>
                            <th>入学年度</th>
                            <th>クラス</th>
                            <th>学生番号</th>
                            <th>氏名</th>
                            <th style="width: 150px;">点数</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="score" items="${score_list}">
                            <tr>
                                <td>${score.entYear}</td>
                                <td>${score.classNum}</td>
                                <td>${score.studentNo}</td>
                                <td>${score.studentName}</td>
                                <td>
                                    <input type="number" 
                                           name="point_${score.studentNo}" 
                                           value="${score.point == -1 ? '' : score.point}" 
                                           min="0" max="100" 
                                           class="form-control"
                                           required>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                
                <div class="mt-4">
                    <button type="submit" class="btn btn-primary">登録して終了</button>
                    <a href="ScoreCreate.action" class="btn btn-secondary">戻る</a>
                </div>
            </form>
        </section>
    </c:param>
</c:import>