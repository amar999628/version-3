<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Project List</title>
 
</head>
<body>
    <h1>Project List</h1>
    <form id="filterForm">
    <label for="projectId">Project:</label>
    <select name="projectId" id="projectId">
        <option value="">All</option>
        <!-- Populate project options -->
        <c:forEach items="${projectDto}" var="project">
            <option value="${project.projectId}">${project.projectName}</option>
        </c:forEach>
    </select>
<label for="status">Status:</label>
<select name="status" id="status">
    <option value="">All</option>
    <option value="O" <c:if test="${status == 'O'}">selected</c:if>>Completed</option>
    <option value="I" <c:if test="${status == 'I'}">selected</c:if>>Ongoing</option>
</select>


    <button type="submit">Apply Filters</button>
</form>
    
    <script>
    $(document).ready(function() {
        // Handle filter form submission using AJAX
        $('#filterForm').submit(function(event) {
            event.preventDefault(); // Prevent form submission
            var filterData = $(this).serialize(); // Serialize form data

            // Send AJAX request to the server
            $.ajax({
                url: 'projectfilter',
                type: 'POST',
                data: filterData,
                dataType: 'json', // Expect JSON response
                success: function(data) {
                    // Replace the table body with the updated data
                    var tbody = $('#taskTable tbody');
                    tbody.empty(); // Clear existing rows

                    // Iterate over the filtered tasks and add them to the table
                    for (var i = 0; i < data.length; i++) {
                        var task = data[i];
                        var row = '<tr>' +
                        <th>Project ID</th>
                        <th>Project Name</th>
                        <th>Project Description</th>
                        <th>Project Start Date</th>
                        <th>Project Manager</th>
                        <th>Project Status</th>
                        <th>Project Last Updated Date</th>
                        tbody.append(row);
                    }
                },
                error: function() {
                    alert('An error occurred while retrieving filtered tasks.');
                }
            });
        });
    });
</script>
    

    <table>
        <tr>
            <th>Project ID</th>
            <th>Project Name</th>
            <th>Project Description</th>
            <th>Project Start Date</th>
            <th>Project Manager</th>
            <th>Project Status</th>
            <th>Project Last Updated Date</th>
        </tr>
        <c:forEach items="${projectDto}" var="project">
            <tr>
                <td>${project.projectId}</td>
                <td>${project.projectName}</td>
                <td>${project.projectDescription}</td>
                <td>${project.projectStartDate}</td>
                <td>${project.projectManagerName}</td>
                <td>${project.projectStatus}</td>
                <td>${project.projectLastUpdatedDate}</td>
            </tr>
        </c:forEach>
    </table>

    <button class="btn btn-primary btn-add-project" type="button">Add Project</button>

    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"></script>
</body>
</html>
