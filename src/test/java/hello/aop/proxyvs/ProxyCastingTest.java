package hello.aop.proxyvs;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;

import hello.aop.member.MemberService;
import hello.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProxyCastingTest {

	@Test
	void jdkProxy() {
		MemberServiceImpl target = new MemberServiceImpl();
		ProxyFactory proxyFactory = new ProxyFactory(target);
		proxyFactory.setProxyTargetClass(false);

		MemberService memberServiceProxy = (MemberService) proxyFactory.getProxy();
		assertThrows(ClassCastException.class, () -> {
			MemberServiceImpl memberService = (MemberServiceImpl) memberServiceProxy; // proxy이므로 casing 실패
		});
	}

	@Test
	void cglibProxy() {
		MemberServiceImpl target = new MemberServiceImpl();
		ProxyFactory proxyFactory = new ProxyFactory(target);
		proxyFactory.setProxyTargetClass(true);

		MemberService memberServiceProxy = (MemberService) proxyFactory.getProxy();

		MemberServiceImpl memberService = (MemberServiceImpl) memberServiceProxy;
	}
}
