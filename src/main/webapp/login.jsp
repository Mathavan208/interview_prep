<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login - Interview Prep Tracker</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        body { background-color: #f8f9fa; display: flex; justify-content: center; align-items: center; height: 100vh; }
        .login-container { background-color: white; border-radius: 8px; box-shadow: 0 4px 12px rgba(0,0,0,0.1); padding: 30px; width: 100%; max-width: 400px; }
        .login-header { text-align: center; margin-bottom: 30px; }
        .login-header h1 { color: #2c3e50; font-size: 1.8rem; }
        .alert { padding: 10px; margin-bottom: 20px; border-radius: 4px; color: #721c24; background-color: #f8d7da; }
    </style>
</head>
<body>
    <div class="login-container">
        <div class="login-header">
            <h1><i class="fas fa-code"></i> Interview Prep Tracker</h1>
            <p>Please login to continue</p>
        </div>
        
        <c:if test="${not empty error}">
            <div class="alert">${error}</div>
        </c:if>
        
        <form action="login" method="post">
            <div class="form-group">
                <label for="username">Username</label>
                <input type="text" id="username" name="username" class="form-control" required>
            </div>
            <div class="form-group">
                <label for="password">Password</label>
                <input type="password" id="password" name="password" class="form-control" required>
            </div>
            <button type="submit" class="btn">Login</button>
        </form>
        
        <div class="register-link" style="text-align: center; margin-top: 20px;">
            Don't have an account? <a href="register.jsp">Register here</a>
        </div>
    </div>
</body>
</html>