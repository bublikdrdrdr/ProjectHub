import app.services.response.ExceptionResponseMapping;
import org.hibernate.HibernateException;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by Bublik on 22-Dec-17.
 */
public class ExceptionResponseMappingTest {

    private ExceptionResponseMapping getTestMapping(){
        ExceptionResponseMapping erm = new ExceptionResponseMapping();
        Exception he = new HibernateException("");
        Exception e = new Exception();
        Exception re = new RuntimeException();
        erm.set(he, HttpStatus.BAD_REQUEST);
        erm.set(e, HttpStatus.NOT_IMPLEMENTED);
        erm.set(he, HttpStatus.SERVICE_UNAVAILABLE);
        erm.set(re, HttpStatus.INTERNAL_SERVER_ERROR);
        return erm;
    }

    @Test
    public void mappingToString(){
        Exception he = new HibernateException("");
        Exception e = new Exception();
        Exception re = new RuntimeException();
        ExceptionResponseMapping erm = getTestMapping();
        assertEquals("ExceptionResponseMapping.toString() returns wrong value",
                "["+he.getClass().getSimpleName()+":"+HttpStatus.SERVICE_UNAVAILABLE.name()+"("+HttpStatus.SERVICE_UNAVAILABLE.value()+"), " +
                re.getClass().getSimpleName()+":"+HttpStatus.INTERNAL_SERVER_ERROR.name()+"("+HttpStatus.INTERNAL_SERVER_ERROR.value()+"), " +
                e.getClass().getSimpleName()+":"+HttpStatus.NOT_IMPLEMENTED.name()+"("+HttpStatus.NOT_IMPLEMENTED.value()+")]",
                erm.toString());
    }

    @Test
    public void getStatusByException(){
        ExceptionResponseMapping erm = getTestMapping();
        assertTrue("Bad ExceptionResponseMapping.get() value", erm.get(new HibernateException("")).equals(HttpStatus.SERVICE_UNAVAILABLE));
        assertTrue("Bad ExceptionResponseMapping.get() value", erm.get(new Exception()).equals(HttpStatus.NOT_IMPLEMENTED));
        assertTrue("Bad ExceptionResponseMapping.get() value", erm.get(new RuntimeException()).equals(HttpStatus.INTERNAL_SERVER_ERROR));
        assertTrue("Bad ExceptionResponseMapping.get() value", erm.get(new IllegalArgumentException()).equals(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @Test
    public void createFromSourceAndEdit(){
        ExceptionResponseMapping erm1 = getTestMapping();
        ExceptionResponseMapping erm2 = erm1.clone();
        assertTrue("Clones don't equal", erm1.equals(erm2));
        erm1.remove(new RuntimeException());
        erm2.remove(new RuntimeException());
        assertTrue("Clones don't equal", erm1.equals(erm2));
        erm1.set(new IllegalArgumentException(), HttpStatus.I_AM_A_TEAPOT);
        erm2.set(new IllegalArgumentException(), HttpStatus.I_AM_A_TEAPOT);
        assertTrue("Clones don't equal", erm1.equals(erm2));
        erm2.set(new IllegalArgumentException(), HttpStatus.TEMPORARY_REDIRECT);
        assertFalse("Different objects are equals", erm1.equals(erm2));
    }

}
