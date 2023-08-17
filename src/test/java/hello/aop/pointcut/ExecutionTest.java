package hello.aop.pointcut;

import static org.assertj.core.api.Assertions.*;

import java.lang.reflect.Method;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import hello.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExecutionTest {
	AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
	Method hellMethod;
	@BeforeEach
	public void init() throws NoSuchMethodException {
		hellMethod = MemberServiceImpl.class.getMethod("hello", String.class);
	}

	@Test
	void printMethod() {
		log.info("helloMethod={}", hellMethod);
	}

	@Test
	void exactMatch() {
		pointcut.setExpression("execution(public String hello.aop.member.MemberServiceImpl.hello(String))");
		assertThat(pointcut.matches(hellMethod, MemberServiceImpl.class)).isTrue();
	}

	@Test
	void allMatch() {
		pointcut.setExpression("execution(* *(..))");
		assertThat(pointcut.matches(hellMethod, MemberServiceImpl.class)).isTrue();
	}

	@Test
	void nameMatch() {
		pointcut.setExpression("execution(* hello(..))");
		assertThat(pointcut.matches(hellMethod, MemberServiceImpl.class)).isTrue();
	}

	@Test
	void nameMatchStart1() {
		pointcut.setExpression("execution(* hel*(..))");
		assertThat(pointcut.matches(hellMethod, MemberServiceImpl.class)).isTrue();
	}

	@Test
	void nameMatchStart2() {
		pointcut.setExpression("execution(* *el*(..))");
		assertThat(pointcut.matches(hellMethod, MemberServiceImpl.class)).isTrue();
	}

	@Test
	void packageExactMatch1() {
		pointcut.setExpression("execution(* hello.aop.member.MemberServiceImpl.hello(..))");
		assertThat(pointcut.matches(hellMethod, MemberServiceImpl.class)).isTrue();
	}

	@Test
	void packageExactMatch2() {
		pointcut.setExpression("execution(* hello.aop.member.*.*(..))");
		assertThat(pointcut.matches(hellMethod, MemberServiceImpl.class)).isTrue();
	}

	@Test
	void packageMatchSubPackage1() {
		pointcut.setExpression("execution(* hello.aop.member..*.*(..))");
		assertThat(pointcut.matches(hellMethod, MemberServiceImpl.class)).isTrue();
	}

	@Test
	void packageMatchSubPackage2() {
		pointcut.setExpression("execution(* hello.aop..*.*(..))");
		assertThat(pointcut.matches(hellMethod, MemberServiceImpl.class)).isTrue();
	}

	@Test
	void typeExactMatch() {
		pointcut.setExpression("execution(* hello.aop.member.MemberServiceImpl.*(..))");
		assertThat(pointcut.matches(hellMethod, MemberServiceImpl.class)).isTrue();
	}

	@Test
	void typeMatchSuperType() {
		pointcut.setExpression("execution(* hello.aop.member.MemberService.*(..))");
		assertThat(pointcut.matches(hellMethod, MemberServiceImpl.class)).isTrue();
	}

	@Test
	void typeMatchInternal() throws NoSuchMethodException {
		pointcut.setExpression("execution(* hello.aop.member.MemberService.*(..))");
		Method internalMethod = MemberServiceImpl.class.getMethod("internal", String.class);
		assertThat(pointcut.matches(internalMethod, MemberServiceImpl.class)).isFalse();
	}

	@Test
	void argsMatch() {
		pointcut.setExpression("execution(* *(String))");
		assertThat(pointcut.matches(hellMethod, MemberServiceImpl.class)).isTrue();
	}

	@Test
	void argsMatchNoArgs() {
		pointcut.setExpression("execution(* *())");
		assertThat(pointcut.matches(hellMethod, MemberServiceImpl.class)).isFalse();
	}

	@Test
	void argsMatchStar() {
		pointcut.setExpression("execution(* *(*))");
		assertThat(pointcut.matches(hellMethod, MemberServiceImpl.class)).isTrue();
	}

	@Test
	void argsMatchAll() {
		pointcut.setExpression("execution(* *(..))");
		assertThat(pointcut.matches(hellMethod, MemberServiceImpl.class)).isTrue();
	}

	@Test
	void argsMatchAComplex() {
		pointcut.setExpression("execution(* *(String, ..))");
		assertThat(pointcut.matches(hellMethod, MemberServiceImpl.class)).isTrue();
	}
}
