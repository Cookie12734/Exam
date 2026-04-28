<%-- 学生変更JSPa --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/common/base.jsp">
    <c:param name="title">得点管理システム</c:param>
    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">学生情報変更</h2>
            
            <%-- 送信先を ExecuteAction（保存処理）に変更 --%>
            <form action="StudentUpdateExecute.action" method="post">
                <div class="mx-3 py-2">
                    <%-- 入学年度（初期値を反映） --%>
                    <div class="mb-3">
                        <label class="form-label" for="student-ent-year-select">入学年度</label>
                        <select class="form-select" id="student-ent-year-select" name="ent_year">
                            <c:forEach var="year" items="${ent_year_set}">
                                <option value="${year}" <c:if test="${year == student.entYear}">selected</c:if>>${year}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <%-- 学生番号（変更不可にする） --%>
                    <div class="mb-3">
                        <label class="form-label" for="student-no-input">学生番号</label>
                        <input class="form-control" type="text" id="student-no-input" name="no" 
                               value="${student.studentNo}" readonly required />
                        <div class="form-text text-muted">学生番号は変更できません。</div>
                    </div>

                    <%-- 氏名（初期値を反映） --%>
                    <div class="mb-3">
                        <label class="form-label" for="student-name-input">氏名</label>
                        <input class="form-control" type="text" id="student-name-input" name="name" 
                               value="${student.studentName}" placeholder="氏名を入力してください" required />
                    </div>

                    <%-- クラス（初期値を反映） --%>
                    <div class="mb-3">
                        <label class="form-label" for="student-class-num-select">クラス</label>
                        <select class="form-select" id="student-class-num-select" name="class_num">
                            <c:forEach var="num" items="${class_num_set}">
                                <option value="${num}" <c:if test="${num == student.classNum}">selected</c:if>>${num}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <%-- 在学フラグ（チェック状態を反映） --%>
                    <div class="mb-3 form-check">
                        <input class="form-check-input" type="checkbox" id="student-is-attend-check" name="is_attend" value="t"
                               <c:if test="${student.isAttend()}">checked</c:if> />
                        <label class="form-check-label" for="student-is-attend-check">在学中</label>
                    </div>

                    <div class="mt-3">
                        <button class="btn btn-primary" type="submit">変更を保存する</button>
                        <a href="StudentList.action" class="btn btn-outline-secondary ms-2">キャンセル</a>
                    </div>
                </div>
            </form>
        </section>
    </c:param>
</c:import>