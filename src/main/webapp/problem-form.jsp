<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${empty problem ? 'Add' : 'Edit'} Problem - Interview Prep Tracker</title>
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
                    <h2 class="card-title">${empty problem ? 'Add' : 'Edit'} Problem</h2>
                    <a href="${pageContext.request.contextPath}/problems" class="btn btn-primary">
                        <i class="fas fa-arrow-left"></i> Back to Problems
                    </a>
                </div>
                
                <form action="${pageContext.request.contextPath}/problems/${empty problem ? 'insert' : 'update'}" 
                      method="post" data-validate>
                    <c:if test="${not empty problem}">
                        <input type="hidden" name="id" value="${problem.id}">
                    </c:if>
                    
                    <div class="form-group">
                        <label for="title">Title *</label>
                        <input type="text" id="title" name="title" class="form-control" 
                               value="${problem.title}" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="description">Description</label>
                        <textarea id="description" name="description" class="form-control" rows="5">${problem.description}</textarea>
                    </div>
                    
                    <div class="form-group">
                        <label for="difficulty">Difficulty *</label>
                        <select id="difficulty" name="difficulty" class="form-control" required>
                            <option value="">Select Difficulty</option>
                            <option value="Easy" ${problem.difficulty == 'Easy' ? 'selected' : ''}>Easy</option>
                            <option value="Medium" ${problem.difficulty == 'Medium' ? 'selected' : ''}>Medium</option>
                            <option value="Hard" ${problem.difficulty == 'Hard' ? 'selected' : ''}>Hard</option>
                        </select>
                    </div>
                    
                    <div class="form-group">
                        <label for="topicId">Topic</label>
                        <select id="topicId" name="topicId" class="form-control">
                            <option value="">Select Topic</option>
                            <c:forEach var="topic" items="${topics}">
                                <option value="${topic.id}" ${problem.topicId == topic.id ? 'selected' : ''}>${topic.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    
                    <div class="form-group">
                        <label for="link">Link</label>
                        <input type="url" id="link" name="link" class="form-control" 
                               value="${problem.link}" placeholder="https://example.com/problem">
                    </div>
                    
                    <div class="form-group">
                        <button type="submit" class="btn btn-primary">
                            <i class="fas fa-save"></i> ${empty problem ? 'Add' : 'Update'} Problem
                        </button>
                        <a href="${pageContext.request.contextPath}/problems" class="btn btn-danger">
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