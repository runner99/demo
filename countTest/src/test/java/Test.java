import com.runner99.count.CountService;
import com.runner99.count.CountTask;
import com.runner99.count.PushCountToQueueTask;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author weizhenqiang
 * @date 2023/3/20 11:29
 */
public class Test {

    @org.junit.Test
    public void test01(){
        CountService countService = new CountService();


        ExecutorService executorService = Executors.newFixedThreadPool(10);
        executorService.execute(new CountTask(countService));

        ScheduledExecutorService scheduledExecutor = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutor.scheduleAtFixedRate(new PushCountToQueueTask(countService), 1, 1, TimeUnit.SECONDS);

        System.out.println(11);
    }
}
