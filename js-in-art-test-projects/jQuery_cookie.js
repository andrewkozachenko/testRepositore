// так можно установить новые кукисы или переписать значения у уже существующих:
$.cookie('cookie_name', 'cookie_value');

// получить значение существующих кукисов можно так:
$.cookie('cookie_name');
// если запрашиваемых кукисов не существует, то эта функция вернет null

// а так можно удалить кукисы
$.cookie('cookie_name', null);



$("p").on("click", function(e){
  // ...
});

$(".myClass").on("mouseenter", (function(e){
  // ...
}));


var data =$("input").val();
// var data = document.getElementByTagName("input").value;

$("input").val("new value");
// document.getElementByTagName("input").value = "new value";


$("p").css("color", "red").css("background", "yellow").css("font-size", "22px");



$.ajax({
  url: "/myHomePage.html", 
  success: function(response) {
    alert(response); 
  }, 
  error: function(xhr) { 
    alert(Error! Status: + xhr.status);   
  } 
}); 
