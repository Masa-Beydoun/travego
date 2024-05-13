package com.example.security.Testing;
import static org.junit.Assert.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import lombok.RequiredArgsConstructor;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
@RunWith(SpringRunner.class)
@SpringBootTest

public class CourseControllerTest {
    public CourseControllerTest() {
    }


   // private  BaseUserService baseUserService;

   // @MockBean
   // private   BaseUserRepository baseUserRepository;

    @Test
    public void test()
    {
       // when(baseUserRepository.findNameById(1)).thenReturn(Optional.of (new String("kareem")));
      //  Optional<String> real= baseUserRepository.findNameById(1);
        //assertEquals("kareem", real.orElse(null));
    }



}
