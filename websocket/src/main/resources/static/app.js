var stompClient = null;

function connect() {
    var socket = new SockJS('/websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame) {
        stompClient.subscribe('/chat/1/save', function (msg) {
            addMessage(JSON.parse(msg.body))
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
}

function sendMessage() {
    stompClient.send(
        "/app/chat/1/save", {},
        JSON.stringify(
            {
                'name': $("#name").val(),
                'text': $("#text").val()
            }
        )
    );
}

function addMessage(message) {
    $("#chat").append("<tr><td>" + message.text + "</td></tr>");
}

$(function () {
   $("form").on('submit', function(e) {
       e.preventDefault();
   });
   connect();
   $("#send").click(
       function () {
           sendMessage();
       }
   )
});