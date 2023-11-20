package kr.easw.lesson06.service;

import kr.easw.lesson06.model.dto.TextDataDto;
import kr.easw.lesson06.model.repository.TextDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

// @Service 어노테이션을 통해 이 클래스가 서비스임을 선언합니다.
@Service
// @RequiredArgsConstructor 어노테이션을 통해 final로 선언된 필드를 초기화하는 생성자를 만듭니다.
@RequiredArgsConstructor
public class TextDataService {

    // @RequiredArgsConstructor 어노테이션을 통해 초기화된 필드는 final로 선언되어 있어서 setter를 만들지 않아도 됩니다.
    // 이 필드는 생성자에서 초기화됩니다.
    private final TextDataRepository userDataRepository;

    // 이 메소드는 테이블에 데이터를 추가합니다.
    public void addText(TextDataDto dto) {
        System.out.println("Adding text");
        userDataRepository.saveAndFlush(dto);
    }

    // 이 메소드는 테이블의 모든 데이터를 가져옵니다.
    public List<TextDataDto> listText() {
        return userDataRepository.findAll();
    }


}
