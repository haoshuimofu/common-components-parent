package me.ele.lab.rgc;

import me.ele.arch.xboot.test.junit4.DelegateTo;
import me.ele.arch.xboot.test.junit4.Property;
import me.ele.arch.xboot.test.junit4.SystemProperties;
import me.ele.arch.xboot.test.junit4.XBootRunner;
import me.ele.lab.rgc.main.Application;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(XBootRunner.class)
@DelegateTo(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {Application.class})
@SystemProperties({
        @Property(key = "APPID", value = "lab.rgc_parser"),
        @Property(key = "project.name", value = "rgc")
})
public abstract class BaseTest {

}