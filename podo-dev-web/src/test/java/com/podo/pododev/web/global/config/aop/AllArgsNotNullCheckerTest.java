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
        AspectJProxyFactory factory = new AspectJProxyFactory(MockInterfaceImpl.class);

        factory.setProxyTargetClass(true);
        final MockInterfaceImpl proxy = factory.getProxy();

        assertThrows(NotAllArgsNullException.class, () -> proxy.something(null));
    }


    private class MockInterfaceImpl{

        public MockInterfaceImpl() {
        }

        @AllArgsNotNull
        public void something(String a) {
            //Something
        }
    }
}
