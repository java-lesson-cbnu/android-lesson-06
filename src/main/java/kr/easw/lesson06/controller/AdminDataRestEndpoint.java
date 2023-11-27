package kr.easw.lesson06.controller;

import kr.easw.lesson06.model.dto.TextDataDto;
import kr.easw.lesson06.service.TextDataService;
import kr.easw.lesson06.service.UserDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

// @RestController 어노테이션을 사용하여 이 클래스가 REST 컨트롤러임을 선언합니다.
@RestController
// @RequestMapping 어노테이션을 사용하여 이 클래스의 기반 엔드포인트를 /api/v1/data/admin으로 설정합니다.
@RequestMapping("/api/v1/data/admin")
// final로 지정된 모든 필드를 파라미터로 가지는 생성자를 생성합니다.
@RequiredArgsConstructor
public class AdminDataRestEndpoint {
    private final TextDataService textDataService;

    // 이 메서드의 엔드포인트를 /api/v1/data/add로 설정합니다. POST만 허용됩니다.
    @PostMapping("/add")
    public ModelAndView addText(@RequestParam Map<String, String> data, @RequestBody Map<String, String> body) {
        // 만약 body로 값이 넘어왔다면 body를, 그렇지 않다면 data를 사용합니다.
        // 이는 form-data로 넘어왔는지, JSON으로 넘어왔는지에 따라 값이 달라지기 때문입니다.
        if (data.containsKey("text"))
            textDataService.addText(new TextDataDto(0L, data.get("text")));
        else if (body.containsKey("text"))
            textDataService.addText(new TextDataDto(0L, body.get("text")));
        return new ModelAndView("redirect:/admin?success=true");
    }

}
