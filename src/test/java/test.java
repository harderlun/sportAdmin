import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class test {

    @Test
    public void run(){
        System.out.println(new BCryptPasswordEncoder().encode("123456"));
    }
}
