//создаем куку с именем welcome со значением 1, временем жизни 3 дня и доступной на всем сайте
setCookie('welcome', 1, { path: '/', expires: 3600*24*3 }); 
if(getCookie('welcome') == '1') {
    alert('Кука welcome равна еденице!');
}
