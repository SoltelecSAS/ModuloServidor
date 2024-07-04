
import com.soltelec.servidor.dao.LlantasDAO;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author SOLTELEC
 */
public class LlantaTest {
    
    public static void main(String[] args) 
    {
        LlantasDAO ldao = new LlantasDAO();
        System.out.println(ldao.insertarLlantas("llanta de prueba", 152, Integer.BYTES));
        //System.out.println();
    }
    
}
