package junit.ipallocations;

import java.util.UUID;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.uca.network.Application;
import com.uca.network.extension.allocations.service.IpAllocationsService;
import com.uca.network.extension.allocations.service.MacAddressService;

/**
 * @author by wangshuqiang@unicloud.com
 * 2019/11/5 9:30
 */
@SpringBootTest(classes = Application.class)
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("dev")
@Ignore
public class IpAllocationsTest {

    @Autowired
    private IpAllocationsService ipAllocationsService;

    @Autowired
    private MacAddressService macAddressService;

    @Test
    @Ignore
    public void testFloatingIp() {
        String subnetId = UUID.randomUUID().toString();// "6ef42666-f95f-4d58-87d8-8de3fb3830bb";
        long tt = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            String ip = ipAllocationsService.allocateIp("", subnetId, "192.168.0.1", "192.168.0.250");
            System.out.println(ip);
        }
        System.out.println(System.currentTimeMillis() - tt);
    }

    @Test
    @Ignore
    public void testMacAddress() {
        for (int i = 0; i < 100; i++) {
            long t = System.currentTimeMillis();
            System.out.println(macAddressService.getMacAddress());
            System.out.println(System.currentTimeMillis() - t);
        }
    }
}
