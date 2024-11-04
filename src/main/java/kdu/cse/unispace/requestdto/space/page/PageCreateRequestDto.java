package kdu.cse.unispace.requestdto.space.page;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageCreateRequestDto {
    private String title;
    private Optional<Long> parentPageId;
}
