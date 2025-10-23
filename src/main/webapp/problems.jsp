<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Problems - Interview Prep Tracker</title>
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
                    <h2 class="card-title">Problems</h2>
                    <a href="${pageContext.request.contextPath}/problems/new" class="btn btn-primary">
                        <i class="fas fa-plus"></i> Add Problem
                    </a>
                </div>
                
                <div class="filters">
                    <form id="filter-form" method="get">
                        <div class="filter-group">
                            <label for="difficulty">Difficulty</label>
                            <select id="difficulty" name="difficulty" class="form-control">
                                <option value="">All Difficulties</option>
                                <option value="Easy" ${selectedDifficulty == 'Easy' ? 'selected' : ''}>Easy</option>
                                <option value="Medium" ${selectedDifficulty == 'Medium' ? 'selected' : ''}>Medium</option>
                                <option value="Hard" ${selectedDifficulty == 'Hard' ? 'selected' : ''}>Hard</option>
                            </select>
                        </div>
                        
                        <div class="filter-group">
                            <label for="topicId">Topic</label>
                            <select id="topicId" name="topicId" class="form-control">
                                <option value="0">All Topics</option>
                                <c:forEach var="topic" items="${topics}">
                                    <option value="${topic.id}" ${selectedTopicId == topic.id ? 'selected' : ''}>${topic.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        
                        <div class="filter-group">
                            <label for="pageSize">Items per page</label>
                            <select id="pageSize" name="pageSize" class="form-control">
                                <option value="5" ${pageSize == 5 ? 'selected' : ''}>5</option>
                                <option value="10" ${pageSize == 10 ? 'selected' : ''}>10</option>
                                <option value="20" ${pageSize == 20 ? 'selected' : ''}>20</option>
                                <option value="50" ${pageSize == 50 ? 'selected' : ''}>50</option>
                            </select>
                        </div>
                    </form>
                </div>
                
                <div class="table-container">
                    <table class="table">
                        <thead>
                            <tr>
                                <th>Title</th>
                                <th>Difficulty</th>
                                <th>Topic</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="problem" items="${problems}">
                                <tr>
                                    <td>
                                        <c:if test="${not empty problem.link}">
                                            <a href="${problem.link}" target="_blank">${problem.title}</a>
                                        </c:if>
                                        <c:if test="${empty problem.link}">
                                            ${problem.title}
                                        </c:if>
                                    </td>
                                    <td>
                                        <span class="badge badge-${problem.difficulty.toLowerCase()}">${problem.difficulty}</span>
                                    </td>
                                    <td>${problem.topicName}</td>
                                    <td>
                                        <div class="action-buttons">
                                            <a href="${pageContext.request.contextPath}/submissions/new?problemId=${problem.id}" 
                                               class="btn btn-sm btn-success" title="Add Submission">
                                                <i class="fas fa-plus"></i>
                                            </a>
                                            <a href="${pageContext.request.contextPath}/problems/edit?id=${problem.id}" 
                                               class="btn btn-sm btn-primary" title="Edit">
                                                <i class="fas fa-edit"></i>
                                            </a>
                                            <a href="${pageContext.request.contextPath}/problems/delete?id=${problem.id}" 
                                               class="btn btn-sm btn-danger btn-delete" 
                                               data-confirm="Are you sure you want to delete this problem?" title="Delete">
                                                <i class="fas fa-trash"></i>
                                            </a>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                    
                    <c:if test="${empty problems}">
                        <div style="text-align: center; padding: 20px; color: #7f8c8d;">
                            No problems found. <a href="${pageContext.request.contextPath}/problems/new">Add a new problem</a>.
                        </div>
                    </c:if>
                </div>
                
                <c:if test="${totalPages > 1}">
                    <div class="pagination">
                        <c:if test="${currentPage > 1}">
                            <a href="?page=${currentPage - 1}&difficulty=${selectedDifficulty}&topicId=${selectedTopicId}&pageSize=${pageSize}">
                                <i class="fas fa-chevron-left"></i> Previous
                            </a>
                        </c:if>
                        
                        <c:forEach begin="1" end="${totalPages}" var="i">
                            <c:choose>
                                <c:when test="${currentPage eq i}">
                                    <span class="active">${i}</span>
                                </c:when>
                                <c:otherwise>
                                    <a href="?page=${i}&difficulty=${selectedDifficulty}&topicId=${selectedTopicId}&pageSize=${pageSize}">${i}</a>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                        
                        <c:if test="${currentPage < totalPages}">
                            <a href="?page=${currentPage + 1}&difficulty=${selectedDifficulty}&topicId=${selectedTopicId}&pageSize=${pageSize}">
                                Next <i class="fas fa-chevron-right"></i>
                            </a>
                        </c:if>
                    </div>
                </c:if>
            </div>
        </div>
    </main>

    <footer style="text-align: center; padding: 20px; color: #7f8c8d; font-size: 14px;">
        <p>&copy; 2023 Interview Prep Tracker. All rights reserved.</p>
    </footer>

    <script src="${pageContext.request.contextPath}/js/app.js"></script>
</body>
</html>