package com.smartaml.shared.context;

import static org.junit.Assert.*;

import java.util.UUID;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test TenantContext ThreadLocal functionality.
 */
public class TenantContextTest {

    private UUID testTenantId;

    @Before
    public void setUp() {
        testTenantId = UUID.randomUUID();
    }

    @After
    public void tearDown() {
        TenantContext.clear();
    }

    @Test
    public void testSetAndGet() {
        TenantContext.set(testTenantId);
        assertEquals(testTenantId, TenantContext.get());
    }

    @Test
    public void testClear() {
        TenantContext.set(testTenantId);
        TenantContext.clear();
        assertNull(TenantContext.get());
    }

    @Test
    public void testSuperAdminFlag() {
        assertFalse(TenantContext.isSuperAdmin());
        TenantContext.setSuperAdmin(true);
        assertTrue(TenantContext.isSuperAdmin());
    }

    @Test
    public void testChildThreadInheritance() throws InterruptedException {
        TenantContext.set(testTenantId);
        TenantContext.setSuperAdmin(true);

        Thread[] childResults = new Thread[1];
        UUID[] childTenantId = new UUID[1];
        Boolean[] childIsSuperAdmin = new Boolean[1];

        Thread childThread = new Thread(() -> {
            childTenantId[0] = TenantContext.get();
            childIsSuperAdmin[0] = TenantContext.isSuperAdmin();
        });

        childThread.start();
        childThread.join();

        assertEquals(testTenantId, childTenantId[0]);
        assertTrue(childIsSuperAdmin[0]);
    }
}
