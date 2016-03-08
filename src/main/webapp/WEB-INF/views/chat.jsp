<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
<meta charset="UTF-8">
<title>WebSocket Tester</title>
<script src="https://cdnjs.cloudflare.com/ajax/libs/sockjs-client/0.3.4/sockjs.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/stomp.js/2.3.3/stomp.min.js"></script>
<script src="https://code.jquery.com/jquery-1.11.0.min.js"></script>
<script language="javascript" type="text/javascript">

    //Create stomp client over sockJS protocol
     var socket = new SockJS("/ProMeets/ws");
     var stompClient = Stomp.over(socket);

     // Callback function to be called when stomp client is connected to server
     var connectCallback = function() 
     {
       stompClient.subscribe('/topic/chat', renderMesses);
     }; 

     // Callback function to be called when stomp client could not connect to server
     var errorCallback = function(error) 
     {
       alert(error.headers.message);
     };
     
        function renderMesses(frame) 
        {
          $('#message').empty();
          var messages = JSON.parse(frame.body);
          for(var i in messages) {
            var message = messages[i];
            var date = new Date(message.time);
            $('#message').append(
              $('<tr>').append(
                $('<td>').html(date.getHours() + ':' + date.getMinutes()+ ':' + date.getSeconds()),
                $('<td>').html(message.text)
              )
            );
          }
        }
        
        // Connect to server via websocket
        stompClient.connect("guest", "guest", connectCallback, errorCallback);
        
        // Register handler for add button
    $(document).ready(function() 
    {
      $('.add').click(function(e)
      {
        e.preventDefault();
        var message = $('.message').val();
        var jsonstr = JSON.stringify({'text': message });
        stompClient.send("/app/chat/1/send", {}, jsonstr);
        return false;
      });
    });

</script>
</head>
    <body>
        
        <p>
            Message: <input type="text" class="message"/>
            <button class="add">Add</button>
        </p>
        <p>
        <table>
            <tbody id="message"></tbody>
        </table>
		
    </body>
   