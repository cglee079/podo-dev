package com.podo.pododev.web.global.config.aop;

import com.podo.pododev.web.global.config.aop.argschecker.AllArgsNotNull;
import com.podo.pododev.web.global.config.aop.argschecker.AllArgsNotNullChecker;
import com.podo.pododev.web.global.config.aop.argschecker.NotAllArgsNullException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AllArgsNotNullCheckerTest {

//    @DisplayName("Args 에 nonNull 값이 주입됬을 때")
//    @Test
//    void testAspect01(){
//        final MockInterfaceImpl mockInterface = new MockInterfaceImpl();
//
//        final AspectJProxyFactory factory = new AspectJProxyFactory(mockInterface);
//
//        factory.addAspect(AllArgsNotNullChecker.class);
//
//        final MockInterface proxy = factory.getProxy();
//
//        assertDoesNotThrow( () -> proxy.something("something"));
//    }


    @DisplayName("Args 에 null 값이 주입됬을 때")
    @Test
    void testAspect02(){
        final MockInterfaceImpl mockInterfaceA = new MockInterfaceImpl("adfd");
        final MockInterfaceImpl mockInterfaceB = new MockInterfaceImpl("123123");

        AspectJProxyFactory factory = new AspectJProxyFactory(MockInterfaceImpl.class);

        factory.addAspect(AllArgsNotNullChecker.class);
        factory.setProxyTargetClass(true);

        factory.setTarget(mockInterfaceA);
        final MockInterfaceImpl proxyA = factory.getProxy();

        factory.setTarget(mockInterfaceB);
        final MockInterfaceImpl proxyB = factory.getProxy();

        proxyA.somethin2g(proxyB);
    }


    private class MockInterfaceImpl{

        private String dd = "dd";

        public MockInterfaceImpl(String adfd) {
        }

        @AllArgsNotNull
        public void something(String a) {
            //Something
        }


        @AllArgsNotNull
        public void somethin2g(MockInterfaceImpl dd) {
            //Something
            System.out.println(dd.dd);
            System.out.println(dd.getDd());
        }

        private String getDd() {
            return dd;
        }
    }
}
