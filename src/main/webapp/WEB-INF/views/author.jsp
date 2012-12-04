<html>
<body>
<h2>Authors</h2>
<hr>
Create a new User:
<input type="text" name="author" id="author-name"/> <input type="button" value="Create" id="create-button"/>

<hr>
Log in as an existing user:
<select name="author" id="authorSelect"> <input type="button" value="Open" id="load-button"/>
    <option value="-1">--</option>
</select>

<script type="text/javascript" src="/resources/jquery-1.8.3.min.js"></script>
<script type="text/javascript">
    $(document).ready(function() {
        $.ajax({
            url:'ajax/author',
            success:function(result) {
                if (result) {
                    var options = $('#authorSelect');
                    result.forEach(function(author) {
                        options.append($("<option />").val(author.id).text(author.name));
                    });
                }
            }
        });
        var createAndDo = function(uname) {
            var nsName = {name:uname};
            $.ajax({
                url:'ajax/author',
                type:'POST',
                contentType : 'application/json',
                data:JSON.stringify(nsName),
                dataType: 'json',
                success:function(result) {
                    console.log(result);
                    document.location = 'message/index.html';
                }
            });
        }

        $('#load-button').click(function() {
            var authorId = $('#authorSelect').val();
            $.ajax({
                url:'ajax/author/'+authorId,
                type:'PUT',
                success:function(result) {
                    console.log(result);
                    document.location = 'message/index.html';
                }
            });

        });
        $('#create-button').click(function() {
            var name = $('#author-name').val();
            $.ajax({
                url:'ajax/author/check?name=' + name,
                success:function(result) {
                    if (result) {
                        alert('name already exists');
                    }
                    else {
                        createAndDo(name);
                    }
                }
            });
        });
    });

</script>
</body>
</html>
