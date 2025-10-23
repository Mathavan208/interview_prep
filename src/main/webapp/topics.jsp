<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Topics - Interview Prep Tracker</title>
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
                    <h2 class="card-title">Topics</h2>
                    <a href="${pageContext.request.contextPath}/topics/new" class="btn btn-primary">
                        <i class="fas fa-plus"></i> Add Topic
                    </a>
                </div>
                
                <div class="table-container">
                    <table class="table">
                        <thead>
                            <tr>
                                <th>Name</th>
                                <th>Description</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="topic" items="${topics}">
                                <tr>
                                    <td>${topic.name}</td>
                                    <td>${topic.description}</td>
                                    <td>
                                        <div class="action-buttons">
                                            <a href="${pageContext.request.contextPath}/topics/edit?id=${topic.id}" 
                                               class="btn btn-sm btn-primary" title="Edit">
                                                <i class="fas fa-edit"></i>
                                            </a>
                                            <a href="${pageContext.request.contextPath}/topics/delete?id=${topic.id}" 
                                               class="btn btn-sm btn-danger btn-delete" 
                                               data-confirm="Are you sure you want to delete this topic? All associated problems will have their topic set to null." 
                                               title="Delete">
                                                <i class="fas fa-trash"></i>
                                            </a>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                    
                    <c:if test="${empty topics}">
                        <div style="text-align: center; padding: 20px; color: #7f8c8d;">
                            No topics found. <a href="${pageContext.request.contextPath}/topics/new">Add a new topic</a>.
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
    </main>

    <footer style="text-align: center; padding: 20px; color: #7f8c8d; font-size: 14px;">
        <p>&copy; 2023 Interview Prep Tracker. All rights reserved.</p>
    </footer>

    <script src="${pageContext.request.contextPath}/js/app.js"></script>
</body>
</html>