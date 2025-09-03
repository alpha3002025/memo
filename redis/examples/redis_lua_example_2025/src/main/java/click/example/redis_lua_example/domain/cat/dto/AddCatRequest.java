package click.example.redis_lua_example.domain.cat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
public class AddCatRequest {
    private Long id;
    private String name;
}
