<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- このページがエラー専用ページであることを宣言しますa --%>
<%@ page isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>エラーが発生しました</title>
</head>
<body>
    <h2>申し訳ございません。システムエラーが発生しました。</h2>
    <p>恐れ入りますが、ログインからやり直してください。</p>
    
    <%-- 開発中のみ、エラーの内容を表示させるとデバッグが楽になります --%>
    <div style="color: red; border: 1px solid red; padding: 10px;">
        <div style="color: red; border: 1px solid red; padding: 10px;">
    		エラーメッセージ: <%= request.getAttribute("javax.servlet.error.exception") %>
		</div>
    </div>

    <a href="index.jsp">ログインページへ戻る</a>
</body>
</html>