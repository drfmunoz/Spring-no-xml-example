<%--
  Created by IntelliJ IDEA.
  User: freddy
  Date: 12/4/12
  Time: 12:27 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head><title>Messaging example for ${author.name}</title></head>
<body>
Talk to an author (click on the author name)
<div style="width: 200px" id="author-list">

</div>

<script type="text/javascript" src="/resources/jquery-1.8.3.min.js"></script>
<script type="text/javascript">
    var authorId =${author.id};
    var getList=function(){
        $.ajax({
            url:'/ajax/author',
            success:function(result) {
                if (result) {
                    var list = $('#author-list');
                    list.html("");
                    result.forEach(function(author) {
                        if (author.id != authorId) {
                            list.append($("<div>").append($('<a href="' + author.id + '/index.html">').text(author.name)));

                        }
                    });

                }
                setTimeout(getList,3000);
            }
        });
    }
    $(document).ready(function() {
       getList();
    });

</script>
</body>
</html>