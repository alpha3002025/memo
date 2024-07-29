# @Mock, @InjectMocks, @MockBean, @SpringBootTest

### 참고자료

- [https://giron.tistory.com/115](https://giron.tistory.com/115)

<br/>



### 요약

- Controller 테스트 시 @SpringBootTest 방식 사용
  - 특정 컴포넌트만 지정해서 컴포넌트 스캔하도록 Filter 하지 않으면, 모든 컴포넌트를 로딩
  - `@SpringBootTest` 사용시에는 가급적이면 테스트 용도의 Profile 을 따로 마련해야 함
  - @MockBean 을 사용합니다.
  - @MockBean 객체들을 의존성 주입 하는 주체는 @SpringBootTest 입니다.
- Controller 테스트 시 @InjectMocks 방식 사용
  - 모든 컴포넌트를 로딩하지 않는다.
  - @Mock 을 사용한다.
  - @Mock 객체들 의존성 주입 하는 주체는 @InjectMocks 입니다.

<br/>



### 컨트롤러에 의존성이 없을 때의 MockMvc 테스트 

컨트롤러 내에 의존성이 많지 않다면 단순하게 아래의 방식으로 테스트 가능합니다. 설명은 생략합니다.

SampleController.java

```java
@RestController
public class SampleController {
    @GetMapping("/sample/page1")
    public String getSamplePage(@RequestParam int size, @RequestParam Long offset){
        return "page1";
    }

    @GetMapping("/sample2/{pageName}")
    public String getSamplePage2(@PathVariable String pageName){
        return pageName;
    }
}

```

<br/>



테스트코드 - SampleControllerMockMvcTest

```java
@WebMvcTest(SampleController.class)
public class SampleControllerMockMvcTest {

    private MockMvc mockMvc;

    @Autowired
    private SampleController sampleController;

    @BeforeEach
    public void init(){
        mockMvc = MockMvcBuilders
                .standaloneSetup(sampleController)
                .build();
    }

    @Test
    public void TEST_USING_MULTIVALUEMAP() throws Exception{
        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("size", String.valueOf(10));
        param.add("offset", String.valueOf(1));

        this.mockMvc
                .perform(
                        get("/sample/page1")
                                .params(param)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("page1")));
    }

    @Test
    public void TEST_USING_PARAM() throws Exception{
        this.mockMvc
                .perform(
                        get("/sample/page1")
                                .queryParam("size", String.valueOf(10))
                                .queryParam("offset", String.valueOf(1))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("page1")));

    }

    @Test
    public void TEST_USING_PATHVARIABLE() throws Exception{
        this.mockMvc
                .perform(
                        get("/sample2/{pageName}", "page1")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("page1")));
    }

}

```

<br/>



### @Mock, @InjectMocks 를 사용하는 방식

@SpringBootTest 처럼 모든 Bean 들을 로딩해서 테스트하는 방식이 아닙니다.<br/>

컨트롤러 외의 모든 계층을 Mock 으로 두어서 API 레벨의 코드 자체에 에러가 있는지 검증을 위해 API 계층외의 Bean 들을 가짜 객체로 @Mock 으로 선언하고, @InjectMocks 를 이용해 바인딩해서 사용합니다.<br/>

<br/>



**예제**<br/>

Controller - UsersController.java

```java
@Controller
public class UsersController {
    private final UserServiceImpl userService;

    public UsersController(UserServiceImpl userService){
        this.userService = userService;
    }

    @PostMapping("/user/signup")
    public @ResponseBody String signup(@RequestBody SignupRequestDto signupRequestDto){
        userService.signupUser(signupRequestDto);
        return "SUCCESS"; // 임시적으로 문자를 리턴하도록 지정
    }

    @PostMapping("/api/me")
    public @ResponseBody LoginResponseDto apiMe(@AuthenticationPrincipal CustomUserDetails userDetails){
        return new LoginResponseDto(userDetails);
    }
}
```

<br/>



Service - UserServiceImpl.java

의존성 객체들

- UsersRepository
- PasswordEncoder 

```java
@Service
public class UserServiceImpl {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UsersRepository usersRepository,
                           @Qualifier("bCryptPasswordEncoder") PasswordEncoder bCryptPasswordEncoder){
        this.usersRepository = usersRepository;
        this.passwordEncoder = bCryptPasswordEncoder;
    }

    @Transactional
    public void signupUser(SignupRequestDto signupRequestDto){
        Users user = Users.of(
                signupRequestDto.getUsername(),
                signupRequestDto.getPassword(),
                "ROLE_USER",
                passwordEncoder
        );

        usersRepository.save(user);
    }

}
```

<br/>



**테스트코드**<br/>

- MockMvc 객체를 직접 생성하고, 의존성 역시 직접 주입
- 주입하는 의존성들은 모두 @Mock 어노테이션을 통해 Mock 객체로 지정
- Mock 객체를 모두 중첩해서 포함하는 객체를 @InjectMock 어노테이션을 통해 Mock 객체로 지정

```java
@ExtendWith(MockitoExtension.class)
public class UsersControllerMockMvcTest {

    private MockMvc mockMvc;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UsersRepository usersRepository;

    @InjectMocks
    private UserServiceImpl userService;


    @BeforeEach
    public void init(){
        passwordEncoder = new BCryptPasswordEncoder();

        mockMvc = MockMvcBuilders.standaloneSetup(new UsersController(userService))
                .build();
    }

    @Test
    public void USER_SIGNUP_API_TEST() throws Exception {
        SignupRequestDto signupRequest = new SignupRequestDto("asdf", "1234");
        String param = new ObjectMapper().writeValueAsString(signupRequest);

        this.mockMvc
                .perform(
                        post("/user/signup")
                                .content(param)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("SUCCESS")));
    }

    @Test
    public void API_ME() throws Exception {

    }
}
```

<br/>



### @MockBean

가끔 MockMvc 개념을 설명하는 예제 테스트 코드들 중 가끔은 실제 Repository 를 주입받아서 DB까지 테스트를 하는 예제가 있습니다. 이렇게 되면 테스트가 무엇을 테스트하려는지 모호해집니다. "MVC 계층에서 DB까지 테스트할 것인지?" 라는 물음에 도달하게 됩니다.<br/>

이런 경우에 현재 테스트에서 사용하려는 Controller 에 연관된 Service, Repository 와 같은 Bean 들은 가급적 @MockBean 으로 지정해서 Controller 계층에서 수행해야 하는 로직의 테스트에만 집중하시면, 테스트 계층별로 독립적인 테스트코드가 만들어지게 됩니다.<br/>

<br/>



### @SpringBootTest



