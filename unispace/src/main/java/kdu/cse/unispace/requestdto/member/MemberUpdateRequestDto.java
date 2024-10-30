package kdu.cse.unispace.requestdto.member;

import lombok.Data;

@Data
public class MemberUpdateRequestDto {

    //변경 가능한거 더 있을라나..? 나중에 추가..
    private String memberName;
    private String email;
    private String password;
}
