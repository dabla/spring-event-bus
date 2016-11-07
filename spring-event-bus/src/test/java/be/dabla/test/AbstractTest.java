package be.dabla.test;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.unitils.UnitilsBlockJUnit4ClassRunner;

@RunWith(UnitilsBlockJUnit4ClassRunner.class)
public abstract class AbstractTest {
    @Before
    public void initAnnotations() throws Exception {
        MockitoAnnotations.initMocks(this);
    }
}
