<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
         language="java" %>
<html>
<body>
<h2>Chat with ${recipient.name}</h2>
Conversation with <span id="recipient-name"></span>:

<div id="conversation">

</div>


<input type="text" id="messageInput"/>


<script type="text/javascript" src="/resources/jquery-1.8.3.min.js"></script>
<script type="text/javascript">
    var recipientId =${recipientId};
    var recipient = {};
    var lastMessageId = 0;
    var messageUrl = '/ajax/${recipientId}/message';

    var loadMessages = function() {
        $.ajax({
            url:messageUrl,
            data:{starting:lastMessageId},
            success:function(result) {
                if (result) {
                    var list = $('#conversation');
                    result.forEach(function(element) {
                        list.append($("<div>").text(element.fromAuthor.name+' ('+new Date(element.sentDate) + '): ' + element.message));
                    });
                    if (result.length > 0) {
                        lastMessageId=result[result.length - 1].id;
                    }
                    console.log(result);
                }
                setTimeout(loadMessages, 1000);
            }
        });
    }
    $(document).ready(function() {
        $.ajax({
            url:'/ajax/author/' + recipientId,
            success:function(result) {
                if (result) {
                    recipient = result;
                    $('#recipient-name').text(recipient.name);
                    loadMessages();
                }
            }
        });
        $('#messageInput').bind('keypress', function(e) {
            var code = (e.keyCode ? e.keyCode : e.which);
            var message={message:$('#messageInput').val()};
            if (code == 13) {
                $.ajax({
                    url:messageUrl,
                    type:'POST',
                    contentType : 'application/json',
                    data:JSON.stringify(message),
                    dataType: 'json',
                    success:function(result) {
                        $('#messageInput').val('');
                    }
                });
            }
        })
    });

</script>
</body>
</html>
