package kdu.cse.unispace.requestdto.member;

import kdu.cse.unispace.validation.member.UniqueMemberName;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberJoinRequestDto {

    @NotEmpty(message = "이메일을 입력해주세요.")
    @Email(message = "올바른 이메일 형식을 사용해주세요")
    private String email;

    @NotEmpty(message = "이름을 입력해주세요.")
    @UniqueMemberName
    private String memberName;

    @NotEmpty(message = "비밀번호를 입력해주세요.")
    @Size(min = 6, message = "비밀번호는 6자 이상 입력해주세요.")
    private String password;


}
