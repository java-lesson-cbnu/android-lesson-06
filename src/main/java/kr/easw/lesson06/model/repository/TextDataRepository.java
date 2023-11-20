package kr.easw.lesson06.model.repository;

import kr.easw.lesson06.model.dto.TextDataDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// @Repository 어노테이션을 통해 이 인터페이스가 레포지토리임을 선언합니다.
// 레포지토리는 SQL에서의 테이블과 매핑됩니다.
@Repository
public interface TextDataRepository extends JpaRepository<TextDataDto, Long> {

}
