<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<c:import url="/common/base.jsp">
    <c:param name="title">成績管理</c:param>
    <c:param name="scripts"></c:param>
    <c:param name="content">
        <section class="me-4">
            <h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2 px-4">成績管理</h2>
            
            <%-- 科目情報での検索フォーム --%>
            <form action="ScoreListExecute.action" method="get" class="row g-3 my-3 px-4 align-items-end">
                <div class="col-auto pb-1">
                    <span class="fw-bold me-2">科目情報</span>
                </div>
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

                <div class="col-auto">
                    <button type="submit" class="btn btn-secondary">検索</button>
                    <input type="hidden" name="f" value="sj"> <%-- 検索種別判別用 --%>
                </div>
            </form>

            <hr class="mx-4">

            <%-- 学生情報での検索フォーム --%>
            <form action="ScoreListExecute.action" method="get" class="row g-3 my-3 px-4 align-items-end">
                <div class="col-auto pb-1">
                    <span class="fw-bold me-2">学生情報</span>
                </div>
                <div class="col-auto">
                    <label for="studentNo" class="form-label">学生番号</label>
                    <input type="text" name="studentNo" id="studentNo" class="form-control" placeholder="学生番号を入力してください" value="${param.studentNo}">
                </div>
                <div class="col-auto">
                    <button type="submit" class="btn btn-secondary">検索</button>
                    <input type="hidden" name="f" value="st"> <%-- 検索種別判別用 --%>
                </div>
            </form>

            <hr class="mx-4">

            <div class="px-4">
                <%-- エラーメッセージの表示 --%>
                <c:if test="${not empty errors}">
                    <div class="alert alert-danger" role="alert">
                        ${errors}
                    </div>
                </c:if>
                <c:if test="${not empty errorMsg}">
                    <div class="alert alert-danger" role="alert">
                        ${errorMsg}
                    </div>
                </c:if>

                <c:choose>
                    <c:when test="${not empty testList}">
                        <%-- 学生情報検索で結果が1件以上ある場合のみ氏名を表示 --%>
                        <c:if test="${param.f == 'st' && not empty testList}">
                            <div class="mb-2 fw-bold">
                                氏名：${testList[0].studentName} (${testList[0].studentNo})
                            </div>
                        </c:if>

                        <%-- 更新フォーム（学生・科目検索どちらもフォームを使用） --%>
                        <form action="ScoreUpdateExecute.action" method="post">
                        
                        <%-- 検索種別を更新アクションにも引き継ぐ --%>
                        <input type="hidden" name="f" value="${param.f}">

                        <c:if test="${param.f == 'st'}">
                            <input type="hidden" name="studentNo" value="${testList[0].studentNo}">
                        </c:if>
                        <c:if test="${param.f == 'sj'}">
                            <%-- 科目検索時は、検索条件の科目をそのまま送信できるようにする --%>
                            <input type="hidden" name="subjectCd" value="${param.subjectCd}">
                            <input type="hidden" name="no" value="${param.num}">
                        </c:if>

                        <table class="table table-hover table-bordered">
                            <thead>
                                <tr>
                                    <c:choose>
                                        <%-- 学生情報検索の場合 --%>
                                        <c:when test="${param.f == 'st'}">
                                            <th>科目名</th>
                                            <th>科目コード</th>
                                            <th>回数</th>
                                            <th>点数</th>
                                        </c:when>
                                        <%-- 科目情報検索の場合 --%>
                                        <c:otherwise>
                                            <th>学生番号</th>
                                            <th>氏名</th>
                                            <th>点数</th>
                                            <th></th>
                                        </c:otherwise>
                                    </c:choose>
                                    <th></th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:choose>
                                    <%-- 学生情報検索の場合 --%>
                                    <c:when test="${param.f == 'st'}">
                                        <c:forEach var="subject" items="${subjectList}">
                                            <c:forEach var="i" begin="1" end="2">
                                                <c:set var="currentPoint" value="" />
                                                <c:forEach var="test" items="${testList}">
                                                    <c:if test="${test.subjectCd == subject.subjectCd && test.no == i}">
                                                        <c:set var="currentPoint" value="${test.point}" />
                                                    </c:if>
                                                </c:forEach>

                                                <tr>
                                                    <td><c:out value="${subject.subjectName}" /></td>
                                                    <td><c:out value="${subject.subjectCd}" /></td>
                                                    <td><c:out value="${i}" /></td>
                                                    <td>
                                                        <input type="hidden" name="subjectCd" value="${subject.subjectCd}">
                                                        <input type="hidden" name="no" value="${i}">
                                                        <input type="number" name="point" value="${currentPoint}" class="form-control" style="width: 80px;" min="0" max="100">
                                                    </td>
                                                    <td></td>
                                                </tr>
                                            </c:forEach>
                                        </c:forEach>
                                    </c:when>
                                    
                                    <%-- 科目情報検索の場合 --%>
                                    <c:otherwise>
                                        <c:forEach var="test" items="${testList}">
                                            <tr>
                                                <td>
                                                    <c:out value="${test.studentNo}" />
                                                    <input type="hidden" name="studentNo" value="${test.studentNo}">
                                                </td>
                                                <td><c:out value="${test.studentName}" /></td>
                                                <td>
                                                    <input type="number" name="point" value="${test.point}" class="form-control" style="width: 80px;" min="0" max="100">
                                                </td>
                                                <td></td>
                                                <td></td>
                                            </tr>
                                        </c:forEach>
                                    </c:otherwise>
                                </c:choose>
                            </tbody>
                        </table>

                        <div class="text-end mt-3">
                            <button type="submit" class="btn btn-primary">更新</button>
                        </div>
                        </form>
                    </c:when>
                    
                    <%-- 検索結果が空、かつ検索実行が行われた場合に表示 --%>
                    <c:when test="${empty testList and not empty param.f and empty errors}">
                        <p>対象の成績情報が存在しませんでした</p>
                    </c:when>
                </c:choose>
            </div>
        </section>
    </c:param>
</c:import>
