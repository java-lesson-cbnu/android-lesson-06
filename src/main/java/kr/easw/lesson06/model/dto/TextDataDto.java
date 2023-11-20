package kr.easw.lesson06.model.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 이 클래스를 엔티티로 선언합니다.
// 엔티티로 선언된 클래스는 DB의 테이블과 매핑됩니다.
@Entity
// 모든 필드를 인자로 받는 생성자를 자동으로 생성합니다.
@AllArgsConstructor
// 인자가 없는 생성자를 자동으로 생성합니다.
// 엔티티 클래스는 반드시 인자가 없는 생성자가 있어야 합니다.
@NoArgsConstructor
// 모든 필드에 대한 Getter를 자동으로 생성합니다.
@Getter
public class TextDataDto {
    // id 필드를 기본키로 지정합니다.
    @Id
    // @GeneratedValue 어노테이션을 통해 이 값을 자동 증가(auto-increment)로 지정합니다.
    @GeneratedValue
    private long id;

    // @Column 어노테이션으로 이 필드가 DB의 어떤 컬럼과 매핑되는지 지정합니다.
    // nullable = false로 지정하면 이 필드는 null이 될 수 없습니다.
    @Column(nullable = false)
    private String text;
}
