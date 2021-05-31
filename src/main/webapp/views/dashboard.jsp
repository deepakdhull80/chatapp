<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.mywork.chatapp.Model.*" %>
<%@ page import="java.util.*" %>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>SignUp |ChatApp</title>
</head>
<body>
<!DOCTYPE HTML>
<html xmlns:th="http://www.thymeleaf.org">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Dashboard | ChatApp</title>

  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x" crossorigin="anonymous">
  <style>
      .card-body:hover{
        box-shadow: 0 0 1px black;
      }
  </style>
</head>

<body >


    <% User user =(User) request.getAttribute("user"); %>



<!-- navbar -->

  <nav class="navbar navbar-expand-lg navbar-light bg-dark navbar-dark position-sticky">
    <div class="container-fluid">
      <a class="navbar-brand" href="/dashboard">${user.userName}</a>
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav me-auto mb-2 mb-lg-0">

          <li class="nav-item">
            <a class="nav-link active" aria-current="page" href="#">Groups</a>
          </li>

        </ul>

        <div class="d-flex mt-4 mt-lg-0" style="margin-left:4px;">
          <a class="btn btn-outline-danger" href="/logout" >Logout</a>
        </div>
      </div>
    </div>
  </nav>
<!--  ------------------ -->

  <!-- create and join section -->

  <div class="container">
    <div class="p-3">
        <input class="form-control w-25 d-inline-block" type="text" name="create" id="valueField" placeholder="Group name for create | group id for join">
        <button class="btn btn-success d-inline-block" id="create" style="margin-left: 10px;width:100px;">Create</button>
        <button class="btn btn-success d-inline-block" id="join" style="width:100px;">Join</button>
    </div>
  </div>



   <% if(user.getGroups().size()>0){ %>
  <!-- having group list -->
      <div class="container" id="grouplist">
        <h2 class="display-3 mb-4">Group List</h2>
        <% for(Group group : user.getGroups()){ %>
            <div class="card w-50">
                <a class="card-body d-block text-decoration-none" href="/chat-group/<%=group.getId() %>">
                    <h5><%=group.getGroupName()%> </h5>
                </a>
            </div>
        <% } %>

      </div>

  <% } %>

  <!-- empty group list -->
    <% if(user.getGroups().size()<=0){ %>
    <div class="container alert alert-success" id="notgrouplist" role="alert">
        <h4 class="alert-heading">Currently you are not in any group</h4>
        <p>To add yourself in any group use navbar</p>
        <hr>
    </div>
    <%}%>
</body>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.min.js" integrity="sha384-Atwg2Pkwv9vp0ygtn1JAojH0nYbwNJLPhwyoVbhoPwBhjQPR5VtM2+xf0Uwh9KtT" crossorigin="anonymous"></script>
<script>

    $('#create').click(function() {
        var data =  $('#valueField').val();
        data=data.trim();
        if(data.length <=0){

        }else{
            $.ajax("/create-group",{
                type:"POST",
                data:{groupName:data},
                success:function(data){

                    location.reload();

                },
                error:function(msg){
                    console.log(msg);
                }

            })
        }
    })


    $('#join').click(function() {
        var data =  $('#valueField').val();
        data=data.trim();
        if(data.length <=0){

        }else{
            $.ajax("/add-group",{
                type:"POST",
                data:{groupid:data},
                success:function(data){

                    location.reload();

                },
                error:function(msg){
                    console.log(msg);
                }

            })
        }
    })


</script>


</html>

</body>
</html>