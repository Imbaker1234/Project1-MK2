package DAO;

import com.DAO.ErsReimbursementDAO;
import com.POJO.ErsReimbursement;
import org.junit.BeforeClass;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class ErsReimbursementDAOTest {

    private static ErsReimbursementDAO eDAO;

    @BeforeClass
    public static void setUp() {
        eDAO = new ErsReimbursementDAO();
    }

    @Test
    public void testRetrieveAllReimbs() {
        /* Test to see if we retrieve anything at all */
        assertTrue(eDAO.retrieveAllReimbs().size() > 0);
        /* Test to see if all retrieved objects are of the desired type. */
        for(ErsReimbursement e : eDAO.retrieveAllReimbs()) {
            assertTrue(e instanceof ErsReimbursement);
        }
    }
}

