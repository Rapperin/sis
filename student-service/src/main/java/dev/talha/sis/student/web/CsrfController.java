// dev.talha.sis.student.web.CsrfController
package dev.talha.sis.student.web;

import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CsrfController {
    @GetMapping("/csrf")
    public CsrfToken csrf(CsrfToken token) {
        // Bu metot çağrılınca Spring token’ı üretir ve CookieCsrfTokenRepository
        // cookie’yi (XSRF-TOKEN) response’a yazar. Ayrıca JSON’da da dönüyoruz.
        return token;
    }
}
