package com.dyks.aihubbe.global.auth.jwt;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.dyks.aihubbe.domain.user.repository.UserRepository;
import com.dyks.aihubbe.global.auth.redis.RedisService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Getter
@Slf4j
@RequiredArgsConstructor
public class JwtService {
	@Value("${jwt.secretKey}")
	private String secretKey;
	@Value("${jwt.access.expiration}")
	private Long accessTokenExpirationPeriod;
	@Value("${jwt.refresh.expiration}")
	private Long refreshTokenExpirationPeriod;
	@Value("${jwt.access.header}")
	private String accessHeader;
	@Value("${jwt.refresh.header}")
	private String refreshHeader;
	private static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
	private static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";
	private static final String EMAIL_CLAIM = "email";
	private static final String BEARER = "Bearer ";
	private final UserRepository userRepository;
	private final RedisService redisService;


	//생성
	public String createAccessToken(String email) {
		Date now = new Date();
		return JWT.create() // JWT 토큰을 생성하는 빌더 반환
				.withSubject(ACCESS_TOKEN_SUBJECT) // JWT의 Subject 지정 -> AccessToken이므로 AccessToken
				.withExpiresAt(new Date(now.getTime() + accessTokenExpirationPeriod)) // 토큰 만료 시간 설정
				//클레임으로는 저희는 email 하나만 사용합니다.
				//추가하실 경우 .withClaim(클래임 이름, 클래임 값) 으로 설정해주시면 됩니다
				.withClaim(EMAIL_CLAIM, email)
				.sign(Algorithm.HMAC512(secretKey)); // HMAC512 알고리즘 사용, application-jwt.yml에서 지정한 secret 키로 암호화
	}

	/**
	 * RefreshToken 생성
	 * RefreshToken은 Claim에 email도 넣지 않으므로 withClaim() X
	 */
	public String createRefreshToken() {
		Date now = new Date();
		return JWT.create()
				.withSubject(REFRESH_TOKEN_SUBJECT)
				.withExpiresAt(new Date(now.getTime() + refreshTokenExpirationPeriod))
				.sign(Algorithm.HMAC512(secretKey));
	}

	/**
	 * AccessToken 헤더에 실어서 보내기
	 */
	public void sendAccessToken(HttpServletResponse response, String accessToken) {
		response.setStatus(HttpServletResponse.SC_OK);

		response.setHeader(accessHeader, accessToken);
		log.info("재발급된 Access Token : {}", accessToken);
	}

	/**
	 * AccessToken + RefreshToken 헤더에 실어서 보내기
	 */
	public void sendAccessAndRefreshToken(HttpServletResponse response, String accessToken, String refreshToken) {
		response.setStatus(HttpServletResponse.SC_OK);

		setAccessTokenHeader(response, accessToken);
		setRefreshTokenHeader(response, refreshToken);
		log.info("Access Token, Refresh Token 헤더 설정 완료");
	}

	/**
	 * 헤더에서 RefreshToken 추출
	 * 토큰 형식 : Bearer XXX에서 Bearer를 제외하고 순수 토큰만 가져오기 위해서
	 * 헤더를 가져온 후 "Bearer"를 삭제(""로 replace)
	 */
	public Optional<String> extractRefreshToken(HttpServletRequest request) {
		return Optional.ofNullable(request.getHeader(refreshHeader))
				.filter(refreshToken -> refreshToken.startsWith(BEARER))
				.map(refreshToken -> refreshToken.replace(BEARER, ""));
	}

	/**
	 * 헤더에서 AccessToken 추출
	 * 토큰 형식 : Bearer XXX에서 Bearer를 제외하고 순수 토큰만 가져오기 위해서
	 * 헤더를 가져온 후 "Bearer"를 삭제(""로 replace)
	 */
	public Optional<String> extractAccessToken(HttpServletRequest request) {
		return Optional.ofNullable(request.getHeader(accessHeader))
				.filter(refreshToken -> refreshToken.startsWith(BEARER))
				.map(refreshToken -> refreshToken.replace(BEARER, ""));
	}

	/**
	 * AccessToken에서 Email 추출
	 * 추출 전에 JWT.require()로 검증기 생성
	 * verify로 AceessToken 검증 후
	 * 유효하다면 getClaim()으로 이메일 추출
	 * 유효하지 않다면 빈 Optional 객체 반환
	 */
	public Optional<String> extractEmail(String accessToken) {
		try {
			// 토큰 유효성 검사하는 데에 사용할 알고리즘이 있는 JWT verifier builder 반환
			return Optional.ofNullable(JWT.require(Algorithm.HMAC512(secretKey))
					.build() // 반환된 빌더로 JWT verifier 생성
					.verify(accessToken) // accessToken을 검증하고 유효하지 않다면 예외 발생
					.getClaim(EMAIL_CLAIM) // claim(Emial) 가져오기
					.asString());
		} catch (Exception e) {
			log.error("액세스 토큰이 유효하지 않습니다.");
			return Optional.empty();
		}
	}

	/**
	 * AccessToken 헤더 설정
	 */
	public void setAccessTokenHeader(HttpServletResponse response, String accessToken) {
		response.setHeader(accessHeader, accessToken);
	}

	/**
	 * RefreshToken 헤더 설정
	 */
	public void setRefreshTokenHeader(HttpServletResponse response, String refreshToken) {
		response.setHeader(refreshHeader, refreshToken);
	}

	/**
	 * RefreshToken DB 저장(업데이트)
	 */
	public void updateRefreshToken(String email, String refreshToken) {
		userRepository.findByEmail(email)
				.ifPresentOrElse(
						user -> user.updateRefreshToken(refreshToken),
						() -> new Exception("일치하는 회원이 없습니다.")
				);
	}

	//엑세스 토큰 검증
	public boolean isTokenValid(String token) {
		try {
			JWT.require(Algorithm.HMAC512(secretKey)).build().verify(token);
			return true;
		} catch (Exception e) {
			log.error("유효하지 않은 토큰입니다. {}", e.getMessage());
			//new InvalidTokenException();
			return false;
		}
	}

	/**
	 * RefreshToken Redis 저장
	 */
	public void saveRefreshToken(String email, String refreshToken) {
		redisService.setRefreshToken(email, refreshToken, refreshTokenExpirationPeriod);
		log.info("Refresh Token이 Redis에 저장되었습니다: {}", email);
	}

	/**
	 * RefreshToken 검증
	 * - Redis에서 저장된 토큰과 비교
	 */
	public boolean validateRefreshToken(String email, String refreshToken) {
		String storedToken = redisService.getRefreshToken(email);
		if (storedToken == null || !storedToken.equals(refreshToken)) {
			log.error("Redis에서 Refresh Token이 일치하지 않거나 없습니다.");
			return false;
		}
		return true;
	}

	/**
	 * RefreshToken 삭제
	 * - Redis에서 Refresh Token 제거
	 */
	public void deleteRefreshToken(String email) {
		redisService.deleteRefreshToken(email);
		log.info("Redis에서 Refresh Token이 삭제되었습니다: {}", email);
	}
}

