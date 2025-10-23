<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${empty topic ? 'Add' : 'Edit'} Topic - Interview Prep Tracker</title>
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
                    <h2 class="card-title">${empty topic ? 'Add' : 'Edit'} Topic</h2>
                    <a href="${pageContext.request.contextPath}/topics" class="btn btn-primary">
                        <i class="fas fa-arrow-left"></i> Back to Topics
                    </a>
                </div>
                
                <form action="${pageContext.request.contextPath}/topics/${empty topic ? 'insert' : 'update'}" 
                      method="post" data-validate>
                    <c:if test="${not empty topic}">
                        <input type="hidden" name="id" value="${topic.id}">
                    </c:if>
                    
                    <div class="form-group">
                        <label for="name">Name *</label>
                        <input type="text" id="name" name="name" class="form-control" 
                               value="${topic.name}" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="description">Description</label>
                        <textarea id="description" name="description" class="form-control" rows="5">${topic.description}</textarea>
                    </div>
                    
                    <div class="form-group">
                        <button type="submit" class="btn btn-primary">
                            <i class="fas fa-save"></i> ${empty topic ? 'Add' : 'Update'} Topic
                        </button>
                        <a href="${pageContext.request.contextPath}/topics" class="btn btn-danger">
                            <i class="fas fa-times"></i> Cancel
                        </a>
                    </div>
                </form>
            </div>
        </div>
    </main>

    <footer style="text-align: center; padding: 20px; color: #7f8c8d; font-size: 14px;">
        <p>&copy; 2023 Interview Prep Tracker. All rights reserved.</p>
    </footer>

    <script src="${pageContext.request.contextPath}/js/app.js"></script>
</body>
</html>