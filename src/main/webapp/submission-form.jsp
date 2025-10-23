<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${empty submission ? 'Add' : 'Edit'} Submission - Interview Prep Tracker</title>
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
                    <h2 class="card-title">${empty submission ? 'Add' : 'Edit'} Submission</h2>
                    <a href="${pageContext.request.contextPath}/submissions" class="btn btn-primary">
                        <i class="fas fa-arrow-left"></i> Back to Submissions
                    </a>
                </div>
                
                <form action="${pageContext.request.contextPath}/submissions/${empty submission ? 'insert' : 'update'}" 
                      method="post" data-validate>
                    <c:if test="${not empty submission}">
                        <input type="hidden" name="id" value="${submission.id}">
                    </c:if>
                    
                    <div class="form-group">
                        <label for="problemId">Problem *</label>
                        <select id="problemId" name="problemId" class="form-control" required>
                            <option value="">Select Problem</option>
                            <c:forEach var="problem" items="${problems}">
                                <option value="${problem.id}" ${submission.problemId == problem.id ? 'selected' : ''}>${problem.title}</option>
                            </c:forEach>
                        </select>
                    </div>
                    
                    <div class="form-group">
                        <label for="status">Status *</label>
                        <select id="status" name="status" class="form-control" required>
                            <option value="">Select Status</option>
                            <option value="Not Started" ${submission.status == 'Not Started' ? 'selected' : ''}>Not Started</option>
                            <option value="In Progress" ${submission.status == 'In Progress' ? 'selected' : ''}>In Progress</option>
                            <option value="Completed" ${submission.status == 'Completed' ? 'selected' : ''}>Completed</option>
                            <option value="Reviewed" ${submission.status == 'Reviewed' ? 'selected' : ''}>Reviewed</option>
                        </select>
                    </div>
                    
                    <div class="form-group">
                        <label for="timeSpent">Time Spent (minutes)</label>
                        <input type="number" id="timeSpent" name="timeSpent" class="form-control" 
                               value="${submission.timeSpent}" min="0">
                    </div>
                    
                    <div class="form-group">
                        <label for="notes">Notes</label>
                        <textarea id="notes" name="notes" class="form-control" rows="5">${submission.notes}</textarea>
                    </div>
                    
                    <div class="form-group">
                        <button type="submit" class="btn btn-primary">
                            <i class="fas fa-save"></i> ${empty submission ? 'Add' : 'Update'} Submission
                        </button>
                        <a href="${pageContext.request.contextPath}/submissions" class="btn btn-danger">
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