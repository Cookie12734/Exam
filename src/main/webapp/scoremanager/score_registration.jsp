<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">
    <c:param name="title">成績管理 - 得点管理システム</c:param>
    <c:param name="scripts"></c:param>

    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">成績管理</h2>

            <form action="ScoreCreateExecute.action" method="get" class="mb-4 px-4">
                <div class="row g-3 align-items-end">
                    <div class="col-md-2">
                        <label class="form-label">入学年度</label>
                        <select name="ent_year" class="form-select" required>
                            <option value="">選択してください</option>
                            <c:forEach var="year" items="${ent_year_set}">
                                <option value="${year}" <c:if test="${year == ent_year}">selected</c:if>>${year}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="col-md-2">
                        <label class="form-label">クラス</label>
                        <select name="class_num" class="form-select" required>
                            <option value="">選択してください</option>
                            <c:forEach var="num" items="${class_num_set}">
                                <option value="${num}" <c:if test="${num == class_num}">selected</c:if>>${num}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="col-md-3">
                        <label class="form-label">科目</label>
                        <select name="subject_cd" class="form-select" required>
                            <option value="">選択してください</option>
                            <c:forEach var="sub" items="${subjects}">
                                <option value="${sub.subjectCd}" <c:if test="${sub.subjectCd == subject_cd}">selected</c:if>>${sub.subjectName}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <div class="col-md-2">
                        <label class="form-label">回数</label>
                        <select name="num" class="form-select" required>
                            <option value="">選択してください</option>
                            <option value="1" <c:if test="${num == 1}">selected</c:if>>1回目</option>
                            <option value="2" <c:if test="${num == 2}">selected</c:if>>2回目</option>
                        </select>
                    </div>

                    <div class="col-md-2">
                        <button type="submit" class="btn btn-primary w-100">検索</button>
                    </div>
                </div>
            </form>

            <c:if test="${not empty errors}">
                <div class="alert alert-danger mx-4">
                    <c:forEach var="error" items="${errors}">
                        <div>${error}</div>
                    </c:forEach>
                </div>
            </c:if>
        </section>
    </c:param>
</c:import>