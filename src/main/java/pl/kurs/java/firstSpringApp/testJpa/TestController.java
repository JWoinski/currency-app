package pl.kurs.java.firstSpringApp.testJpa;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class TestController {
    private final TestEntityRepository testEntityRepository;

    @RequestMapping("/test")
    public String testEp(ModelMap modelMap) {
        TestEntity testEntity1 = new TestEntity("a", true);
        TestEntity testEntity2 = new TestEntity("b", false);
        TestEntity save = testEntityRepository.save(testEntity1);

        List<TestEntity> all = testEntityRepository.findAll();

        Optional<TestEntity> byId = testEntityRepository.findById(1);
        TestEntity testEntity = testEntityRepository.findById(1)
                .orElseThrow(() -> new EntityNotFoundException("ne znaloenizonoo encji o id: 1"));


//        modelMap.addAttribute("testVar", "to jest moja wartosc");
        modelMap.addAttribute("testVar", "to jest moja wartosc");
        return "testHtml";
    }
}
