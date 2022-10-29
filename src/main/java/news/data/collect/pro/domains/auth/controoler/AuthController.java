package news.data.collect.pro.domains.auth.controoler;

import lombok.RequiredArgsConstructor;
import news.data.collect.pro.domains.auth.controoler.request.LoginRequest;
import news.data.collect.pro.domains.auth.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

//    @PostMapping(value = "login")
//    public String login(@RequestBody LoginRequest request) {
//
//
//    }

}
