package fit.iuh.config;


import fit.iuh.model.User;
import fit.iuh.repo.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // để dùng @PreAuthorize trong Controller
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/css/**", "/js/**", "/images/**").permitAll()
                // API áp dụng cùng ma trận quyền với giao diện.
                .requestMatchers(HttpMethod.GET, "/api/employees", "/api/employees/**")
                    .hasAnyRole("GUEST", "USER", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/employees").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/employees/*").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/employees/*").hasRole("ADMIN")
                .requestMatchers("/api/**").denyAll()
                // Thêm / sửa: USER, ADMIN
                .requestMatchers(HttpMethod.GET, "/employees/new").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/employees/*/edit").hasAnyRole("USER", "ADMIN")
                // Xóa: chỉ ADMIN
                .requestMatchers(HttpMethod.POST, "/employees/*/delete").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/employees", "/employees/*").hasAnyRole("USER", "ADMIN")
                // Xem danh sách / tìm kiếm / chi tiết: cả 3 role
                .requestMatchers(HttpMethod.GET, "/employees", "/employees/search", "/employees/*")
                    .hasAnyRole("GUEST", "USER", "ADMIN")
                .anyRequest().authenticated()
            )
            // Dùng form login mặc định của Spring, login xong về trang danh sách
            .formLogin(form -> form
                .defaultSuccessUrl("/employees", true)
                .permitAll()
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            );
        return http.build();
    }

    // Tự tạo 3 tài khoản mẫu nếu bảng users đang trống
    @Bean
    public CommandLineRunner seedUsers(UserRepository userRepository, PasswordEncoder encoder) {
        return args -> {
            if (userRepository.count() == 0) {
                userRepository.save(new User(null, "guest", encoder.encode("guest123"), "GUEST"));
                userRepository.save(new User(null, "user", encoder.encode("user123"), "USER"));
                userRepository.save(new User(null, "admin", encoder.encode("admin123"), "ADMIN"));
            }
        };
    }
}
