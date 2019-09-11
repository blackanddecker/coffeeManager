var data={};

function select_product(){
  var shop_id = localStorage.getItem('shop_id');
  
  $.ajax({
      url: "http://127.0.0.1:5000/select_product/"+shop_id,
      type: "GET",
      dataType : "json",
      success: function(res){
        users = res;
      },
      error: function(xhr , status){
        document.write("<p>" +status+"</p>");
      }          

  });

}
  


var users = [
  {
    id: 1,
    name: "Bob",
    address: "Manila",
    age: 27
  },
  {
    id: 2,
    name: "Harry",
    address: "Baguio",
    age: 32
  }
];

$.each(users, function(i, user) {
  appendToUsrTable(user);
});

$("form").submit(function(e) {
  e.preventDefault();
});



function appendToUsrTable(user) {
  $("#userTable > tbody:last-child").append(`
        <tr id="user-${user.id}">
            <td class="userData" name="name">${user.name}</td>
            '<td class="userData" name="address">${user.address}</td>
            '<td id="tdAge" class="userData" name="age">${user.age}</td>
        </tr>
    `);
}