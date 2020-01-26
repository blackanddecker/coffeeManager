var users=[];
selectOrder();

function compare( a, b ) {
  if ( a.status > b.status ){
    return -1;
  }
  if ( a.status < b.status ){
    return 1;
  }
  return 0;
}



function selectOrder(){
  var shop_id = localStorage.getItem('shop_id');
  var start_date = '2009-10-28';
  var end_date = '2029-10-28';
  var sendInfo = {shop_id : parseInt (shop_id), start_date : start_date,end_date : end_date };
  $.ajax({
      url: "/select_order",
      type: "POST",
      dataType: 'json',
      contentType: "application/json; charset=utf-8",
      traditional: true,      
      success: function(res){
        users = res;
        users.sort( compare );
        for (i=0; i< users.length ; i++){
          appendToUsrTable(users[i]);
        }
      },
      error: function(xhr , status){
        document.write("<p>" +status+"</p>");
      },          
      data: JSON.stringify(sendInfo)

  });
  console.log("OK")

};


$.each(users, function(i, user) {
  appendToUsrTable(user);
});

$.each(users, function(i, user) {
  appendToOrder(user);
});

$("form").submit(function(e) {
  e.preventDefault();
});










function update_delivered_order(id) {
  
  var msg = "User updated successfully!";
  var sendInfo={};

  sendInfo.order_id =id

  if (sendInfo.order_id) {
    $.ajax({
        url: "/update_delivered_order",
        type: "POST",
            dataType: 'json',
            contentType: "application/json; charset=utf-8",
            traditional: true,
        success: function(res){
          window.location.href = "orders";
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

function info(id) {
  
  var msg = "User updated successfully!";
  var sendInfo={};

  sendInfo.order_id =id

  if (sendInfo.order_id) {
    $.ajax({
        url: "/order_info",
        type: "POST",
            dataType: 'json',
            contentType: "application/json; charset=utf-8",
            traditional: true,
        success: function(res){
          users = res;
          for (i=0; i< users.length ; i++){
            appendToOrder(users[i]);
          }        },
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


function update_paid_order(id) {
  
  var msg = "User updated successfully!";
  var sendInfo={};

  sendInfo.order_id =id

  if (sendInfo.order_id) {
    $.ajax({
        url: "/update_paid_order",
        type: "POST",
            dataType: 'json',
            contentType: "application/json; charset=utf-8",
            traditional: true,
        success: function(res){
          window.location.href = "orders";
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










// edit user form 
function appendToOrder(user) {
  
      console.log("appendToOrder", user)
      $("#infoTable > tbody:last-child").append(`
           <tr id="user-${user.o_info}">
           '<td class="info" name="name">${user.name}</td>
           '<td class="info" name="details">${user.details}</td>
        </tr>
`);
}


//    <tr id="user-${user.order_id}">
//     <td class="userData" name="name">${user.name}</td>
//     <td class="userData" name="status">${user.details}</td>
// </tr>
// <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
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
          sendInfo.status = parseInt(value);
        } else if (attr == "email") {
          sendInfo.email = value;
        }
         else if (attr == "password") {
          sendInfo.password = parseInt(value);
        }
        sendInfo.id =id
      });
    }
  });

  if (sendInfo.idemployee) {
    $.ajax({
        url: "/updateUser",
        type: "POST",
            dataType: 'json',
            contentType: "application/json; charset=utf-8",
            traditional: true,
        success: function(res){
          window.location.href = "users.html";
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
  $("#orderTable > tbody:last-child").append(`
        <tr id="user-${user.id}">
            <td class="userData" name="name">${user.id}</td>
            '<td class="userData" name="status">${user.status}</td>
            '<td class="userData" name="price">${user.price}</td>
            '<td class="userData" name="no_table">${user.table_id}</td>
            '<td class="userData" name="date_paid">${user.date_paid}</td>
            '<td align="center">
                <button class="btn btn-success form-control" onClick="info(${user.id})" data-toggle="modal" data-target="#myModal")">INFO</button>
            </td>
            '<td align="center">
                <button class="btn btn-success form-control" onClick="update_delivered_order(${user.id})" )">Delivered</button>
            </td>
            '<td align="center">
                <button class="btn btn-success form-control" onClick="update_paid_order(${user.id})" )">Paid</button>
            </td>
        </tr>
    `);
}