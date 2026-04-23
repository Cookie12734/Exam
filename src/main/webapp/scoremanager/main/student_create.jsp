<%-- 学生新規登録JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<c:import url="/common/base.jsp" >
    <c:param name="title">
        学生新規登録 - 得点管理システム
    </c:param>

    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">学生新規登録</h2>
            
            <form action="StudentCreate.action" method="post" class="mx-3">
                <div class="mb-3">
                    <label class="form-label" for="Ent_Year">入学年度</label>
                    <input type="text" class="form-control" id="Ent_Year" name="Ent_Year" required>
                </div>
                <div class="mb-3">
                    <label class="form-label" for="Student_No">学生番号</label>
                    <input type="text" class="form-control" id="Student_No" name="Student_No" required>
                </div>
                <div class="mb-3">
                    <label class="form-label" for="Student_Name">氏名</label>
                    <input type="text" class="form-control" id="Student_Name" name="Student_Name" required>
                </div>
                <div class="mb-3">
                    <label class="form-label" for="Class_Num">クラス</label>
                    <input type="text" class="form-control" id="Class_Num" name="Class_Num" required>
                </div>
                
                <div class="text-end">
                    <button type="submit" class="btn btn-primary">登録</button>
                    <a href="StudentList.action" class="btn btn-secondary">戻る</a>
                </div>
            </form>
        </section>
    </c:param>
</c:import>
