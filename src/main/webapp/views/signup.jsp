<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML>
<html >
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>SignUp | ChatApp</title>

  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x" crossorigin="anonymous">
  <style>
            .login{
                width: 50%;
                padding: 100px;
                box-shadow: 0 0 10px black;
            }

            @media (max-width:1000px){
                .login{
                    width:100%;
                }
            }
        </style>
</head>

<body class="bg-dark">

<% boolean hasError = request.getAttribute("error")==null?false:true; %>

<!-- if login failed -->
<% if(hasError){ %>
<div class="position-absolute alert alert-warning alert-dismissible fade show" id="notification" style="margin-left: 70%;" role="alert">
  <strong>SignUp Failed</strong> Username Already Exist.
  <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
</div>
<% } %>

<div class="container mt-5">
  <form action="/signup" class="m-auto bg-dark login" method="post">
    <h2 class="text-success d-flex justify-content-center ">Signup ChatApp</h2>
    <div class="form-group mb-3">
      <label for="username" class="form-label text-white" >Enter UserName</label>
      <input type="username" id="username" class="form-control" name="username" autofocus required>
    </div>

    <div class="form-group mb-3">
      <label for="password" class="form-label text-white"  >Enter Password</label>
      <input type="password" name="password" minlength="7" oninvalid="this.setCustomValidity('Enter minimum 7 character')" class="form-control" id="password" required>
    </div>

    <div class="form-group mb-3">
      <label for="password" class="form-label text-white"  >Enter re-enter Password</label>
      <input type="password" name="repassword" class="form-control" id="repassword" required>
    </div>
    <input type="submit" class="btn btn-success" id="btn" value="Signup" style="width:12rem; margin-left:10px;" disabled />
    <a type="submit" class="btn btn-outline-info" href="/login" >Login Your Account</a>
  </form>
</div>

</body>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.min.js" integrity="sha384-Atwg2Pkwv9vp0ygtn1JAojH0nYbwNJLPhwyoVbhoPwBhjQPR5VtM2+xf0Uwh9KtT" crossorigin="anonymous"></script>
<script>


$('#repassword').focusout(function(){

      if( $('#repassword').val() === $('#password').val() ){
            $('#btn').prop('disabled',false);
      }})
    </script>


</html>

</body>
</html>