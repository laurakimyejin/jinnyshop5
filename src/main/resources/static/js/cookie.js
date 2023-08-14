
// 쿠키를 가져오는 함수
function getCookie(key) {

    console.log('쿠키')

    var result = null;
    var cookie = document.cookie.split(';');
    cookie.some(function (item) {
        item = item.replace(' ', '');

        var dic = item.split('=');

        if (key === dic[0]) {
            result = dic[1];
            return true;
        }
    });

    return result;
}

// HTTP 요청을 보내는 함수
function httpRequest(method, url, body, success, fail) {
    fetch(url, {
        method: method,
        headers: { // 로컬 스토리지에서 액세스 토큰 값을 가져와 헤더에 추가
            Authorization: 'Bearer ' + localStorage.getItem('access_token'),
            'Content-Type': 'application/json'
        },
        body: body,
    }).then(response => {
        if (response.status === 200 || response.status === 201) {
            return success();
        }
        const refresh_token = getCookie('refresh_token');
        if (response.status === 401 && refresh_token) {
            fetch('/api/token', {
                method: 'POST',
                headers: {
                    Authorization: 'Bearer ' + localStorage.getItem('access_token'),
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    refreshToken: getCookie('refresh_token')
                }),
            })
                .then(res => {
                    if (res.ok) {
                        return res.json();
                    }
                })
                .then(result => { // 재발급이 성공하면 로컬 스토리지값을 새로운 액세스 토큰으로 교체
                    localStorage.setItem('access_token', result.accessToken);
                    httpRequest(method, url, body, success, fail);
                })
                .catch(error => fail());
        } else {
            return fail();
        }
    });
}

const logout = document.getElementById('logout');
if (logout) {
    console.log("로그아웃")
    // 로그아웃 링크를 클릭하면 /members/logout로 요청을 보낸다
    logout.addEventListener('click', event => {
        event.preventDefault(); // 기본 동작인 페이지 이동을 막음
        // var url = logout.getAttribute('href'); // a 태그의 href 속성 값을 가져옴
        var url = "api/token"
        body = JSON.stringify({
            refreshToken: getCookie('refresh_token'),
            subject: localStorage.getItem('access_token')
        });
        function success() {
            alert('로그아웃 성공');
            location.replace('/members/login');
        };
        function fail() {
            alert('로그아웃 실패');
            location.replace('/members/login');
        };

        httpRequest('DELETE', url, body, success, fail)
        localStorage.removeItem('access_token')
    });
    
}