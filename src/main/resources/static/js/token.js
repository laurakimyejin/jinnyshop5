const token = searchParam('token')

if (token) {
    localStorage.setItem("access_token", token)
}

function searchParam(key) {
    return new URLSearchParams(location.search).get(key);
}

function formatSeconds(seconds) {
    if (seconds <= 0) {
        return "timeOut"
    }
    var hours = Math.floor(seconds / 3600); 
    var minutes = Math.floor((seconds % 3600) / 60); 
    var seconds = seconds % 60;
    if (hours >= 0 && hours < 10) {
    hours = "0" + hours;
    }
    if (minutes >= 0 && minutes < 10) {
    minutes = "0" + minutes;
    }
    if (seconds >= 0 && seconds < 10) {
    seconds = "0" + seconds;
    }

    return "[" + hours + ":" + minutes + ":" + seconds + "]";
}

if (localStorage.getItem('access_token')) {
    const localToken = localStorage.getItem('access_token');
    var parts = localToken.split('.');
    var payload = JSON.parse(atob(parts[1]));
    const nick = payload.nick;
    const memberName = payload.name;
    const email = payload.sub;

    if (memberName) {
        document.getElementById("nickname").innerText = memberName;
    }
    if (nick) {
        document.getElementById("nickname").innerText = nick;
    }
    if (email) {
        document.getElementById("email").innerText = email;
    }



    var tokenTimeout = (payload.exp)-(Math.floor(new Date().getTime() / 1000));

    if (tokenTimeout) {
        document.getElementById("tokenTimeout").innerText = formatSeconds(tokenTimeout);
    }
}
