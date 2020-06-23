package http.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class QueryStringTest {
    @DisplayName("쿼리스트링을 변수와 값의 쌍 형태로 파싱")
    @Test
    void test_parsing_querystring_should_pass() {
        // given
        String fullQueryString = "userId=crystal&password=password&name=Sujung";
        // when
        QueryString queryString = new QueryString(fullQueryString);
        // then
        assertAll(
                () -> assertThat(queryString.size()).isEqualTo(3),
                () -> assertThat(queryString.getParameter("userId")).hasSize(1).containsExactly("crystal"),
                () -> assertThat(queryString.getParameter("password")).hasSize(1).containsExactly("password"),
                () -> assertThat(queryString.getParameter("name")).hasSize(1).containsExactly("Sujung")
        );
    }

    @DisplayName("키는 존재하나 값이 표기되지 않은 파라미터는 값이 null로 저장")
    @Test
    void parse_querystring_without_value() {
        // given
        String fullQueryString = "userId=";
        // when
        QueryString queryString = new QueryString(fullQueryString);
        // then
        assertThat(queryString.getParameter("userId")).hasSize(1).containsNull();
    }

    @DisplayName("구분자가 포함되지 않은 경우 그 자체가 키가 되고 값이 null로 저장")
    @Test
    void parse_querystring_without_delimiter() {
        // given
        String fullQueryString = "userId";
        // when
        QueryString queryString = new QueryString(fullQueryString);
        // then
        assertThat(queryString.getParameter("userId")).hasSize(1).containsNull();
    }

    @DisplayName("구분자가 맨 앞에 위치하는 경우 그 자체가 키가 되고 값이 null로 저장")
    @Test
    void parse_querystring_startswith_delimiter() {
        // given
        String fullQueryString = "=userId";
        // when
        QueryString queryString = new QueryString(fullQueryString);
        // then
        assertThat(queryString.getParameter("=userId")).hasSize(1).containsNull();
    }

    @DisplayName("빈 문자열의 경우 빈 MultiValueMap으로 초기화")
    @Test
    void test_parsing_empty() {
        // given
        String fullQueryString = "";
        // when
        QueryString queryString = new QueryString(fullQueryString);
        // then
        assertThat(queryString.size()).isEqualTo(0);
    }

    @DisplayName("구분자만 있는 경우 무시")
    @Test
    void test_parsing_only_delimiter() {
        // given
        String fullQueryString = "=";
        // when
        QueryString queryString = new QueryString(fullQueryString);
        // then
        assertThat(queryString.size()).isEqualTo(0);
    }

    @DisplayName("최종 파싱된 파라미터에 URL 디코딩 처리")
    @Test
    void test_url_decoding() {
        // given
        String fullQueryString = "userId=crystal&password=password&name=%EC%9E%84%EC%88%98%EC%A0%95&email=crystal%40naver.com";
        // when
        QueryString queryString = new QueryString(fullQueryString);
        // then
        assertThat(queryString.getFirstParameter("name")).isEqualTo("임수정");
        assertThat(queryString.getFirstParameter("email")).isEqualTo("crystal@naver.com");
    }
}