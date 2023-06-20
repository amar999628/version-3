<%@page import="java.sql.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>Productivity Monitor</title>
    <style>
        body {
            background-color: #f2f2f2;
            font-family: Arial, sans-serif;
        }

        h1 {
            color: #333;
            text-align: center;
            margin-top: 50px;
        }

        table {
            width: 80%;
            margin: 30px auto;
            border-collapse: collapse;
        }

        th, td {
            padding: 10px;
            text-align: center;
            border: 1px solid #ccc;
        }

        th {
            background-color: #f9f9f9;
            color: #333;
        }

        .clickable-row {
            cursor: pointer;
            background-color: #fff;
        }

        .clickable-row:hover {
            background-color: #f5f5f5;
        }

        .buttons-container {
            text-align: center;
            margin-top: 20px;
        }

        .button {
            background-color: #333;
            color: #fff;
            border: none;
            padding: 10px 20px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-size: 16px;
            margin: 10px;
            cursor: pointer;
            border-radius: 5px;
        }
    </style>
</head>
<body>

    <table>
        <tr>
            <th>proj_id</th>
            <th>proj_Name</th>
            <th>proj_desc</th>
            <th>proj_stdate</th>
            <th>proj_manager</th>
            <th>proj_status</th>
        </tr>
        <% 
            Connection conn = null;
            Statement stmt = null;
            ResultSet rs = null;
            
            try {
                // Establish database connection
                Class.forName("org.postgresql.Driver");
                String url = "jdbc:postgresql://192.168.110.48:5432/plf_training";
                String username = "plf_training_admin";
                String password = "pff123";
                conn = DriverManager.getConnection(url, username, password);
                
                // Execute SQL query
                String sql = "SELECT * FROM ProductivityMonitor";
                stmt = conn.createStatement();
                rs = stmt.executeQuery(sql);
                
                // Retrieve and display data
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String date = rs.getString("Date");
                    String employeeName = rs.getString("EmployeeName");
                    String task = rs.getString("Task");
                    double timeSpent = rs.getDouble("TimeSpent");
                    double efficiencyRating = rs.getDouble("EfficiencyRating");
        %>
        
        <tr class="clickable-row" onclick="location.href='details.jsp?id=<%= task %>'">
            <td><%= id %></td>
            <td><%= employeeName %></td>
            <td><%= task %></td>
            <td><%= timeSpent %></td>
            <td><%= efficiencyRating %></td>
            <td>active</td>
        </tr>
        
        <% 
                }
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            } finally {
                // Close database connections and resources
                try {
                    if (rs != null) rs.close();
                    if (stmt != null) stmt.close();
                    if (conn != null) conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        %>
    </table>
    
    <div class="buttons-container">
        <button class="button" onclick="location.href='module'">Modules</button>
        <button class="button" onclick="location.href='resources1.jsp'">Resources</button>
        <button class="button" onclick="location.href='sprint'">Sprints</button>
    </div>
</body>
</html>
