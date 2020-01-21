
var users=[];
selectUser();
function selectUser(){
  var shop_id = localStorage.getItem('shop_id');
  
  $.ajax({
      url: "/selectUser/"+shop_id,
      type: "GET",
      dataType : "json",
      success: function(res){
        users = res;
        for (i=0; i< users.length ; i++){
          appendToUsrTable(users[i]);
        }
      },
      error: function(xhr , status){
        document.write("<p>" +status+"</p>");
      }          

  });

};


$.each(users, function(i, user) {
  appendToUsrTable(user);
});

$("form").submit(function(e) {
  e.preventDefault();
});

//add
$("form#addUser").submit(function() {
  var shop_id = localStorage.getItem('shop_id');
  var sendInfo = {

    name:$('#name').val(),
    password: $('#password').val(),
    email: $('#email').val(),
    status: $('#status').val(),
    shop_id : parseInt (shop_id)
     };

  if (sendInfo.name && sendInfo.password && sendInfo.email && sendInfo.status) {
    $.ajax({
        url: "/addUser",
        type: "POST",
            dataType: 'json',
            contentType: "application/json; charset=utf-8",
            traditional: true,
        success: function(res){
          window.location.href = "/users";

        },
        error: function(xhr , status){
          document.write("<p>" +status+"</p>");
        },
        data: JSON.stringify(sendInfo)
      });
    } 
    else {
    alert("All fields must have a valid value.");
  }

});


// edit user form 
function editUser(id) {
  users.forEach(function(user, i) {
    if (user.id == id) {
      $(".modal-body").empty().append(`
                <form id="updateUser" action="">
                    
                    <label for="name">Name</label>
                    <input class="form-control" type="text" name="name" value="${user.name}"/>
                    
                    <label for="status">Status</label>
                    <input class="form-control" type="text" name="status" value="${user.status}"/>
                    
                    <label for="email">Email</label>
                    <input class="form-control" type="text" name="email" value="${user.email}"/>
                    
                    <label for="details">Password</label>
                    <input class="form-control" type="text" name="password" value="${user.password}"/>
            `);
      $(".modal-footer").empty().append(`
                    <button type="button" type="submit" class="btn btn-primary" onClick="updateUser(${id})">Save changes</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                </form>
            `);
    }
  });
}


function updateUser(id) {
  
  var msg = "User updated successfully!";
  var sendInfo={};
  users.forEach(function(user, i) {
    if (user.id == id) {
      $("#updateUser").children("input").each(function() {
        var value = $(this).val();
        var attr = $(this).attr("name");
        if (attr == "name") {
          sendInfo.name = value;
        } else if (attr == "status") {
          sendInfo.status = value;
        } else if (attr == "email") {
          sendInfo.email = value;
        }
         else if (attr == "password") {
          sendInfo.password = value;
        }
        sendInfo.id =id
      });
    }
  });

  if (sendInfo.id) {
    $.ajax({
        url: "/updateUser",
        type: "POST",
            dataType: 'json',
            contentType: "application/json; charset=utf-8",
            traditional: true,
        success: function(res){
          window.location.href = "users";
        },
        error: function(xhr , status){
          document.write("<p>" +status+"</p>");
        },
        data: JSON.stringify(sendInfo)
      });
    } 
    else {
    alert("All fields must have a valid value.");
  }
}

function flashMessage(msg) {
  $(".flashMsg").remove();
  $(".row").prepend(`
        <div class="col-sm-12"><div class="flashMsg alert alert-success alert-dismissible fade in" role="alert"> <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">×</span></button> <strong>${msg}</strong></div></div>
    `);
}



// Add products to table 
function appendToUsrTable(user) {
  $("#userTable > tbody:last-child").append(`
        <tr id="user-${user.id}">
            <td class="userData" name="name">${user.name}</td>
            '<td class="userData" name="status">${user.status}</td>
            '<td class="userData" name="email">${user.email}</td>
            '<td align="center">
                <button class="btn btn-success form-control" onClick="editUser(${user.id})" data-toggle="modal" data-target="#myModal")">EDIT</button>
            </td>
        </tr>
    `);
}