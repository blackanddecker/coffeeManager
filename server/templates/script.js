
var users=[];
select_product();
function select_product(){
  var shop_id = localStorage.getItem('shop_id');
  
  $.ajax({
      url: "http://127.0.0.1:5000/select_product/"+shop_id,
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
$("form#addProduct").submit(function() {
  var shop_id = localStorage.getItem('shop_id');
  var sendInfo = {

    name:$('#name').val(),
    price: parseInt ($('#price').val()) ,
    available: parseInt ( $('#available').val() ) ,
    details: $('#details').val(),
    shop_id : parseInt (shop_id)
     };

  if (sendInfo.name && sendInfo.price && sendInfo.details && sendInfo.available) {
    $.ajax({
        url: "http://127.0.0.1:5000/add_product",
        type: "POST",
            dataType: 'json',
            contentType: "application/json; charset=utf-8",
            traditional: true,
        success: function(res){
          window.location.href = "products.html";

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
                    <label for="price">Price</label>
                    <input class="form-control" type="number" name="price" value="${user.price}"/>
                    <label for="available">Available</label>
                    <input class="form-control" type="number" name="available" value="${user.available}"/>
                    <label for="details">Details</label>
                    <input class="form-control" type="text" name="details" value="${user.details}"/>
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
        } else if (attr == "price") {
          sendInfo.price = parseInt(value);
        } else if (attr == "details") {
          sendInfo.details = value;
        }
         else if (attr == "available") {
          sendInfo.available = parseInt(value);
        }
        sendInfo.product_id =id
      });
    }
  });

  if (sendInfo.product_id) {
    $.ajax({
        url: "http://127.0.0.1:5000/update_product",
        type: "POST",
            dataType: 'json',
            contentType: "application/json; charset=utf-8",
            traditional: true,
        success: function(res){
          window.location.href = "products.html";

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
  $("#productTable > tbody:last-child").append(`
        <tr id="user-${user.id}">
            <td class="userData" name="name">${user.name}</td>
            '<td class="userData" name="price">${user.price}</td>
            '<td class="userData" name="available">${user.available}</td>
            '<td class="userData" name="available">${user.details}</td>
            '<td align="center">
                <button class="btn btn-success form-control" onClick="editUser(${user.id})" data-toggle="modal" data-target="#myModal")">EDIT</button>
            </td>
        </tr>
    `);
}