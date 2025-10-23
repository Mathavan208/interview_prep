<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Submissions - Interview Prep Tracker</title>
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
                    <h2 class="card-title">Submissions</h2>
                    <a href="${pageContext.request.contextPath}/submissions/new" class="btn btn-primary">
                        <i class="fas fa-plus"></i> Add Submission
                    </a>
                </div>
                
                <div class="filters">
                    <form id="filter-form" method="get">
                        <div class="filter-group">
                            <label for="status">Status</label>
                            <select id="status" name="status" class="form-control">
                                <option value="">All Statuses</option>
                                <option value="Not Started" ${selectedStatus == 'Not Started' ? 'selected' : ''}>Not Started</option>
                                <option value="In Progress" ${selectedStatus == 'In Progress' ? 'selected' : ''}>In Progress</option>
                                <option value="Completed" ${selectedStatus == 'Completed' ? 'selected' : ''}>Completed</option>
                                <option value="Reviewed" ${selectedStatus == 'Reviewed' ? 'selected' : ''}>Reviewed</option>
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
                                <th>Problem</th>
                                <th>Status</th>
                                <th>Date</th>
                                <th>Time Spent</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="submission" items="${submissions}">
                                <tr>
                                    <td>${submission.problemTitle}</td>
                                    <td>
                                        <span class="badge badge-${submission.status.toLowerCase().replace(' ', '-')}">${submission.status}</span>
                                    </td>
                                    <td><fmt:formatDate value="${submission.submissionDate}" pattern="yyyy-MM-dd HH:mm" /></td>
                                    <td>${submission.timeSpent} min</td>
                                    <td>
                                        <div class="action-buttons">
                                            <a href="${pageContext.request.contextPath}/submissions/edit?id=${submission.id}" 
                                               class="btn btn-sm btn-primary" title="Edit">
                                                <i class="fas fa-edit"></i>
                                            </a>
                                            <a href="${pageContext.request.contextPath}/submissions/delete?id=${submission.id}" 
                                               class="btn btn-sm btn-danger btn-delete" 
                                               data-confirm="Are you sure you want to delete this submission?" title="Delete">
                                                <i class="fas fa-trash"></i>
                                            </a>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                    
                    <c:if test="${empty submissions}">
                        <div style="text-align: center; padding: 20px; color: #7f8c8d;">
                            No submissions found. <a href="${pageContext.request.contextPath}/submissions/new">Add a new submission</a>.
                        </div>
                    </c:if>
                </div>
                
                <c:if test="${totalPages > 1}">
                    <div class="pagination">
                        <c:if test="${currentPage > 1}">
                            <a href="?page=${currentPage - 1}&status=${selectedStatus}&pageSize=${pageSize}">
                                <i class="fas fa-chevron-left"></i> Previous
                            </a>
                        </c:if>
                        
                        <c:forEach begin="1" end="${totalPages}" var="i">
                            <c:choose>
                                <c:when test="${currentPage eq i}">
                                    <span class="active">${i}</span>
                                </c:when>
                                <c:otherwise>
                                    <a href="?page=${i}&status=${selectedStatus}&pageSize=${pageSize}">${i}</a>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                        
                        <c:if test="${currentPage < totalPages}">
                            <a href="?page=${currentPage + 1}&status=${selectedStatus}&pageSize=${pageSize}">
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