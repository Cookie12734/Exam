<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>

<c:import url="/common/base.jsp">
    <c:param name="title">登録完了 - 得点管理システム</c:param>
    <c:param name="scripts"></c:param>

    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">成績登録</h2>
            
            <div class="container mt-5">
                <div class="card shadow-sm p-5 text-center">
                    <div class="mb-4">
                        <i class="bi bi-check-circle-fill text-success" style="font-size: 3rem;"></i>
                    </div>
                    <h3 class="mb-4">登録が完了しました</h3>
                    
                    <div class="d-grid gap-2 d-md-block">
                        <a href="ScoreCreate.action" class="btn btn-primary px-5">続けて登録する</a>
                        <a href="menu.jsp" class="btn btn-outline-secondary px-5">メニューに戻る</a>
                    </div>
                </div>
            </div>
        </section>
    </c:param>
</c:import>