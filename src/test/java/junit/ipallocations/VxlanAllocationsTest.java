package junit.ipallocations;

import java.util.UUID;

import com.uca.network.extension.allocations.service.VlanAllocationsService;
import com.uca.network.extension.allocations.service.VxlanAllocationsService;
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
public class VxlanAllocationsTest {

    @Autowired
    private VxlanAllocationsService vxlanAllocationsService;

    @Autowired
    private VlanAllocationsService vlanAllocationsService;

    @Test
    public void testVxlan() {
        String subnetId = "25d95bbd-ecc0-41e5-b993-e92f82fb44ce";// "6ef42666-f95f-4d58-87d8-8de3fb3830bb";
        long tt = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            long vxlanId = vxlanAllocationsService.allocateVxlanId(subnetId, 100, 200);
            System.out.println(vxlanId);
        }
        System.out.println(System.currentTimeMillis() - tt);
    }


    @Test
    public void testVlan() {
        String subnetId = UUID.randomUUID().toString();// "6ef42666-f95f-4d58-87d8-8de3fb3830bb";
        long tt = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            long vlanId = vlanAllocationsService.allocateVlanId(subnetId, 100, 200);
            System.out.println(vlanId);
        }
        System.out.println(System.currentTimeMillis() - tt);
    }

    @Test
    public void testReleaseVxlan(){
        vxlanAllocationsService.releaseVxlanId("25d95bbd-ecc0-41e5-b993-e92f82fb44ce", 116);
    }
}
