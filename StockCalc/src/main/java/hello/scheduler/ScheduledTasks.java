package hello.scheduler;

import hello.service.StockUserService;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 
 * @author Manikandan E.D
 *
 */


// @Scheduled(cron = "0 1 1 * * ?")
//Below you can find example pattern found on spring forum:

//* "0 0 * * * *" = the top of every hour of every day.
//* "*/10 * * * * *" = every ten seconds.
//* "0 0 8-10 * * *" = 8, 9 and 10 o'clock of every day.
//* "0 0/30 8-10 * * *" = 8:00, 8:30, 9:00, 9:30 and 10 o'clock every day.
//* "0 0 9-17 * * MON-FRI" = on the hour nine-to-five weekdays
//* "0 0 0 25 12 ?" = every Christmas Day at midnight
//Cron expression is represented by six fields:

//second, minute, hour, day of month, month, day(s) of week
//(*) means match any

//*/X means "every X"



@Component
public class ScheduledTasks {
	
	

	@Autowired
	StockUserService stockUserService;;

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 10000)
    public void reportCurrentTime() {
        log.info("The time is now {}", dateFormat.format(new Date()));
        stockUserService.sendStockEmailNoticationToUsers("D");
    	log.info("RunEveryWeekMonday Ended : The time is now {}", dateFormat.format(new Date()));
    }
    
    
    /*@Scheduled(cron = "0 0 9 * * MON")
    public void runEveryWeekMonday(){
    	log.info("RunEveryWeekMonday Started : The time is now {}", dateFormat.format(new Date()));
    	stockUserService.sendStockEmailNoticationToUsers("W");
    	log.info("RunEveryWeekMonday Ended : The time is now {}", dateFormat.format(new Date()));
    	
    }
    
    
    @Scheduled(cron = "0 0 9 1 * *")
    public void runEveryMonthFirst(){
    	log.info("RunEveryMonth Started Started : The time is now {}", dateFormat.format(new Date()));
    	stockUserService.sendStockEmailNoticationToUsers("M");
    	log.info("RunEveryMonth Ended : The time is now {}", dateFormat.format(new Date()));
    	
    }
    
    @Scheduled(cron = "0 0 9 1 1 *")
    public void runEveryYear(){
    	log.info("RunEveryYear Started: The time is now {}", dateFormat.format(new Date()));
    	stockUserService.sendStockEmailNoticationToUsers("Y");
    	log.info("runEveryYear Ended : The time is now {}", dateFormat.format(new Date()));
    	
    }
    
    @Scheduled(cron = "0 0 9 * * *")
    public void runEveryDay(){
    	log.info("RunEveryDay Started: The time is now {}", dateFormat.format(new Date()));
    	stockUserService.sendStockEmailNoticationToUsers("D");
    	log.info("RunEveryDay Ended : The time is now {}", dateFormat.format(new Date()));
    }*/
    
}