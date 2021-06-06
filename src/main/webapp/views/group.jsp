<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.mywork.chatapp.Model.*" %>
<%@ page import="java.util.*" %>

<% User user =(User) request.getAttribute("user"); %>
<% Group group =(Group) request.getAttribute("group"); %>

<!DOCTYPE HTML>
<html>
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title><%=group.getGroupName()%> | ChatApp</title>

  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x" crossorigin="anonymous">
  <style>
    ::-webkit-scrollbar {
      width: 10px;
    }

    /* Track */
    ::-webkit-scrollbar-track {
      background: #f1f1f1;
    }

    /* Handle */
    ::-webkit-scrollbar-thumb {
      background: #888;
    }

    /* Handle on hover */
    ::-webkit-scrollbar-thumb:hover {
      background: #555;
    }

      .card-body:hover{
        box-shadow: 0 0 1px black;
      }
  </style>
</head>

<body >





<!-- navbar -->

  <nav class="navbar navbar-expand-lg navbar-light bg-dark navbar-dark">
    <div class="container-fluid">
      <a class="navbar-brand" href="/dashboard"><%=user.getUserName()%></a>
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav me-auto mb-2 mb-lg-0">

          <li class="nav-item">
            <a class="nav-link active" aria-current="page" href="/group/<%=group.getId()%>/<%=user.getId()%>/remove">Left Group</a>
          </li>

        </ul>

        <div class="d-flex mt-4 mt-lg-0" style="margin-left:4px;">
          <a class="btn btn-outline-danger" href="/logout" >Logout</a>
        </div>
      </div>
    </div>
  </nav>
<!--  ------------------ -->

<div class="container-md row m-auto bg-dark">

  <!-- user window left side -->
  <div class="col-md-3 d-none d-md-block">
    <h2 class="text-center mt-2 text-white "><%=group.getGroupName()%></h2>
    <hr class="bg-white">

    <div class="overflow-auto p-1 mb-3 mb-md-0 mr-md-3" style="height: 30rem;">

      <% for(User u : group.getUsers()){ %>
          <div class="card mb-2">
            <div class="card-body d-flex justify-content-center align-content-center">
              <!-- <img src="" alt="" class="w-25"> -->
              <span class="bg-info rounded-circle d-inline-block" style="width: 2.5rem; height: 2.5rem; margin-right: 0.5rem;"></span>
              <h3 class="d-inline-block"><%=u.getUserName()%></h3>
            </div>
          </div>
      <% } %>


    </div>

  </div>

  <!-- chat window right side -->
  <div class="col-12 col-md-9 overflow-auto p-3 mb-3 mb-md-0 mr-md-3 bg-gray" id="chatWindow" style="height: 35rem;">


    <% for(Message message : group.getMessages()){ %>

        <% if( !message.getUser().getUserName().equals(user.getUserName())){ %>

            <!--  another user message -->
            <div class="bg-white w-50 p-2 mb-3">
              <div><strong class=""><%=message.getUser().getUserName()%></strong> <%=message.getMessageDate()%> </div>
              <div><%=message.getMessage()%></div>
            </div>

        <% } %>
        <!-- user message -->
        <% if( message.getUser().getUserName().equals(user.getUserName())){ %>
            <div class="bg-white w-50 p-2 mb-3" style="margin-left: auto;margin-right: 0;" id="<%=message.getId()%>" >
              <div>
                    <div>
                        <strong class="">
                            <%=message.getUser().getUserName()%>
                        </strong>
                        <span class="btn btn-danger" style="width: 10px;height: 20px; margin-left: 5%;" onclick="deleteMessage(<%= message.getId() %>)">-</span>
                    </div>
                    <small class="fw-light">
                        <%=message.getMessageDate()%>
                    </small>
              </div>
              <div><%=message.getMessage()%></div>
            </div>
        <% } %>

    <% } %>
  </div>

</div>


<div class="container-md row m-auto bg-dark">

  <!--  window left side -->
  <div class="col-md-3 d-none d-md-block">

  </div>

  <!-- chat window right side -->
  <div class="col-12 col-md-9 p-4 d-flex align-content-end justify-content-end" id="chatWindow" style="height: 7rem;">
    <input type="text" name="message" id="message" class="form-control w-75 d-inline-block" placeholder="Message here" >
    <button class="btn btn-outline-info d-inline-block" style="margin-left: 5px;" id="btn-send">Send</button>
  </div>


</div>







</body>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.min.js" integrity="sha384-Atwg2Pkwv9vp0ygtn1JAojH0nYbwNJLPhwyoVbhoPwBhjQPR5VtM2+xf0Uwh9KtT" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
<script>

    var element = document.getElementById("chatWindow");
    element.scrollTop = element.scrollHeight;

    var ws = null;
    var client = null;


    function connect(groupid){
        ws = new SockJS("/webSocket");
        client = Stomp.over(ws);

        client.connect({},function(){
            client.subscribe("/topic/"+groupid,function(response){
                // user response
                var res = JSON.parse(response.body);

                var time = new Date();
                if(res.username=="<%=user.getUserName()%>"){
                $("#chatWindow").append("<div class='bg-white w-50 p-2 mb-3'  style='margin-left: auto;margin-right: 0;' id='"+res.messageId+"' > <div><strong>"+res.username+"</strong><span class='btn btn-danger' style='width: 10px;height: 20px; margin-left: 5%;' onclick='deleteMessage("+res.messageId+")'>-</span> <small>"+res.date+"</small> </div> <div>"+res.message+"</div> </div>");

                $("#message").val("");
                }else{
                    $("#chatWindow").append("<div class='bg-white w-50 p-2 mb-3' > <div><strong>"+res.username+"</strong> <small>"+res.date+"</small> </div> <div>"+res.message+"</div> </div>");
                }
                var element = document.getElementById("chatWindow");
                element.scrollTop = element.scrollHeight;


            })
        })

    }

    connect(<%=group.getId()%>);

    $("#btn-send").click(()=>{
      var message = $("#message").val();
      if(message.trim().length==0){

      }else{

        var message = {userid:"<%=user.getId()%>", message:message};

        client.send("/chatapp/message/${group.id}",{},JSON.stringify(message));
      }
    })


function deleteMessage(messageId){
    $.post("/message/delete",
        {
            data:messageId
        },
        function(){
            $('#'+messageId).remove();
        })
}


</script>


</html>

</body>
</html>