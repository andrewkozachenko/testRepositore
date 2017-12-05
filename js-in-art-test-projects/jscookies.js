function setCookie(name, value, options) {
  options = options || {}; //по умолчанию нет параметров (допустимые: expires, domain, secure, path)
  var expires = options.expires;

  if (typeof expires == "number" && expires) { //если указано время жизни, и это число
    var d = new Date();
    d.setTime(d.getTime() + expires * 1000); //expires в секундах
    expires = options.expires = d;
  }
  if (expires && expires.toUTCString) {
    options.expires = expires.toUTCString();
  }

  value = encodeURIComponent(value); 
  var data = name + "=" + value; //строка в формате cookie имеет вид "имя_куки=значение"

  for (var propName in options) {   //дописываем параметры кук (domain, secure, path)
    data += "; " + propName;
    var propValue = options[propName];
    if (propValue !== true) {
      data += "=" + propValue;
    }
  }

  document.cookie = data; //сохраняем куку
}
