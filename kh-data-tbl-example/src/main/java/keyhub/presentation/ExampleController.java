package keyhub.presentation;

import keyhub.domain.ExampleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/example")
public class ExampleController {
    private final ExampleService exampleService;



}
