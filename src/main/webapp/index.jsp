<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Dashboard - Interview Prep Tracker</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <header>
        <div class="container">
            <h1><i class="fas fa-code"></i> Interview Prep Tracker</h1>
            <nav>
                <ul>
                    <li><a href="${pageContext.request.contextPath}/index.jsp" class="active">Dashboard</a></li>
                    <li><a href="${pageContext.request.contextPath}/problems">Problems</a></li>
                    <li><a href="${pageContext.request.contextPath}/topics">Topics</a></li>
                    <li><a href="${pageContext.request.contextPath}/submissions">Submissions</a></li>
                    <li><a href="${pageContext.request.contextPath}/logout">Logout</a></li>
                </ul>
            </nav>
            <div style="color: white; margin-left: 20px;">
                Welcome, ${user.fullName}
            </div>
        </div>
    </header>

    <main>
        <div class="container">
            <div class="card">
                <div class="card-header">
                    <h2 class="card-title">Dashboard</h2>
                </div>
                
                <div class="stats-container">
                    <div class="stat-card">
                        <div class="stat-value">${totalProblems}</div>
                        <div class="stat-label">Total Problems</div>
                    </div>
                    <div class="stat-card">
                        <div class="stat-value">${totalTopics}</div>
                        <div class="stat-label">Topics</div>
                    </div>
                    <div class="stat-card">
                        <div class="stat-value">${completedSubmissions}</div>
                        <div class="stat-label">Completed</div>
                    </div>
                    <div class="stat-card">
                        <div class="stat-value">${inProgressSubmissions}</div>
                        <div class="stat-label">In Progress</div>
                    </div>
                </div>
                
                <div class="card">
                    <div class="card-header">
                        <h3 class="card-title">Quick Actions</h3>
                    </div>
                    <div style="display: flex; gap: 10px; flex-wrap: wrap;">
                        <a href="${pageContext.request.contextPath}/problems/new" class="btn btn-primary"><i class="fas fa-plus"></i> Add Problem</a>
                        <a href="${pageContext.request.contextPath}/topics/new" class="btn btn-success"><i class="fas fa-plus"></i> Add Topic</a>
                        <a href="${pageContext.request.contextPath}/submissions/new" class="btn btn-warning"><i class="fas fa-plus"></i> Add Submission</a>
                    </div>
                </div>
            </div>
        </div>
    </main>
</body>
</html>