# 웹 애플리케이션 서버
## 진행 방법
* 웹 애플리케이션 서버 요구사항을 파악한다.
* 요구사항에 대한 구현을 완료한 후 자신의 github 아이디에 해당하는 브랜치에 Pull Request(이하 PR)를 통해 코드 리뷰 요청을 한다.
* 코드 리뷰 피드백에 대한 개선 작업을 하고 다시 PUSH한다.
* 모든 피드백을 완료하면 다음 단계를 도전하고 앞의 과정을 반복한다.

## 온라인 코드 리뷰 과정
* [텍스트와 이미지로 살펴보는 온라인 코드 리뷰 과정](https://github.com/next-step/nextstep-docs/tree/master/codereview)

## Todo

- [x] method parser 는 첫 번째 라인을 통해 BufferedReader 를 돌려 method 를 구한다.
- [x] path parser 는 첫 번째 라인을 통해 BufferedReader 를 돌려 path 를 구한다.
- [x] querystring parser 는 첫 번째 라인을 통해 BufferedReader 를 돌려 query string 정보를 Map 으로 구한다.
- [x] Header parser 는 두 번째부터 쭉 BufferedReader 를 돌려 Map 으로 구한다.
- [x] body parser 는 body 를 받아 저장한다.
- [x] GET `/index.html` 요청이 들어오면 해당 페이지를 돌려준다.
    - [x] BufferedReader 를 활용해서 http header 를 알 수 있다.
        - [x] 이 때 line 이 Null 인 경우의 예외 처리를 진행한다. `if (line == null) { return;}`
        - [x] Header 의 첫 라인을 통해 요청 URL 을 추출할 수 있다.
    - [x] path 에 해당하는 파일을 `src/main/resources` 디렉토리에서 읽어 전달한다.
- [x] GET `./css/style.css` 요청이 들어오면 css stylesheet 파일을 지원한다.
- [x] GET `/user/create` 요청이 오면 User 를 저장한다.
    - [x] 이 때 request parameter 에서 URL 을 통해 접근 경로와 이름=값 을 추출해 User 클래스에 담는다.
- [x] POST `/user/create` 요청이 오면 User 를 저장하고 `/user/form.html` 으로 이동시킨다.
    - [x] 이 때 request body 에서 이름=값 을 저장하여 이를 통해 User 객체를 생성한다.
- [x] `user/create` 단계 후 `index.html`로 이동시킨다 (302 활용)